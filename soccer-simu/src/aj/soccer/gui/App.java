package aj.soccer.gui;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import aj.soccer.data.Player;
import aj.soccer.data.Sprite;
import aj.soccer.data.Team;

/**
 * Provides the GUI for the application.
 */
public class App extends GenericGUIImpl implements MenusToApp, DisplayToApp, UncaughtExceptionHandler {

	private Display display;
	private Team team = null;
	private Team opponent = null;

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
		Thread.setDefaultUncaughtExceptionHandler(this);
		display = new Display(this);
		frame.add(display.getPanel());
		Menus menuGUI = new Menus(this);
		frame.setJMenuBar(menuGUI.getMenuBar());
		frame.pack();
		//setSize(250,250);
		frame.setVisible(true);
	}

	@Override
	public void setTeam(Team team) {
		this.team = _setTeam(this.team, team);
	}

	private Team _setTeam(Team oldTeam, Team newTeam) {
		if (oldTeam != null) {
			undrawTeam(oldTeam);
		}
		if (newTeam != null) {
			drawTeam(newTeam);
		}
		return newTeam;
	}

	@Override
	public void setOpponentTeam(Team team) {
		this.opponent = _setTeam(this.opponent, team);
	}

	private void drawTeam(Team team) {
		List<Sprite> sprites = new LinkedList<>();
		for (Player player : team.getActivePlayers()) {
			sprites.add(player.getSprite());
		}
		display.addSprites(sprites);
	}

	private void undrawTeam(Team team) {
		List<Sprite> sprites = new LinkedList<>();
		for (Player player : team.getPlayers()) {
			sprites.add(player.getSprite());
		}
		display.removeSprites(sprites);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		displayError("Exception", e.toString());
	}
}
