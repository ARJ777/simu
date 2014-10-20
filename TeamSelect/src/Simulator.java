
public interface Simulator {

	/**
	 * @param team1 - The team that kicks-off in the first half.
	 * @param team2 - The team that kicks-off in the second half.
	 * @return The sequence of states representing the play of the game.
	 */
	int[] simulateGame(Team team1, Team team2);

	/**
	 * Prints out the ball-by-ball play of the game.
	 * @param states - The sequence of states representing the play of the game.
	 */
	void displayGame(int[] states);
	
	/**
	 * @param states - The sequence of states representing the play of the game.
	 * @return The number of goals for team1 and team2.
	 */
	int[] summariseGame(int[] states);
	
}
