package aj.soccer.core;

import javax.swing.SwingUtilities;

import aj.soccer.graphics.AppGUI;

/**
 * Launches the application.
 *
 */
public class AppMain {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        AppGUI f = new AppGUI();
        f.display();
    }

}
