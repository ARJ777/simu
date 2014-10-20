package aj.nfl.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OffensiveDrive {

	public static void main(String[] args) {
		double offDistance = 70;
		int numDowns = 1;
		double needDist = 10;
		while (numDowns <= 4) {
			System.out.printf("[Commentator]: Down %d starts at %3.0f yards needing %3.0f yards!%n", numDowns, offDistance, needDist);
			OffenseAction offAction = userChooseOffenseAction();
			DefenseAction defAction = autoChooseDefenseAction();
			System.out.printf("[Debug]: The offense are trying to %s, and the defence are trying to %s%n",
					offAction, defAction);
			double offStrength = 8;
			double defStrength = 7;
			double probSucceed = DriveSimulator.actionSucceeds(offAction, defAction, offStrength, defStrength);
			boolean doesSucceed = Simulator.isSuccessful(probSucceed);
			if (doesSucceed) {
				offDistance -= 5;
				needDist -= 5;
				System.out.printf("[Commentator]: Great play! The offense advanced 5 yards!%n");
				if (needDist <= 0) {
					System.out.printf("[Commentator]: Fantastic! The offense advanced a total of %3.0f yards!%n", 10-needDist);
					break;
				}
			} else {
				offDistance += 3;
				needDist += 3;
				System.out.printf("[Commentator]: Oh no, the offense just lost 3 yards!%n");
			}
			numDowns++;
			//System.out.printf("[Commentator]: The offense is now %d downs and %3.0f yards!%n", numDowns, offDistance);
		}
		if (numDowns > 4) { // Offense failed!
			System.out.printf("[Commentator]: The offense has failed, turnover opposition's ball%n");
		} else { // Offense succeeded.
			System.out.printf("[Commentator]: The offense has made a first down%n");	
		}
	}

	private static DefenseAction autoChooseDefenseAction() {
		return (DefenseAction) Simulator.choose(
				DefenseAction.BlockLine, 0.33, 
				DefenseAction.BlockPass, 0.33,
				DefenseAction.BlockRun, 0.34
				);
	}

	private static OffenseAction userChooseOffenseAction() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the offense action: ");
		try {
			OffenseAction offAction = null;
			while (offAction == null) {
				String input = br.readLine();
				offAction = OffenseAction.fromString(input);
			}
			return offAction;
		} catch (IOException e) {
			return (OffenseAction) Simulator.choose(
					OffenseAction.Line, 0.33, 
					OffenseAction.Pass, 0.33,
					OffenseAction.Run, 0.34
					);
		}
	}
}
