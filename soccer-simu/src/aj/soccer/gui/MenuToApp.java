package aj.soccer.gui;

import java.util.List;

import aj.soccer.teams.Team;

/**
 * Specifies the interface for call-backs to the main application from the menu component.
 */
public interface MenuToApp {

	/**
	 * Displays a dialog to select one of a list of items.
	 * 
	 * @param title - The title of the dialog box.
	 * @param selections - The list of possible selections.
	 * @return A single selection, or a value of null if no selection has been made.
	 */
	<T> /*@Nullable*/ T selectOne(String title, List<T> selections);

	/**
	 * Specifies the user's team.
	 * 
	 * @param team - The new team.
	 */
	void setTeam(Team team);

}
