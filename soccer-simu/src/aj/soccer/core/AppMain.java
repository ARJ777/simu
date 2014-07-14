package aj.soccer.core;

import javax.swing.JFrame;
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
        JFrame f = new AppGUI();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        //f.setSize(250,250);
        f.setVisible(true);
    }
    
}
