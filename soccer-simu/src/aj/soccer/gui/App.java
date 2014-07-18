package aj.soccer.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import aj.soccer.teams.Team;

/**
 * Provides the GUI for the application.
 */
public class App extends GenericGUIImpl implements MenusToApp, DisplayToApp {

	private final JFrame frame = new JFrame();
	private Display display;
	private Team team = null;

	public App() {
		setLookAndFeel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("AJ's Soccer Simulator");
		//setIconImage(ImageFactory.loadImageIcon(ICON_IMAGE_PATH));
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				throw new IllegalStateException(e1);
			}
		}
	}

	public void display() {
		display = new Display(this);
		frame.add(display.getPanel());
		Menus menuGUI = new Menus(this);
		frame.setJMenuBar(menuGUI.getMenuBar());
        frame.pack();
        //setSize(250,250);
        frame.setVisible(true);
	}

	public void setTeam(Team team) {
		if (this.team != null) undrawTeam(this.team);
		this.team = team;
		drawTeam(team);
	}

	private void drawTeam(Team team) {
		// TODO Auto-generated method stub
		
	}

	private void undrawTeam(Team team) {
		// TODO Auto-generated method stub
		
	}
}
