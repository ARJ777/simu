package aj.soccer.graphics;

import java.util.List;

/**
 * Specifies generic GUI functionality.
 */
public interface GenericGUI {

	/**
	 * Displays a dialog allowing the user to select zero or one of the
	 * listed items.
	 * 
	 * @param title - The title of the dialog.
	 * @param selections - The list of items.
	 * @return The selected item, or a value of null if no selection has been made.
	 */
	public <T> /*@Nullable*/ T selectOne(String title, List<T> selections);

}
