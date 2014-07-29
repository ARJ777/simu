package aj.soccer.team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aj.soccer.data.Formation;
import aj.soccer.data.Player;
import aj.soccer.data.Position;
import aj.soccer.data.Team;
import aj.soccer.formation.FormationFactory;

/**
 * Provides a means for loading teams from file.
 * <p/>Convention: The name of the file is the name of the team
 * with spaces replaced by underscores, with file extension ".dat".
 */
public abstract class TeamFactory {

	private static final File TEAM_FILE_ROOT = new File("teams");
	private static final String TEAM_FILE_EXT = ".dat";
	private static final String COMMENT_MARKER = "#";
	private static final String FIELD_SEPARATOR = ",";
	private static final int NAME_FIELD_INDEX = 0;
	private static final int IMAGE_NUMBER_FIELD_INDEX = 1;
	private static final int JERSEY_NUMBER_FIELD_INDEX = 2;
	private static final int POSITIONS_FIELD_INDEX = 3;
	private static final int MAX_ASSIGNMENT_ATTEMPTS = 20;

	private static List<Team> teams = null;

	private TeamFactory() {}

	private static String toFileName(String teamName) {
		return teamName.replace(' ', '_') + TEAM_FILE_EXT;
	}

	private static String toTeamName(String teamFileName) {
		if (!teamFileName.endsWith(TEAM_FILE_EXT))
			return null;
		return teamFileName.substring(0, teamFileName.length() - TEAM_FILE_EXT.length()).replace('_', ' ');
	}

	private static String toTeamName(File teamFile) {
		return toTeamName(teamFile.getName());
	}

	/**
	 * Lists the names of all currently defined teams.
	 * 
	 * @return The sorted list of team names.
	 */
	public static List<String> getTeamNames() {
		List<String> teamNames = new ArrayList<>();
		for (File teamFile : TEAM_FILE_ROOT.listFiles()) {
			String teamName = toTeamName(teamFile);
			if (teamName != null)
				teamNames.add(teamName);
		}
		Collections.sort(teamNames);
		return teamNames;
	}

	/**
	 * Lists all currently defined teams.
	 * 
	 * @return The list of teams, sorted by name.
	 */
	public static List<Team> getTeams() {
		if (teams == null) {
			ArrayList<Team> _teams = new ArrayList<>();
			for (String teamName : getTeamNames()) {
				Team team = loadTeam(teamName);
				selectPlayers(team, team.getFormation());
				teams.add(team);
			}
			teams = _teams;
		}
		return teams;
	}

	/**
	 * Refreshes the list of currently defined teams from the disk, e.g.
	 * if a new team file has been added.
	 */
	public static void refreshTeams() {
		teams = null;
	}

	private static Team loadTeam(final String teamName, File teamFile) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(teamFile)))) {
			return parseTeam(teamName, br);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Team loadTeam(String teamName) {
		return loadTeam(teamName, new File(TEAM_FILE_ROOT, toFileName(teamName)));
	}

	private static Team parseTeam(final String teamName, BufferedReader reader) {
		final List<Player> players = new ArrayList<>();
		Formation formation = null;
		boolean hasHeader = false;
		String line = null;
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) break;
				line = line.trim();
				if (line.isEmpty() || line.startsWith(COMMENT_MARKER)) continue;
				if (hasHeader) {
					players.add(parsePlayer(line));
				} else {
					formation = FormationFactory.getFormation(line);
					if (formation == null)
						throw new IllegalStateException("Unknown formation: " + line);
					hasHeader = true;
				}
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		Team team = new TeamImpl(teamName, players, formation);
		return team;
	}

	/**
	 * Deselects all players on the team, and marks them as inactive but selectable.
	 * 
	 * @param team - The team of all players.
	 */
	public static void deselectPlayers(Team team) {
		final List<Player> allPlayers = team.getPlayers();
		for (Player player : allPlayers) {
			player.setActive(false);
			player.setSelectable(true);
		}
		FormationFactory.deselectPlayers(allPlayers);
	}
	
	/**
	 * Automatically selects a group of on-field players and assigns them to their 
	 * positions in the formation.
	 * <p/>Players who are already active will be reselected, and players
	 * who are unselectable will never be selected. If necessary, additional players
	 * who are inactive but selectable will also be selected.  
	 * 
	 * @param team - The team of all possible players.
	 * @param formation - The on-field formation.
	 * @throws IllegalStateException If the team cannot be assigned to the formation. 
	 */
	public static void selectPlayers(Team team, Formation formation) {
		final List<Player> allPlayers = team.getPlayers();
		List<Player> selectedPlayers = FormationFactory.selectPlayers(allPlayers, formation);
		if (selectedPlayers == null)
			throw new IllegalStateException("Could not assign " + team + " to the formation " + formation);
		for (Player player: selectedPlayers) player.setActive(true);
	}

	private static Player parsePlayer(String line) {
		String[] fields = line.split(FIELD_SEPARATOR);
		return new PlayerImpl(
				fields[NAME_FIELD_INDEX].trim(), 
				getPositions(fields[POSITIONS_FIELD_INDEX].trim())
		);
	}

	private static List<Position> getPositions(String positionCodes) {
		final int length = positionCodes.length();
		List<Position> positions = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			positions.add(Position.fromCode(positionCodes.charAt(i)));
		}
		return positions;
	}

}
