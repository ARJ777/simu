package aj.soccer.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPitch2 extends JFrame {

	private static final String IMG_PATH = "images/Soccer-pitch.jpg";
	private final JPanel panel = new JPanel();

	public DrawPitch2() throws IOException {
		BufferedImage img = ImageIO.read(new File(IMG_PATH));
		ImageIcon icon = new ImageIcon(img);
		JLabel label = new JLabel(icon);
		panel.add(label);
		add(panel);
	}
	public static void main(String[] args) throws IOException
	{
		DrawPitch2 pitch = new DrawPitch2();
		pitch.pack();
		pitch.setVisible(true);
	}

}
