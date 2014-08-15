package painting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SwingPaintDemo3 {

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		createAndShowGUI();
	    }
	});
    }

    private static void createAndShowGUI() {
	System.out.println("Created GUI on EDT? "+
		SwingUtilities.isEventDispatchThread());
	JFrame f = new JFrame("Swing Paint Demo");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.add(new MyPanel3());
	f.pack();
	f.setVisible(true);
    }
}

class MyPanel3 extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    public MyPanel3() {

	setBorder(BorderFactory.createLineBorder(Color.black));

	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		moveSquare(e.getX(),e.getY());
	    }
	});

	addMouseMotionListener(new MouseAdapter() {
	    @Override
	    public void mouseDragged(MouseEvent e) {
		moveSquare(e.getX(),e.getY());
	    }
	});

    }

    private void moveSquare(int x, int y) {
	int OFFSET = 1;
	if ((squareX!=x) || (squareY!=y)) {
	    repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
	    squareX=x;
	    squareY=y;
	    repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
	}
    }


    @Override
    public Dimension getPreferredSize() {
	return new Dimension(250,200);
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawString("This is my custom Panel!",10,20);
	g.setColor(Color.RED);
	g.fillRect(squareX,squareY,squareW,squareH);
	g.setColor(Color.BLACK);
	g.drawRect(squareX,squareY,squareW,squareH);
    }
}