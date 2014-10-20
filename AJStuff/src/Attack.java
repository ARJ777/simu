import java.util.Random;


public class Attack {

	private static final int OFFENSE_NAME_ARG = 0;
	private static final int OFFENSE_SCORE_ARG = 1;
	private static final int DEFENSE_NAME_ARG = 2;
	private static final int DEFENSE_SCORE_ARG = 3;

	private final static Random generator = new Random();
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.printf("Usage: java %s <offense-name> <offsense-score> <defense-name> <defense-score><\n", Attack.class.getSimpleName());
			System.exit(0);
		}
		int oscore = Integer.valueOf(args[OFFENSE_SCORE_ARG]);
		int dscore = Integer.valueOf(args[DEFENSE_SCORE_ARG]);
		double prob = 1.0 * oscore / (oscore + dscore);
		System.out.printf("%s vs %s with %6.2f%% chance", args[OFFENSE_NAME_ARG], args[DEFENSE_NAME_ARG], 100*prob);
		double roll = generator.nextDouble();
		String winner = args[(roll <= prob) ? OFFENSE_NAME_ARG : DEFENSE_NAME_ARG];
		System.out.printf(" - %s wins!\n", winner);
	}

}
