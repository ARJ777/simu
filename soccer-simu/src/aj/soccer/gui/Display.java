package aj.soccer.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
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

	private static final File BACKGROUND_IMAGE_FILE = new File("images/Soccer-pitch-horiz.jpg");
	private static final Image BACKGROUND_IMAGE = ImageFactory.loadImage(BACKGROUND_IMAGE_FILE);

	private final JPanel panel;
	/** Circular reference to main GUI for call-backs. */
	private final DisplayToApp appGUI;
	private final List<Sprite> sprites = new ArrayList<>();

	public Display(DisplayToApp appGUI) {
		this.appGUI = appGUI;
		panel = new DisplayPanel();
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
			//g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
			g.drawImage(
					BACKGROUND_IMAGE, 
					0, 0, panel.getWidth(), panel.getHeight(),
					0, 0, BACKGROUND_IMAGE.getWidth(null), BACKGROUND_IMAGE.getHeight(null),
					null);
			Display.this.drawSprites(g);
		}

	}

	public void drawSprites(Graphics g) {
		for (Sprite sprite : sprites) {
			drawSprite(g, sprite);
		}
	}

	private static final double WIDTH_SCALE = 0.01;
	private static final double HEIGHT_SCALE = 0.01;
	
	private void drawSprite(Graphics g, Sprite sprite) {
		Coordinates location = sprite.getLocation();
		if (location == null) return;
		double x = location.getX();
		double y = location.getY();
		if (x < 0 || x > 1 || y < 0 || y > 1) return;
		Rectangle bounds = panel.getBounds();
		int width = (int) (WIDTH_SCALE * bounds.getWidth());
		int height = (int) (HEIGHT_SCALE * bounds.getHeight());
		int x0 = (int) (bounds.getMinX() + x * bounds.getWidth()) - width / 2;
		int y0 = (int) (bounds.getMinY() + y * bounds.getHeight()) - height / 2;
		System.out.printf("Sprite @ (%d, %d)%n", x0, y0);
		g.setColor(Color.RED);
		g.fillOval(x0, y0, width, height);
		Image image = sprite.getImage();
		g.drawImage(
				image, 
				x0, y0, x0 + width, y0 + width,
				0, 0, image.getWidth(null), image.getHeight(null), null);
	}

}
