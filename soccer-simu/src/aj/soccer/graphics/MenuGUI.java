package aj.soccer.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Encapsulates the application menus.
 */
public class MenuGUI implements ActionListener {

	public MenuGUI() {}

	/**
	 * Initialises the menu bar and any menu item listeners.
	 * 
	 * @return The initialised menu bar.
	 */
	public JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu teamsMenu = new JMenu("Teams");
		JMenuItem selectTeamMenu = new JMenuItem("Select Team");
		selectTeamMenu.addActionListener(this);
		teamsMenu.add(selectTeamMenu);
		menuBar.add(teamsMenu);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.printf("Event performed: %s%n", event);
	}

}
