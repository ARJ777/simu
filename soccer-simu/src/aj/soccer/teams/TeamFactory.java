package aj.soccer.teams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
			teams = new ArrayList<>();
			for (String teamName : getTeamNames())
				teams.add(loadTeam(teamName));
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
	 * Automatically selects a field of players. 
	 * 
	 * @param team - The team.
	 */
	public static void autoSelectPlayers(Team team/*,Formation formation*/) {
		clearPlayers(team);
		//choosePlayers();
		//assignPlayers();
	}

	private static void clearPlayers(Team team) {
		for (Player player : team.getPlayers()) {
			player.setActive(false);
			player.setPosition(null);
		}
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
