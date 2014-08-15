package aj.soccer.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import aj.soccer.data.Sprite;
import aj.soccer.data.SpriteManager;
import aj.soccer.images.ImageFactory;

/**
 * Encapsulates the background soccer pitch, and the drawing of all player sprites.
 */
public class Display implements SpriteManager {

    private static final String BACKGROUND_IMG_PATH = "Soccer-pitch-horiz.jpg";
    private final JPanel panel;
    /** Circular reference to main GUI for call-backs. */
    private final DisplayToApp appGUI;
    private final List<Sprite> sprites = new ArrayList<>();

    public Display(DisplayToApp appGUI) {
	this.appGUI = appGUI;
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

    @Override
    public void addSprite(Sprite sprite) {
	if (!sprites.contains(sprite)) {
	    sprites.add(sprite);
	}
    }

    @Override
    public void removeSprite(Sprite sprite) {
	sprites.remove(sprite);
    }

}
