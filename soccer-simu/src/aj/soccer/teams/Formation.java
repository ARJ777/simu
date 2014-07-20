package aj.soccer.teams;

import java.util.List;

/**
 * Specifies the locations and numbers of players filling various positions
 * on the field.
 */
public interface Formation {

	/**
	 * Obtains the location of the goal keeper.
	 * 
	 * @return The goal keeper's coordinates.
	 */
	Coordinates getGoalKeeper();
	
	/**
	 * Obtains the locations of all defenders.
	 * 
	 * @return The defenders' coordinates.
	 */
	List<Coordinates> getDefenders();
	
	/**
	 * Obtains the locations of all mid-fielders.
	 * 
	 * @return The mid-fielders' coordinates.
	 */
	List<Coordinates> getMidFielders();

	/**
	 * Obtains the locations of all forwards.
	 * 
	 * @return The forwards' coordinates.
	 */
	List<Coordinates> getForwards();

}
