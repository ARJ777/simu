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
 * The state transitions are:
 * 
 * S{0,+1}  --> S{+1,+1} with P{a1;a2}=P(a1 beats a2);  OR S{0,+1}  --> S{-1,-1} with P{a2;a1}=1-P{a1;a2}.
 * S{+1,+1} --> S{+2,+1} with P{a1;m2}; 				OR S{+1,+1} --> S{0,-1}  with P{m2;a1}.
 * S{+2,+1} --> S{+3,+1} with P{a1;d2};					OR S{+2,+1} --> S{+1,-1} with P{d2;a1}.
 * S{+3,+1} --> S{+1}    with P{a1;g2};					OR S{+3,+1} --> S{+2,-1} with P{g2;a1}.
 * 
 * S{-1,+1} --> S{0,+1}  with P{m1;m2};					OR S{-1,+1} --> S{-2,-1} with P{m2;m1}.
 * S{-2,+1} --> S{-1,+1} with P{d1;a2};					OR S{-2,+1} --> S{-3,-1} with P{a2;d1}.
 * 
 * S{0,-1}  --> S{-1,-1} with P{a2;a1};					OR S{0,-1}  --> S{+1,+1} with P{a1;a2}.
 * S{-1,-1} --> S{-2,-1} with P{a2;m1};					OR S{-1,-1} --> S{0,+1}  with P{m1;a2}.
 * S{-2,-1} --> S{-3,-1} with P{a2;d1};					OR S{-2,-1} --> S{-1,+1} with P{d1;a2}.
 * S{-3,-1} --> S{-1}    with P{a2;g1};					OR S{-3,-1} --> S{-2,+1} with P{g1;a2}.
 * 
 * S{+1,-1} --> S{0,-1}  with P{m2;m1};					OR S{+1,-1} --> S{+2,+1} with P{m1;m2}.
 * S{+2,-1} --> S{+1,-1} with P{d2;a1};					OR S{+2,-1} --> S{+3,+1} with P{a1;d2}.
 * 
 * S{+1}    --> S{0,-1}.
 * S{-1}    --> S{0,+1}.
 * 
 * Note that states S{+3,-1} and S{-3,+1} never occur in practice.
 */
public class MarkovSimulator implements Simulator {
	
	public int[] simulateGame(Team team1, Team team2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void displayGame(int[] states) {
		// TODO Auto-generated method stub

	}

	public int[] summariseGame(int[] states) {
		// TODO Auto-generated method stub
		return null;
	}

}
