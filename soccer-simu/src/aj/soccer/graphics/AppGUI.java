package aj.soccer.graphics;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Provides the GUI for the application.
 */
@SuppressWarnings("serial")
public class AppGUI extends JFrame {

	private static final String BACKGROUND_IMG_PATH = "Soccer-pitch.jpg";

	public AppGUI() {
		add(initBackground());
		setJMenuBar(initMenus());
	}

	private JMenuBar initMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem dataItem = new JMenuItem("Data");
		fileMenu.add(dataItem);
		menuBar.add(fileMenu);
		return menuBar;
	}

	private JPanel initBackground() {
			JLabel label = new JLabel(ImageFactory.loadImage(BACKGROUND_IMG_PATH));
			JPanel panel = new JPanel();
			panel.add(label);
			return panel;
	}

}
