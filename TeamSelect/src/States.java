
public class States {

	/**
	 * Assume, without loss of generality, that team1 kicks-off first and plays from left to right.
	 * In the second half, team2 kicks-off first and plays from right to left.
	 * The directions of play are therefore:
	 * 
	 *   +1 => left-to-right (i.e. team1 has possession).
	 *   -1 => right-to-left (i.e. team2 has possession).
	 *
	 * The field itself is divided into the following zones (with the subscript showing the team that mans that zone):
	 * 
	 * Z{-3} |      Z{-2}       |   Z{-1}  |   Z{0}   |   Z{+1}  |     Z{+2}        | Z{+3}
	 * Goal1 | Defense1/Attack2 | Midfield | Kick-off | Midfield | Attack1/Defense2 | Goal2
	 * 
	 * The states are a combination of zone and direction, e.g. S{+2,-1} => zone Z{+2}, direction -1.
	 * Two additional states are needed for scoring: S{-1} => team2 has scored; S{+1} => team1 has scored.
	 * 
	 * Note that states S{+3,-1} and S{-3,+1} never occur under this model, so we may utilise
	 * S{-3,+1}=S{-1} and S{+3,-1}=S{+1}.
	 * 
	 * To compute indices, let z' = zone + 3 and d' = (1 - direction)/2. Then s' = 2 * z' + d'.
	 */ 
	public enum State {
		
		Team2Scores(-1), Team1GoldSquare(-3,-1), Team1GoalDefense(-2,+1), Team2GoalAttack(-2,-1), 
		Team1MidfieldDefense(-1,+1), Team2MidfieldAttack(-1,-1), 
		Team1Kickoff(0,+1), Team2Kickoff(0,-1), 
		Team1MidfieldAttack(+1,+1), Team2MidfieldDefemse(+1,-1), 
		Team1GoalAttack(+2,+1), Team2GoalDefense(+2,-1), Team2GoalSquare(+3,+1), Team1Scores(+1);
		
		public static final int NUM_STATES = values().length;
		
		public final int zone;
		public final int direction;
		public final boolean isGoalForTeam1;
		public final boolean isGoalForTeam2;

		State(int zone, int direction) {
			this.zone = zone;
			this.direction = direction;
			this.isGoalForTeam1 = false;
			this.isGoalForTeam2 = false;
		}
		
		State(int direction) {
			this.zone = 3 * direction;
			this.direction = -direction;
			this.isGoalForTeam1 = (direction == 1);
			this.isGoalForTeam2 = (direction == -1);
		}
		
		public static State getState(int zone, int direction) {
			int index = 2 * (zone + 3) + (1 - direction) / 2;
			return State.values()[index];
		}
		
		public State nextState(int direction) {
			if (isGoalForTeam1) return (direction > 0) ? Team2Kickoff : Team2GoalSquare;
			if (isGoalForTeam2) return (direction < 0) ? Team1Kickoff : Team1GoldSquare;
			int zone = this.zone + direction;
			int index = 2 * (zone + 3) + (1 - direction) / 2;
			if (index < 0) return Team2Scores;
			if (index >= NUM_STATES) return Team1Scores;
			return State.values()[index];
		}

	}

	public static void main(String[] args) {
		for (State s : State.values()) {
			// Test consistency of state lookup from knowledge of its parts.
			if (s != State.getState(s.zone, s.direction))
				throw new RuntimeException("Invalid state: " + s.name());
			State fs = s.nextState(+1), bs = s.nextState(-1); 
			System.out.printf(
			    "From state %s(%d,%d), right -> %s(%d,%d), left -> %s(%d,%d)\n",
			    s.name(), s.zone, s.direction, fs.name(), fs.zone, fs.direction,
			    bs.name(), bs.zone, bs.direction
			);
			if (!s.isGoalForTeam1 && !s.isGoalForTeam2) {
				// Test consistency of transitioning forward then backward.
				System.out.printf("Reciprocity: %s ->[+1] %s ->[-1] %s\n", s.name(), s.nextState(+1).name(), s.nextState(+1).nextState(-1).name());
				//if (s.nextState(+1).nextState(-1) != s)
				//	throw new RuntimeException("Non-reciprocal forward/backward transition from state: " + s.name());
				// Test consistency of transitioning backward then forward.
				System.out.printf("Reciprocity: %s ->[-1] %s ->[+1] %s\n", s.name(), s.nextState(-1).name(), s.nextState(-1).nextState(+1).name());
				//if (s.nextState(-1).nextState(+1) != s)
				//	throw new RuntimeException("Non-reciprocal backward/forward transition from state: " + s.name());
			}
		}
	}
	
}
