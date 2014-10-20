package aj.nfl.stats;

public class RunningPast {


	public static double runsPast(int offPower, int offSpeed, int offTechnique, int defPower, int defSpeed, int defTechnique) {
		checkRange(offPower, "offensive power");
		checkRange(defPower, "defensive power");
		checkRange(offSpeed, "offensive speed");
		checkRange(defSpeed, "defensive speed");
		checkRange(offTechnique, "offensive technique");
		checkRange(defTechnique, "defensive technique");
		int offScore = offPower + offSpeed + offTechnique;
		int defScore = defPower + defSpeed + defTechnique;
		int norm = 1 * offScore + 1 * defScore; 
		return 1.0 * offScore / norm;
	}

	private static void checkRange(int stat, String name) {
		if (stat <= 0 || stat > 10)
			throw new IllegalArgumentException("Stat " + name + " cannot have value " + stat);
	}
	public static void main(String[] args) {
		// Test isSuccessful() for various values.
		System.out.printf("Testing isSuccessful()...%n");
		final int N = 1000;
		for (int op = 1; op <= 10; op+=3) { 
			for (int os = 1; os <= 10; os+=3) {
				for (int ot = 1; ot <= 10; ot+=3) {
					for (int dp = 1; dp <= 10; dp+=3) {
						for (int ds = 1; ds <= 10; ds+=3) {
							for (int dt = 1; dt <= 10; dt+=3) {
								double prob = runsPast(op, os, ot, dp, ds, dt);
								int count = Simulator.simulate(prob, N);
								System.out.printf("offense: %d %d %d, defence: %d %d %d: occurs %6.2f%%%n",  
										op, os, ot, dp, ds, dt, 100.0 * count / N);
							}
						}
					}
				}
			}
		}
	}
}
