package aj.soccer.formation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import aj.soccer.data.Coordinates;
import aj.soccer.data.Formation;
import aj.soccer.data.Position;

/*package-private*/ class FormationImpl implements Formation {

	private static final int NUM_POSITIONS = 11;

	private Map<Position, List<Coordinates>> map;

	/*package-private*/ public FormationImpl(Map<Position, List<Coordinates>> map) {
		this.map = map;
		final int numGoalKeepers = count(map, Position.GoalKeeper);
		if (numGoalKeepers != 1)
			throw new IllegalArgumentException("Found " + numGoalKeepers + " goal-keepers - require exactly 1");
		final int numDefenders = count(map, Position.Defender);
		if (numDefenders < 2)
			throw new IllegalArgumentException("Found " + numDefenders + " defender(s) - require at least 2");
		final int numMidFielders = count(map, Position.MidFielder);
		if (numMidFielders < 2)
			throw new IllegalArgumentException("Found " + numMidFielders + " mid-fielder(s) - require at least 2");
		final int numForwards = count(map, Position.Forward);
		if (numForwards < 1)
			throw new IllegalArgumentException("Found 0 forwards - require at least 1");
		final int numPositions = numGoalKeepers + numDefenders + numMidFielders + numForwards;
		if (numPositions != NUM_POSITIONS)
			throw new IllegalArgumentException("Found " + numPositions + " positions - require exactly " + NUM_POSITIONS);
	}
	
	private static int count(Map<Position, List<Coordinates>> map, Position position) {
		List<Coordinates> fieldPositions = map.get(position);
		return (fieldPositions == null) ? 0 : fieldPositions.size();
	}

	@Override
	public Coordinates getGoalKeeper() {
		return map.get(Position.GoalKeeper).get(0);
	}

	@Override
	public List<Coordinates> getDefenders() {
		return Collections.unmodifiableList(map.get(Position.Defender));
	}

	@Override
	public List<Coordinates> getMidFielders() {
		return Collections.unmodifiableList(map.get(Position.MidFielder));
	}

	@Override
	public List<Coordinates> getForwards() {
		return Collections.unmodifiableList(map.get(Position.Forward));
	}

}
