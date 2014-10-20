import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Common {

	/*
	 * File format:
	 * #team-name-1
	 * player-name-1,power,speed,grip
	 * ...
	 * player-name-n,power,speed,grip
     * ...    
	 * #team-name-m
	 * player-name-1,power,speed,grip
	 * ...
	 * player-name-n,power,speed,grip
	 */
	public static Map<String,Team> loadTeams(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) return null;
		Map<String,Team> teams = new HashMap<String,Team>();
		Team curTeam = null;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = bufferedReader.readLine();
		while (line != null) {
			line = line.trim();
			if (line.startsWith("#")) {
				String teamName = line;
				curTeam = new Team(teamName);
				teams.put(teamName, curTeam);
			} else {
				if (curTeam == null)
					throw new IOException("Missing field: team-name");
				String[] parts = line.split(",");
				String playerName = parts[0];
				int power = Integer.valueOf(parts[1]);
				int speed = Integer.valueOf(parts[2]);
				int grip = Integer.valueOf(parts[3]);
				Player player = new Player(playerName, power, speed, grip);
				curTeam.put(playerName, player);
			}
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return teams;
	}

}
