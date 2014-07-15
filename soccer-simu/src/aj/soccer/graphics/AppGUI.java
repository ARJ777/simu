package aj.soccer.graphics;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Provides the GUI for the application.
 */
@SuppressWarnings("serial")
public class AppGUI extends JFrame {

	private static final String ICON_IMAGE_PATH = "Soccer_ball.jpg";
	
	private BackgroundGUI background;

	public AppGUI() {
		setLookAndFeel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("AJ's Soccer Simulator");
		//setIconImage(ImageFactory.loadImage(ICON_IMAGE_PATH));
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				throw new IllegalStateException(e1);
			}
		}
	}

	public void display() {
		background = new BackgroundGUI();
		add(background.getPanel());
		MenuGUI menuGUI = new MenuGUI();
		setJMenuBar(menuGUI.getMenuBar());
        pack();
        //setSize(250,250);
        setVisible(true);
	}
    
}
