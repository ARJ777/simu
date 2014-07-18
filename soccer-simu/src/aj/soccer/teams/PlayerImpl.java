package aj.soccer.teams;

import java.util.Collections;
import java.util.List;

/*package-private*/ class PlayerImpl implements Player {

	private final String playerName;
	private final List<Position> allowedPositions;
	private boolean isActive = false;
	private Position position = null;

	/*package-private*/ PlayerImpl(String playerName, List<Position> allowedPositions) {
		this.playerName = playerName;
		this.allowedPositions = Collections.unmodifiableList(allowedPositions);
	}

	@Override
	public String getName() {
		return playerName;
	}

	@Override
	public List<Position> getAllowedPositions() {
		return allowedPositions;
	}

	@Override
	public String toString() {
		return playerName;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive(boolean flag) {
		isActive = flag;
	}

	@Override
	public Position getPosition() {
		if (position == null)
			throw new IllegalStateException("No position set for player: " + playerName);
		return position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

}
