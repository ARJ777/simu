package aj.soccer.gui;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import aj.soccer.data.Coordinates;
import aj.soccer.data.Formation;
import aj.soccer.data.Player;
import aj.soccer.data.Sprite;
import aj.soccer.data.Team;
import aj.soccer.images.ImageFactory;
import aj.soccer.team.TeamFactory;

/**
 * Provides the GUI for the application.
 */
public class App extends GenericGUIImpl implements MenusToApp, DisplayToApp, UncaughtExceptionHandler {

	private static final String ICON_IMAGE_FILE = "soccer-ball.jpg";

	private Display display;
	private Team team1 = null;
	private Formation formation1 = null;
	private Team team2 = null;
	private Formation formation2 = null;

	public App() {
		setLookAndFeel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("AJ's Soccer Simulator");
		frame.setIconImage(ImageFactory.loadImage(ICON_IMAGE_FILE));
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
		frame.setSize(1200,700);
		frame.setVisible(true);
	}

	@Override
	public void setTeam(Team team) {
		if (team1 != null)
			undrawTeam(team1);
		team1 = team;
		if (team != null) {
			setDefaultPlayerImage(team.getPlayers(), "1");
			formation1 = team.getFormation();
			TeamFactory.selectPlayers(team, formation1);
			drawTeam(team);
		} else {
			formation1 = null;
		}
	}

	private void setDefaultPlayerImage(List<Player> players, String imgNum) {
		for (Player player : players) {
			String _imgNum = player.getImageLabel();
			if (_imgNum == null)
				player.setImageLabel(imgNum);
		}
	}

	@Override
	public void setOpponentTeam(Team team) {
		if (team2 != null)
			undrawTeam(team2);
		team2 = team;
		if (team != null) {
			setDefaultPlayerImage(team.getPlayers(), "2");
			formation2 = team.getFormation();
			TeamFactory.selectPlayers(team, formation2);
			reverseLocations(team.getActivePlayers());
			drawTeam(team);
		} else {
			formation2 = null;
		}
	}

	private void reverseLocations(List<Player> activePlayers) {
		for (Player player : activePlayers) {
			Coordinates location = player.getLocation();
			if (location == null)
				throw new IllegalStateException("Active player with no location: " + player);
			final double x = 1 - location.getX();
			final double y = location.getY();
			player.setLocation(new Coordinates() {
				@Override
				public double getX() {
					return x;
				}

				@Override
				public double getY() {
					return y;
				}
			});
		}
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
