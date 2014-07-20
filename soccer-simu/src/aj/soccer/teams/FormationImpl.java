package aj.soccer.teams;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/*package-private*/ class FormationImpl implements Formation {

	private Map<Position, List<Coordinates>> map;

	/*package-private*/ public FormationImpl(Map<Position, List<Coordinates>> map) {
		this.map = map;
		check(map.get(Position.GoalKeeper), 1, 1, "Need exactly one gold keeper");
		check(map.get(Position.Defender), 2, Integer.MAX_VALUE, "Need at least two defenders");
		check(map.get(Position.MidFielder), 2, Integer.MAX_VALUE, "Need at least two mid-fielders");
		check(map.get(Position.Forward), 1, Integer.MAX_VALUE, "Need at least one forward");
	}
	
	private void check(/*@Nullable*/ List<Coordinates> list, int minSize, int maxSize,
			String errMsg) {
		if (list == null || list.size() < minSize || list.size() > maxSize)
			throw new IllegalStateException(errMsg);
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
