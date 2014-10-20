package aj.nfl.stats;

public class LineBattle {

	public static double createsGap(int offPower, int defPower) {
		if (offPower < 0 || offPower > 10)
			throw new IllegalArgumentException("Cannot have offensive power: " + offPower);
		if (defPower < 0 || defPower > 10)
			throw new IllegalArgumentException("Cannot have defensive power: " + defPower);
		int norm = 5 * offPower + 4 * defPower; 
		return 5.0 * offPower / norm;
	}
	public static void main(String[] args) {
		// Test isSuccessful() for various values.
		System.out.printf("Testing isSuccessful()...%n");
		final int N = 1000;
		for (int o = 0; o <= 10; o++) {
			for (int d = 0; d <= 10; d++) {
				double prob = createsGap(o, d);
				int count = Simulator.simulate(prob, N);
				System.out.printf("off %d, def %d: occurs %6.2f%%%n", o, d, 100.0 * count / N);
			}  
		}
	}
}
