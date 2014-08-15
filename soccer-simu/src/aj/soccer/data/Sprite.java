package aj.soccer.data;

import java.awt.Image;

/**
 * Encapsulates a moveable sprite graphic.
 */
public interface Sprite {

    String getLabel();

    Image getImage();

    Coordinates getLocation();

}
