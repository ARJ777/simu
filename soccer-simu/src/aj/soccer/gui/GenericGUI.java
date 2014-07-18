package aj.soccer.gui;

import java.util.Collection;
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

	/**
	 * Displays a dialog allowing the user to select zero, one or more of the
	 * listed items.
	 * 
	 * @param title - The title of the dialog.
	 * @param selections - The list of selectable items.
	 * @param selected - An array of indices of initially selected items.
	 * @return An array of indices of selected items, 
	 * or a value of null if the dialog was closed or cancelled. 
	 */
	public <T> int/*@Nullable*/[] selectMany(String title, List<T> selections, int[] selected);

	/**
	 * Displays an error dialog.
	 * 
	 * @param title - The title of the dialog.
	 * @param message - The error message.
	 */
	public void displayError(String title, String message);

}
