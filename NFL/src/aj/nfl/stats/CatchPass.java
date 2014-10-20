package aj.nfl.stats;

public class CatchPass {	
	public static double catchPass(int offPower, int offCatch, int offTechnique, int offJumping, int defPower, int defCatch, int defTechnique, int defJumping) {
		checkRange(offPower, "offensive power");
		checkRange(defPower, "defensive power");
		checkRange(offCatch, "offensive catch");
		checkRange(defCatch, "defensive catch");
		checkRange(offTechnique, "offensive technique");
		checkRange(defTechnique, "defensive technique");
		checkRange(offJumping, "offensive jumping");
		checkRange(defJumping, "offensive jumping"); 
		int offScore = offPower + offCatch + offTechnique + offJumping;
		int defScore = defPower + defCatch + defTechnique + defJumping;
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
			for (int oc = 1; oc <= 10; oc+=3) {
				for (int ot = 1; ot <= 10; ot+=3) {
					for (int oj = 1; oj <= 10; oj+=3) {
						for (int dp = 1; dp <= 10; dp+=3) {
							for (int dc = 1; dc <= 10; dc+=3) {
								for (int dt = 1; dt <= 10; dt+=3) {
									for (int dj = 1; dj <= 10; dj+=3) {
										double prob = catchPass(op, oc, ot, oj, dp, dc, dt, dj);
										int count = Simulator.simulate(prob, N);
										System.out.printf("offense: %d %d %d %d, defence: %d %d %d %d: occurs %6.2f%%%n", 
												op, oc, ot, oj, dp, dc, dt, dj, 100.0 * count / N);
									}
								}
							}
						}
					}
				}
			}

		}
	}
}