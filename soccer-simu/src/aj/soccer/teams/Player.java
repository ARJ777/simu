package aj.soccer.teams;

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
	 * Indicates whether or not the player is actively selected, e.g. on the field.
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
}
