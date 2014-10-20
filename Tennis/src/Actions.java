import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Actions {

	private final static Random generator = new Random();

	public static PointStats playSinglesPoint(SinglesShotModel model) {
		// Server serves.
		int numShots = 1;
		double roll = generator.nextDouble();
		if (roll < model.probServerAce) {
			// Server won by ace.
		} else {
			while (true) {
				// Receiver returns shot.
				numShots++;
				roll = generator.nextDouble();
				if (roll < model.probReceiverWinsReturn) break;
				// Server returns shot.
				numShots++;
				roll = generator.nextDouble();
				if (roll < model.probServerWinsReturn) break;
			}
		}
		return new PointStats(numShots);
	}


	public static GameStats playSinglesGame(SinglesShotModel model) {
		Game game = new Game();
		List<Integer> points = new ArrayList<Integer>();
		while (!game.isDone()) {
			PointStats point = playSinglesPoint(model);
			points.add(point.isServerWin() ? 0 : 1);
			game.update(point.isServerWin());
		}
		return new GameStats(points, game.isServerWin());
	}

	public static SetStats playSinglesSet(PlayerShotModel player1, PlayerShotModel player2) {
		SinglesShotModel[] models = new SinglesShotModel[] {
				new SinglesShotModel(player1, player2),
				new SinglesShotModel(player2, player1)
		};
		TennisSet tset = new TennisSet();
		List<Integer> points = new ArrayList<Integer>();
		while (!tset.isDone()) {
			boolean serverWon = false;
			int serverIndex = tset.serverIndex();
			if (tset.isTiebreak()) {
				PointStats pstats = playSinglesPoint(models[serverIndex]);
				serverWon = pstats.isServerWin();
			} else {
				GameStats gstats = playSinglesGame(models[serverIndex]);
				serverWon = gstats.isServerWin();
			}
			tset.update(serverWon);
			int winnerIndex = serverWon ? serverIndex : 1 - serverIndex;
			points.add(winnerIndex);
		}
		return new SetStats(points);
	}

}