package aj.soccer.gui;

import java.awt.Component;
import java.awt.Graphics;
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
	panel = new DisplayPanel();
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
	    drawSprite(sprite);
	}
    }

    private void drawSprite(Sprite sprite) {
    }

    @Override
    public void removeSprite(Sprite sprite) {
	if (sprites.remove(sprite)) {
	    undrawSprite(sprite);
	}
    }

    private void undrawSprite(Sprite sprite) {
	// TODO Auto-generated method stub

    }

    //===============================================
    @SuppressWarnings("serial")
    private class DisplayPanel extends JPanel {

	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Display.this.paintComponent(g);
	}

    }

    public void paintComponent(Graphics g) {
	for (Sprite sprite : sprites) {
	    drawSprite(g, sprite);
	}
    }

    private void drawSprite(Graphics g, Sprite sprite) {
	// TODO Auto-generated method stub

    }

}
