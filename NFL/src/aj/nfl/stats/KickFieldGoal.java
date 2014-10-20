package aj.nfl.stats;

public class KickFieldGoal {

	/**
	 * Determines the probability of the best player successfully 
	 * kicking a field goal from the given distance.
	 * 
	 * @param dist - Distance from goals (in yards).
	 * @return The probability of a goal being kicked from the
	 * given distance.
	 */
	public static double kicksGoal(double dist) {
		if (dist < 17 || dist > 60)
			throw new IllegalArgumentException("Cannot kick a goal from distance: " + dist);
		if (dist > 55) return 0;
		return (55 - dist) / (55 - 17);
	}

    public static void main(String[] args) {
    	// Test isSuccessful() for various values.
		System.out.printf("Testing isSuccessful()...%n");
		final int N = 1000;
    	for (int d = 20; d <= 60; d += 5) {
    		double prob = kicksGoal(d);
    		int count = Simulator.simulate(prob, N);
    		System.out.printf("distance %d: occurs %6.2f%%%n", d, 100.0 * count / N);
    	}    	
    }
}
