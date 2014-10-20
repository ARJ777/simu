
public class PointStats {

	private final int numShots;

	public PointStats(int numShots) {
		this.numShots = numShots;
	}
	
	public void displayRally(String[] playerNames) {
		if (numShots <= 1) {
			System.out.printf("%s wins by acing %s!\n", playerNames[0], playerNames[1]);
		} else {
			System.out.printf("%s serves but doesn't ace %s.\n", playerNames[0], playerNames[1]);
			int _numShots = numShots;
			int playerNum = 1;
			while (--_numShots > 0) {
				if (_numShots == 1) {
					System.out.printf("%s returns the shot and wins the point!\n", playerNames[playerNum]);
					break;
				} else {
					System.out.printf("%s returns the shot.\n", playerNames[playerNum]);
					playerNum = 1 - playerNum;
				}
			}
		}
		System.out.printf("Rally lasted for %d shots!\n", numShots);
	}

	public boolean isAce() {
		return numShots == 1;
	}

	public boolean isServerWin() {
		return numShots % 2 == 1;
	}
	
}
