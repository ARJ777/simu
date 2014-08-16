package aj.soccer.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import aj.soccer.data.Coordinates;
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
		if (sprites.add(sprite)) {
			panel.repaint();
		}
	}

	@Override
	public void removeSprite(Sprite sprite) {
		if (sprites.remove(sprite)) {
			panel.repaint();
		}
	}

	@Override
	public void addSprites(Collection<Sprite> sprites) {
		if (this.sprites.addAll(sprites))
			panel.repaint();
	}

	@Override
	public void removeSprites(Collection<Sprite> sprites) {
		if (this.sprites.removeAll(sprites))
			panel.repaint();
	}

	//===============================================
	@SuppressWarnings("serial")
	private class DisplayPanel extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Display.this.drawSprites(g);
		}

	}

	public void drawSprites(Graphics g) {
		for (Sprite sprite : sprites) {
			drawSprite(g, sprite);
		}
	}

	private void drawSprite(Graphics g, Sprite sprite) {
		Coordinates location = sprite.getLocation();
		if (location == null) return;
		double x = location.getX();
		double y = location.getY();
		if (x < 0 || x > 1 || y < 0 || y > 1) return;
		Rectangle bounds = panel.getBounds();
		x = bounds.getMinX() + x * bounds.getWidth();
		y = bounds.getMinY() + y * bounds.getHeight();
		System.out.printf("Sprite @ (%d, %d)%n", (int) x, (int) y);
		g.drawImage(sprite.getImage(), (int) x, (int) y, null);
	}

}
