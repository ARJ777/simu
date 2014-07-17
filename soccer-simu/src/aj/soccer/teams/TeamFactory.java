package aj.soccer.teams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	 * @return The list of team names.
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

	private static Team loadTeam(File teamFile) {
		try (InputStream is = new FileInputStream(teamFile)) {
			return parseTeam(is);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Team loadTeam(String teamName) {
		return loadTeam(new File(TEAM_FILE_ROOT, toFileName(teamName)));
	}

	private static Team parseTeam(InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}

}
