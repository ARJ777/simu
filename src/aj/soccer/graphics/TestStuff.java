package aj.soccer.graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TestStuff {

	private JPanel picture = new JPanel();
	private JButton play = new JButton("Play");
	private JButton highScores = new JButton("High Score and \nStatistics");
	private JButton changeMap = new JButton("Create Custom \nor Change Map");
	private JButton difficulty = new JButton("Custom or\nChange Difficulty");
	private JButton user = new JButton("Change User");
	Image img;

	public MinesweeperMenu()
	{
	    // Set Layout for the menu
	    LayoutManager menuLayout = new BoxLayout(menu, BoxLayout.Y_AXIS);
	    menu.setLayout(menuLayout);

	    // Set Layout for the window
	    LayoutManager windowLayout = new BorderLayout();
	    window.setLayout(windowLayout);

	    // Add buttons to the panels
	    menu.add(play);
	    menu.add(highScores);
	    menu.add(changeMap);
	    menu.add(difficulty);
	    menu.add(user);

	    // Add picture to the frame
	    try{
	    File input = new File("./setup/images/MineMenuPicture.jpg");
	    img = ImageIO.read(input);
	    }
	    catch(IOException ie)
	    {
	        System.out.println(ie.getMessage());
	    }

	    // Add action listeners
	    changeMap.addActionListener(new ChangeMapListener());   

	}


	public void paintComponent(Graphics g)
	{
	    // POSITION OF THE PICTURE
	    int x = 650;
	    int y = 585;
	    g.drawImage(img, x, y, null);
	}

	public void displayFrame()
	{
	    // Display Frame
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setVisible(true);
	}

	public static void main(String[] args)
	{
	    MinesweeperMenu menu = new MinesweeperMenu();
	    window.pack();
	    menu.displayFrame();
	    window.repaint();
	}
	}


	public class MinesweeperPanel extends JFrame{

	public static final Color COLOR_KEY = new Color(220, 110, 0);

	// Initialize all the objects
	public static JFrame window = new JFrame("Minesweeper++");
	public static JPanel menu = new JPanel();

	// Close the current window
	public static void close()
	{
	    window.setVisible(false);
	    window.dispose();
	}



	}