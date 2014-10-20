package aj.nfl.stats;

import java.util.Random;

public class Simulator {

	private static final Random generator = new Random();

	public static double getRandom() {
		return generator.nextDouble();
	}
	
	/**
	 * Determines if an event succeeds at random given the prior level of probability.
	 * @param prob
	 * @return
	 */
	public static boolean isSuccessful(double prob) {
		return getRandom() < prob;
	}

	/**
	 * Randomly selects from one of the given choices, which are in the format: action, probability.
	 * 
	 * @param choices The available choices and their probabilities.
	 * @return The chosen action.
	 */
	public static Object choose(Object... choices) {
		final int len = choices.length;
		if (len == 0 || len % 2 != 0)
			throw new IllegalArgumentException("Invalid choices");
		double rnd = getRandom();
		int idx = 0;
		double cumProb = (Double) choices[idx + 1];
		while (idx < len) {
			if (rnd < cumProb) return choices[idx];
			idx += 2;
			cumProb += (Double) choices[idx + 1];
		}
		return choices[len - 1];
	}

    public static void main(String[] args) {
    	// Test isSuccessful() for various values.
		System.out.printf("Testing isSuccessful()...%n");
		final int N = 1000;
    	for (int i = 0; i <= 10; i++) {
    		double prob = i / 10.0;
    		int count = simulate(prob, N);
    		System.out.printf("prob %6.2f%%: occurs %6.2f%%%n", 100 * prob, 100.0 * count / N);
    	}    	
    }

	public static int simulate(double prob, int numTimes) {
		int count = 0;
		for (int i = 0; i < numTimes; i++) {
			if (isSuccessful(prob)) count++;
		}
		return count;
	}
    
}
