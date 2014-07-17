package aj.soccer.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import aj.soccer.teams.Team;
import aj.soccer.teams.TeamFactory;

/**
 * Encapsulates the application menus.
 */
public class MenuGUI implements ActionListener {

	private static final String SELECT_TEAM = "Select Team";

	/** Circular reference to main GUI for call-backs. */
	private final MenuToAppGUI appGUI;

	public MenuGUI(MenuToAppGUI appGUI) {
		this.appGUI = appGUI;
	}

	/**
	 * Initialises the menu bar and any menu item listeners.
	 * 
	 * @return The initialised menu bar.
	 */
	public JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu teamsMenu = new JMenu("Teams");
		JMenuItem selectTeamMenu = new JMenuItem(SELECT_TEAM);
		selectTeamMenu.addActionListener(this);
		teamsMenu.add(selectTeamMenu);
		menuBar.add(teamsMenu);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.printf("Event performed: %s%n", event);
		if (event.getActionCommand() == SELECT_TEAM) {
			String teamName = appGUI.selectOne("Select a team", TeamFactory.getTeamNames());
			Team team = (teamName == null) ? null : TeamFactory.loadTeam(teamName);
			appGUI.setTeam(team);
		}
	}

}
