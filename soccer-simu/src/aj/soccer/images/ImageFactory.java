package aj.soccer.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Provides access to the file-based images.
 */
public abstract class ImageFactory {

	private static final String IMAGE_ROOT = "images/";
	private static final File IMAGE_ROOT_DIR = new File(IMAGE_ROOT);
	private static final File PLAYER_IMAGE_DIR = new File(IMAGE_ROOT_DIR, "players");
	private static final Map<String, Image> playerImages = new HashMap<>();
	private static final String PLAYER_IMAGE_PREFIX = "Player";
	private static final String PLAYER_IMAGE_SUFFIX = ".png";

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
	 * @param path - The absolute path to the image.
	 * @return The image.
	 */
	public static BufferedImage loadImage(File path) {
		try {
			return ImageIO.read(path);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Image getPlayerImage(/*@Nullable*/ String imgNum) {
		if (imgNum == null) {
			return selectPlayerImage();
		}
		Image image = playerImages.get(imgNum);
		if (image == null) {
			image = loadPlayerImage(imgNum);
			playerImages.put(imgNum, image);
		}
		return image;
	}

	private static Image loadPlayerImage(String imgNum) {
		return loadImage(new File(PLAYER_IMAGE_DIR, PLAYER_IMAGE_PREFIX + imgNum + PLAYER_IMAGE_SUFFIX));
	}

	private static Image selectPlayerImage() {
		// TODO Randomly select an image from the directory.
		return loadPlayerImage("1");
	}

	public static Image loadImage(String imageFile) {
		return loadImage(new File(IMAGE_ROOT_DIR, imageFile));
	}
}
