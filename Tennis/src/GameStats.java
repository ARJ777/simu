import java.util.List;


public class GameStats {

	private final List<Integer> points;
	private final boolean isServerWin;

	/**
	 * @param points - A list of indices of who scored the winning
	 * points in the game.
	 * @param isServerWin 
	 */
	public GameStats(List<Integer> points, boolean isServerWin) {
		this.points = points;
		this.isServerWin = isServerWin;
	}

	public void displayGame(String[] playerNames) {
		// Replay game.
		Game game = new Game();
		for (int winnerIndex : points) {
			game.update(winnerIndex == 0);
			System.out.printf("%s won point - score: %s\n", playerNames[winnerIndex], game.scoreString());
		}
	}

	public boolean isServerWin() {
		return isServerWin;
	}
	
}
