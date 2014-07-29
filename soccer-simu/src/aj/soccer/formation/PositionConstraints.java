package aj.soccer.formation;

import java.util.Arrays;

import aj.soccer.data.Constraints;
import aj.soccer.data.Formation;
import aj.soccer.data.Position;

/*package-private*/ class PositionConstraints implements Constraints<Position> {

	private final int[] maxCounts;
	private final int maxTotal;
	private final int[] counts = new int[Position.values().length];
	private int total = 0;

	/*package-private*/ PositionConstraints(Formation formation) {
		counts[Position.GoalKeeper.ordinal()] = 1;
		counts[Position.Defender.ordinal()] = formation.getDefenders().size();
		counts[Position.MidFielder.ordinal()] = formation.getMidFielders().size();
		counts[Position.Forward.ordinal()] = formation.getForwards().size();
		for (int count : counts) total += count;
		maxTotal = total;
		maxCounts = Arrays.copyOf(counts, counts.length);
	}

	@Override
	public int totalCapacity() {
		return total;
	}

	@Override
	public int capacity(Position position) {
		return counts[position.ordinal()];
	}

	@Override
	public boolean increment(Position position) {
		final int idx = position.ordinal();
		if (total >= maxTotal || counts[idx] >= maxCounts[idx])
			return false;
		counts[idx]++;
		total++; 
		return true;
	}

	@Override
	public boolean decrement(Position position) {
		final int idx = position.ordinal();
		if (total <= 0 || counts[idx] <= 0)
			return false;
		counts[idx]--;
		total--; 
		return true;
	}

}
