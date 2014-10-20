import java.io.IOException;
import java.util.Map;


public class TestSetScores {

	public static void main(String[] args) throws IOException {
		Map<String,Team> teams = Common.loadTeams("resource/TeamsPlayers.dat");
		Player player1 = findPlayer(teams, args[0]);
		Player player2 = findPlayer(teams, args[1]);
		PlayerShotModel model1 = new PlayerShotModel(player1);
		PlayerShotModel model2 = new PlayerShotModel(player2);
		final String[] playerNames = new String[]{player1.name, player2.name};
		final int M = 1;
		for (int i = 0; i < M; i++) {
			System.out.printf("Set %d of %d:\n", i+1, M);
			SetStats setStats = Actions.playSinglesSet(model1, model2);
			setStats.displaySet(playerNames);

		}
	}
	
	private static Player findPlayer(Map<String, Team> teams, String playerName) {
		for (Team team : teams.values()) {
			Player player = team.get(playerName);
			if (player != null) return player;
		}
		return null;
	}

}
