package aj.soccer.main;

import javax.swing.SwingUtilities;

import aj.soccer.gui.App;

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
        App f = new App();
        f.display();
    }

}
