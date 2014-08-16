package aj.soccer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import aj.soccer.data.Team;
import aj.soccer.formation.FormationFactory;
import aj.soccer.team.TeamFactory;

/**
 * Encapsulates the application menus.
 */
public class Menus implements ActionListener {

	private static final String REFRESH_DATA = "Refresh Data";
	private static final String SELECT_TEAM = "Select Team";
	private static final String SELECT_OPPONENT_TEAM = "Select Opponent";

	/** Circular reference to main GUI for call-backs. */
	private final MenusToApp appGUI;

	public Menus(MenusToApp appGUI) {
		this.appGUI = appGUI;
	}

	/**
	 * Initialises the menu bar and any menu item listeners.
	 * 
	 * @return The initialised menu bar.
	 */
	public JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getFileMenu());
		menuBar.add(getOpponentMenu());
		menuBar.add(getCoachMenu());
		menuBar.add(getTeamsMenu());
		menuBar.add(getStartMenu());
		return menuBar;
	}

	private JMenu getFileMenu() {
		JMenu menu = new JMenu("File");
		JMenuItem refreshTeamsMenu = new JMenuItem(REFRESH_DATA);
		refreshTeamsMenu.addActionListener(this);
		menu.add(refreshTeamsMenu);
		return menu;
	}

	private JMenu getOpponentMenu() {
		JMenu menu = new JMenu("Opponent Data");
		JMenuItem selectTeamMenu = new JMenuItem(SELECT_OPPONENT_TEAM);
		selectTeamMenu.addActionListener(this);
		menu.add(selectTeamMenu);
		return menu;
	}

	private JMenu getCoachMenu() {
		JMenu menu = new JMenu("Coach's Advice");
		return menu;
	}

	private JMenu getTeamsMenu() {
		JMenu menu = new JMenu("Team Management");
		JMenuItem selectTeamMenu = new JMenuItem(SELECT_TEAM);
		selectTeamMenu.addActionListener(this);
		menu.add(selectTeamMenu);
		return menu;
	}

	private JMenu getStartMenu() {
		JMenu startMenu = new JMenu("Start Game");
		return startMenu;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.printf("Event performed: %s%n", event);
		final String command = event.getActionCommand();
		if (command == SELECT_TEAM) {
			String teamName = appGUI.selectOne("Select a team", TeamFactory.getTeamNames());
			if (teamName != null)
				appGUI.setTeam(TeamFactory.loadTeam(teamName));
		} else if (command == REFRESH_DATA) {
			TeamFactory.refreshTeams();
			FormationFactory.refreshFormations();
		} else if (command == SELECT_OPPONENT_TEAM) {
			String teamName = appGUI.selectOne("Select a team", TeamFactory.getTeamNames());
			if (teamName != null)
				appGUI.setOpponentTeam(TeamFactory.loadTeam(teamName));
		}
	}

}
