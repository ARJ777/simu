package aj.soccer.team;

import java.util.Collections;
import java.util.List;

import aj.soccer.data.Coordinates;
import aj.soccer.data.Player;
import aj.soccer.data.Position;

/*package-private*/ class PlayerImpl implements Player {

	private final String playerName;
	private final List<Position> allowedPositions;
	private boolean isActive = false;
	private boolean isSelectable = true;
	private Position position = null;
	private Coordinates location = null;

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

	@Override
	public boolean isSelectable() {
		return isSelectable;
	}

	@Override
	public void setSelectable(boolean flag) {
		isSelectable = flag;
	}

	@Override
	public Coordinates getLocation() {
		return location;
	}

	@Override
	public void setLocation(Coordinates loc) {
		location = loc;
	}

}
