package aj.soccer.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Provides access to the file-based images.
 */
public abstract class ImageFactory {

	private static final String IMAGE_ROOT = "images/";
	private static final File IMAGE_ROOT_FILE = new File(IMAGE_ROOT);
	
	private ImageFactory() {};
	
	/**
	 * Loads an image icon from file.
	 * 
	 * @param path - The relative path to the image.
	 * @return The image icon.
	 */
	public static ImageIcon loadImageIcon(String path) {
		return new ImageIcon(IMAGE_ROOT + path);
	}

	/**
	 * Loads an image from file.
	 * 
	 * @param path - The relative path to the image.
	 * @return The image.
	 */
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(new File(IMAGE_ROOT_FILE, path));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
