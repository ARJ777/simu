package aj.soccer.team;

import java.awt.Image;
import java.util.Collections;
import java.util.List;

import aj.soccer.data.Coordinates;
import aj.soccer.data.Player;
import aj.soccer.data.Position;
import aj.soccer.data.Sprite;
import aj.soccer.images.ImageFactory;

/*package-private*/ class PlayerImpl implements Player {

    private final String playerName;
    private final List<Position> allowedPositions;
    private boolean isActive = false;
    private boolean isSelectable = true;
    private Position position = null;
    private Coordinates location = null;
    private final Sprite sprite = new SpriteImpl();
    private Image image = null;
    private String imgNum;

    /*package-private*/ PlayerImpl(String playerName, List<Position> allowedPositions, String imgNum) {
	this.playerName = playerName;
	this.imgNum = imgNum;
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
	if (position == null) {
	    throw new IllegalStateException("No position set for player: " + playerName);
	}
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

    @Override
    public Sprite getSprite() {
	return sprite;
    }

    private Image getImage() {
	if (image == null) {
	    image = ImageFactory.getPlayerImage(imgNum);
	}
	return image;
    }

    //============================================================
    private static final Coordinates OFF_SCREEN_COORDINATES = new Coordinates() {

	@Override
	public double getY() {
	    return -1;
	}

	@Override
	public double getX() {
	    return -1;
	}
    };

    private class SpriteImpl implements Sprite {

	@Override
	public String getLabel() {
	    return playerName;
	}

	@Override
	public Image getImage() {
	    return PlayerImpl.this.getImage();
	}

	@Override
	public Coordinates getLocation() {
	    return (location == null) ? OFF_SCREEN_COORDINATES : location;
	}

    }

	@Override
	public String getImageLabel() {
		return imgNum;
	}

	@Override
	public void setImageLabel(String imgLabel) {
		this.imgNum = imgLabel;
	}

}
