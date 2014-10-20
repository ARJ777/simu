import java.util.List;


public class SetStats {

	private final List<Integer> points;

	public SetStats(List<Integer> points) {
		this.points = points;
	}

	public void displaySet(String[] playerNames) {
		// Replay set.
		TennisSet tset = new TennisSet();
		for (int winnerIndex : points) {
			int serverIndex = tset.serverIndex();
			tset.update(winnerIndex == serverIndex);
			if (tset.isTiebreak())
				System.out.printf("%s serves: %s won point - score: %s\n", playerNames[serverIndex], playerNames[winnerIndex], tset.scoreString());
			else
				System.out.printf("%s serves: %s won game - score: %s\n", playerNames[serverIndex], playerNames[winnerIndex], tset.scoreString());
		}
		System.out.printf("%s wins!\n", playerNames[tset.winnerIndex()]);
	}

}
