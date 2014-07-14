package aj.soccer.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Provides access to the file-based images.
 */
public abstract class ImageFactory {

	private static final File ROOT = new File("images");
	
	private ImageFactory() {};
	
	/**
	 * Loads an image icon from file.
	 * 
	 * @param path - The relative path to the image.
	 * @return The image icon.
	 */
	public static ImageIcon loadImage(String path) {
		try {
			BufferedImage img = ImageIO.read(new File(ROOT, path));
			return new ImageIcon(img);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
