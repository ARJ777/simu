package aj.soccer.data;

import java.util.List;

/**
 * Describes a single player in a team.
 */
public interface Player {

    /**
     * Obtains the player's full name.
     *
     * @return The player's name.
     */
    String getName();

    /**
     * Specifies all of the on-field positions this player may hold.
     *
     * @return An unmodifiable list of positions.
     */
    List<Position> getAllowedPositions();

    /**
     * Obtains the on-field position held by this player.
     *
     * @return The player's position.
     * @throws IllegalStateException If the player has no assigned position.
     */
    Position getPosition();

    /**
     * Sets the on-field position held by this player.
     *
     * @param position - The position.
     */
    void setPosition(Position position);

    /**
     * Indicates whether or not the player is actively selected on the field.
     *
     * @return A value of true (or false) if the player is (or is not) active.
     */
    boolean isActive();

    /**
     * Sets the active status of the player to the specified value.
     *
     * @param flag - A value of true (or false) if the player is (or is not) active.
     */
    void setActive(boolean flag);

    /**
     * Indicates whether or not the player may be selected to play on the field.
     *
     * @return A value of true (or false) if the player may (or may not) become active.
     */
    boolean isSelectable();

    /**
     * Sets the selectability status of the player to the specified value.
     *
     * @param flag - A value of true (or false) if the player may (or may not) become active.
     */
    void setSelectable(boolean flag);

    /**
     * Obtains the player's current location.
     *
     * @return The location.
     */
    Coordinates getLocation();

    /**
     * Sets the player's current location.
     *
     * @param loc - The location.
     */
    void setLocation(Coordinates loc);

    /**
     * Obtains the graphical representation of the player.
     *
     * @return The player sprite.
     */
    Sprite getSprite();

    /**
     * Obtains a label identifying the player image.
     * 
     * @return The image label, or a value of null if no preferred image has been
     * specified.
     */
	/*@Nullable*/ String getImageLabel();

	/**
	 * Sets the label identifying the player image.
	 * 
	 * @param imgLabel - The image label, or a value of null if no
	 * particular image is preferred.
	 */
	void setImageLabel(/*@Nullable*/ String imgLabel);

}
