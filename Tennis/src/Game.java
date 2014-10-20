
public class Game {

	public static enum Score {
		love(0), fifteen(15), thirty(30), forty(40), game(-1);

		public final int score;

		Score(int score) {
			this.score = score;
		}

		public Score increment() {
			return Score.values()[this.ordinal()+1];
		}
	}

	private Score serverScore = Score.love;
	private Score receiverScore = Score.love;
	private boolean isDeuceMode = false;
	private int advantage = 0;

	public void update(boolean serverWonPoint) {
		if (isDone()) throw new IllegalStateException("Game has already ended!");
		if (isDeuceMode) {
			advantage += (serverWonPoint) ? 1 : -1;
			if (Math.abs(advantage) >= 2) {
				isDeuceMode = false;
				if (advantage > 0)
					serverScore = Score.game;
				else
					receiverScore = Score.game;
				advantage = 0;
			}
		}
		else if (serverWonPoint) {
			serverScore = serverScore.increment();
		} else {
			receiverScore = receiverScore.increment();
		}
		if (!isDeuceMode && serverScore == Score.forty && receiverScore == Score.forty) {
			isDeuceMode = true;
			advantage = 0;
		}
	}

	public boolean isDone() {
		return serverScore == Score.game || receiverScore == Score.game;
	}

	public boolean isDeuce() {
		return isDeuceMode && advantage == 0;
	}

	public boolean isAdvantageServer() {
		return isDeuceMode && advantage > 0;
	}

	public boolean isAdvantageReceiver() {
		return isDeuceMode && advantage > 0;
	}

	public String scoreString() {
		if (isDeuceMode) {
			return (advantage == 0)?"deuce":((advantage > 0)?"advantage server":"advantage receiver");
		} else if (serverScore == receiverScore) {
			return "" + serverScore.name() + " all";
		} else{
			return "" + serverScore.name() + ":" + receiverScore.name();
		}
	}

	public boolean isServerWin() {
		return serverScore == Score.game;
	}

}
