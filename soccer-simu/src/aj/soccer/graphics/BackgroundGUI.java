package aj.soccer.graphics;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Encapsulates the background soccer pitch, and the drawing of all player sprites.
 */
public class BackgroundGUI {

	private static final String BACKGROUND_IMG_PATH = "Soccer-pitch.jpg";
	private final JPanel panel;

	public BackgroundGUI() {
		panel = new JPanel();
		JLabel label = new JLabel(ImageFactory.loadImageIcon(BACKGROUND_IMG_PATH));
		panel.add(label);
	}

	/**
	 * Initialises the background image and any mouse listeners.
	 * 
	 * @return An initialised background panel.
	 */
	public Component getPanel() {
		return panel;
	}
}
