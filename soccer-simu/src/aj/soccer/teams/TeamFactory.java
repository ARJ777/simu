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

	private static final String TEAM_FILE_EXT = ".dat";
	private static final File TEAM_FILE_ROOT = new File("teams");
	private static final String COMMENT_MARKER = "#";
	private static final String FIELD_SEPARATOR = ",";

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
		String line = null;
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) break;
				line = line.trim();
				if (line.isEmpty() || line.startsWith(COMMENT_MARKER)) continue;
				players.add(parsePlayer(line));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		return new TeamImpl(teamName, players);
	}

	private static Player parsePlayer(String line) {
		String[] fields = line.split(FIELD_SEPARATOR);
		return new PlayerImpl(fields[0].trim());
	}

}
