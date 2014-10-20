


public class TennisSet {

	private int[] playerScores = new int[] { 0, 0 };
	private boolean isTiebreakMode = false;
	private int[] tiebreakScores = new int[] { 0, 0 };;
	private boolean isDone = false;
	private int serverIndex = 0;
	private int setWinnerIndex = 0;
	private boolean serverSwap = false;

	public boolean isDone() {
		return isDone;
	}

	public int serverIndex() {
		return serverIndex;
	}

	public int winnerIndex() {
		if (!isDone) throw new IllegalStateException("Set has not ended!");
		return setWinnerIndex;
	}

	public boolean isTiebreak() {
		return isTiebreakMode;
	}

	public void update(boolean serverWon) {
		if (isDone) throw new IllegalStateException("Set has already ended!");
		if (isTiebreakMode) {
			int winnerIndex = serverWon ? serverIndex : 1 - serverIndex;
			tiebreakScores[winnerIndex]++;
			int diff = tiebreakScores[0] - tiebreakScores[1];
			if (tiebreakScores[0] >= 7 && diff >= 2) {
				isDone = true;
				setWinnerIndex = 0;
			} else if (tiebreakScores[1] >= 7 && diff <= -2) {
				isDone = true;
				setWinnerIndex = 1;
			} else {
				if (serverSwap) serverIndex = 1 - serverIndex;				
				serverSwap = !serverSwap;
			}
		} else {
			int winnerIndex = serverWon ? serverIndex : 1 - serverIndex;
			playerScores[winnerIndex]++;
			if (playerScores[0] == 6 && playerScores[1] == 6) {
				// Enter tie-break mode.
				isTiebreakMode = true;
				serverIndex = 0;
				serverSwap = true;
			} else {
				int diff = playerScores[0] - playerScores[1];
				if (playerScores[0] >= 6 && diff >= 2) {
					isDone = true;
					setWinnerIndex = 0;
				} else if (playerScores[1] >= 6 && diff <= -2) {
					isDone = true;
					setWinnerIndex = 1;
				} else {
					serverIndex = 1 - serverIndex;
				}
			}
		}
	}

	public String scoreString() {
		return "" + playerScores[0] + " game" 
				+ plural(playerScores[0]) + " to "
				+ playerScores[1];
	}

	private String plural(int i) {
		return (i == 0 || i > 1) ? "s" : "";
	}

}
