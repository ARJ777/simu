package aj.soccer.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import aj.soccer.data.Coordinates;
import aj.soccer.data.Player;
import aj.soccer.data.Position;
import aj.soccer.data.Sprite;

/**
 * Represents a player who can only play in a single, given position.
 */
/*package-private*/ class UnambiguousPlayer implements Player {

    private final String name;
    private final List<Position> positions;
    private Position position = null;
    private boolean isActive = false;
    private Coordinates location = null;

    /*package-private*/ public UnambiguousPlayer(
	    String name, Position position)
    {
	this.name = name;
	positions = Collections.unmodifiableList(Arrays.asList(position));
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public List<Position> getAllowedPositions() {
	return positions;
    }

    @Override
    public Position getPosition() {
	return position;
    }

    @Override
    public void setPosition(Position position) {
	this.position = position;
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
    public boolean isSelectable() {
	return true;
    }

    @Override
    public void setSelectable(boolean flag) {}

    @Override
    public Coordinates getLocation() {
	return location;
    }

    @Override
    public void setLocation(Coordinates loc) {
	this.location = loc;
    }

    @Override
    public Sprite getSprite() {
	// TODO Auto-generated method stub
	return null;
    }

}
