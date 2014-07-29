package aj.soccer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import aj.soccer.data.Constraints;
import aj.soccer.data.Formation;
import aj.soccer.data.Player;
import aj.soccer.data.Position;
import aj.soccer.data.Team;
import aj.soccer.formation.AssignmentFactory;
import aj.soccer.formation.FormationFactory;

public class AssignmentTest {

	@Test
	public void testMinimumUnambiguousTeam() {
		Formation formation = new TestFormation1();
		Team team = new UnambiguousTeam("Team1", formation, 11);
		List<Player> players = team.getPlayers();
		Constraints<Position> constraints = 
				FormationFactory.getConstraints(formation);
		Map<Player, Position> assignments =
				AssignmentFactory.assignPlayerPositions(constraints, players);
		assertNotNull(assignments);
		assertEquals(11, assignments.size());
	}

}
