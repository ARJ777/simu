import java.io.IOException;
import java.util.Map;


public class TestGameScores {

	public static void main(String[] args) throws IOException {
		Map<String,Team> teams = Common.loadTeams("resource/TeamsPlayers.dat");
		Player player1 = findPlayer(teams, args[0]);
		Player player2 = findPlayer(teams, args[1]);
		PlayerShotModel model1 = new PlayerShotModel(player1);
		PlayerShotModel model2 = new PlayerShotModel(player2);
		SinglesShotModel shotModel = new SinglesShotModel(model1, model2);
		final String[] playerNames = new String[]{player1.name, player2.name};
		final int M = 12;
		for (int i = 0; i < M; i++) {
			System.out.printf("Point %d of %d:\n", i+1, M);
			PointStats pointStats = Actions.playSinglesPoint(shotModel);
			pointStats.displayRally(playerNames);

		}

		simulateRallies(model1, model2);
		simulateRallies(model2, model1);

		for (int i = 0; i < M; i++) {
			System.out.printf("Game %d of %d:\n", i+1, M);
			GameStats gameStats = Actions.playSinglesGame(shotModel);
			gameStats.displayGame(playerNames);
		}
	}

	private static void simulateRallies(PlayerShotModel server, PlayerShotModel receiver) {
		SinglesShotModel shotModel = new SinglesShotModel(server, receiver);
		//System.out.printf("DEBUG: %s serving\n", server.player.name);
		//System.out.printf("serv_ace=%f, rec_ret=%f, serv_ret=%f\n", shotModel.probServerAce, shotModel.probReceiverWinsReturn, shotModel.probServerWinsReturn);
		int aces = 0, serverWins = 0, receiverWins = 0;
		final int N = 100000;
		for (int i = 0; i < N; i++) {
			PointStats pointStats = Actions.playSinglesPoint(shotModel);
			//pointStats.displayRally(playerNames);
			if (pointStats.isAce()) aces++;
			if (pointStats.isServerWin()) { serverWins++; }
			else { receiverWins++; }
		}
		System.out.printf("Simulated %d point rallies with %s serving:\n", N, server.player.name);
		System.out.printf("* %s won %6.2f%% of points, %s won %6.2f%%\n", server.player.name, 100.*serverWins/N, receiver.player.name, 100.*receiverWins/N);
		System.out.printf("* %s aced in %6.2f%% of wins\n", server.player.name, 100.*aces/serverWins);
		System.out.printf("* %s aced in %6.2f%% of points\n", server.player.name, 100.*aces/N);
	}

	private static Player findPlayer(Map<String, Team> teams, String playerName) {
		for (Team team : teams.values()) {
			Player player = team.get(playerName);
			if (player != null) return player;
		}
		return null;
	}

}
