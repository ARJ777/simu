package aj.soccer.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import aj.soccer.data.Constraints;
import aj.soccer.data.Formation;
import aj.soccer.data.Player;
import aj.soccer.data.Position;
import aj.soccer.data.Team;
import aj.soccer.formation.FormationFactory;

/**
 * Represents a team of players, each of whom can only play
 * in a single, randomly generated position, consistent with
 * the given formation.
 */
/*package-private*/ class UnambiguousTeam implements Team {

	private static final Random generator = new Random();
	
	private final String name;
	private final Formation formation;
	private final List<Player> players;
	
	/*package-private*/ UnambiguousTeam(String name, Formation formation, final int teamSize) {
		this.name = name;
		this.formation = formation;
		Constraints<Position> constraints =
				FormationFactory.getConstraints(formation);
		if (teamSize < constraints.totalCapacity())
			throw new IllegalArgumentException("Too few players");
		List<Player> _players = new ArrayList<>(teamSize);
		int i = 0;
		while (constraints.totalCapacity() > 0) {
			Position position = generatePosition(constraints);
			_players.add(new UnambiguousPlayer("Player" + (++i), position));
		}
		while (i < teamSize) {
			Position position = generatePosition();
			_players.add(new UnambiguousPlayer("Player" + (++i), position));
		}
		players = Collections.unmodifiableList(_players);
	}
	
	private static final int NUM_POSITIONS = Position.values().length;

	private Position generatePosition(Constraints<Position> constraints) {
		while (true) {
			int idx = generator.nextInt(NUM_POSITIONS);
			Position position = Position.values()[idx];
			if (constraints.decrement(position))
				return position;
		}
	}

	private Position generatePosition() {
		while (true) {
			int idx = generator.nextInt(NUM_POSITIONS);
			Position position = Position.values()[idx];
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public Formation getFormation() {
		return formation;
	}

	@Override
	public void setFormation(Formation formation) {}

}
