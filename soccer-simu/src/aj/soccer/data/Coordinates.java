package aj.soccer.data;

/**
 * Specifies horizontal and vertical coordinates, relative to the
 * bottom left-hand corner of a normalised rectangle.
 */
public interface Coordinates {

	/**
	 * Obtains the scaled [0,1] horizontal distance from coordinate (0,0).
	 * 
	 * @return The scaled x-value.
	 */
	double getX();
	
	/**
	 * Obtains the scaled [0,1] vertical distance from coordinate (0,0).
	 * 
	 * @return The scaled y-value.
	 */
	double getY();
	
}
