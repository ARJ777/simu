package aj.soccer.data;

/**
 * Specify constraints on the numbers of grouped items.
 */
public interface Constraints<T> {

	/**
	 * Indicates the total number of items in all groups.
	 * 
	 * @return The overall capacity.
	 */
	int totalCapacity();
	
	/**
	 * Indicates the number of items in the specified group.
	 * 
	 * @param group - The group.
	 * @return The group capacity.
	 */
	int capacity(T group);

	/**
	 * Attempts to increment the capacity of the given group.
	 * 
	 * @param group - The group.
	 * @return A value of true (or false) if the capacity 
	 * was (or was not) incremented. 
	 */
	boolean increment(T group);
	
	/**
	 * Attempts to decrement the capacity of the given group.
	 * 
	 * @param group - The group.
	 * @return A value of true (or false) if the capacity 
	 * was (or was not) decremented. 
	 */
	boolean decrement(T group);
	
}
