package aj.nfl.stats;

import static aj.nfl.stats.OffenseAction.*;
import static aj.nfl.stats.DefenseAction.*;

public class DriveSimulator { 
	
	private static final double DEFENSE_BONUS = 1.25;

	public static void main(String[] args) {
		double distFromTDArea = 70; //yards
		OffenseAction offAction = Run;
		DefenseAction defAction = BlockRun;
		double offStrength = 8;
		double defStrength = 7;
		double probOfSuccess = actionSucceeds(offAction, defAction, offStrength, defStrength);
		System.out.printf("Prob(%s|%s) = %6.2f%n", offAction, defAction, 100*probOfSuccess);
				
	}

	public static double actionSucceeds(OffenseAction offAction, DefenseAction defAction, double offStrength, double defStrength) {
		if (defAction.getComplement() == offAction) {
			defStrength *= DEFENSE_BONUS;
		} else {
			defStrength /= DEFENSE_BONUS; // penalty
		}
		System.out.printf("offStr=%f, defStr=%f%n", offStrength, defStrength);
		return offStrength / (offStrength + defStrength);
	}
}
