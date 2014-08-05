

/*File Animate01.java
Copyright 2001, R.G.Baldwin

This program displays several animated
colored spherical creatures swimming 
around in an aquarium.  Each creature 
maintains generally the same course
with until it collides with another 
creature or with a wall.  However, 
each creature has the ability to 
occasionally make random changes in 
its course.

**************************************/
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Animate01 extends Frame 
                  implements Runnable {
  private Image offScreenImage;
  private Image backGroundImage;
  private Image[] gifImages = 
                          new Image[6];
  //offscreen graphics context
  private Graphics 
                  offScreenGraphicsCtx;
  private Thread animationThread;
  private MediaTracker mediaTracker;
  private SpriteManager spriteManager;
  //Animation display rate, 12fps
  private int animationDelay = 83;
  private Random rand = 
                new Random(System.
                  currentTimeMillis());
  
  public static void main(
                        String[] args){
    new Animate01();
  }//end main
  //---------------------------------//

  Animate01() {//constructor
    // Load and track the images
    mediaTracker = 
                new MediaTracker(this);
    //Get and track the background 
    // image
    backGroundImage = 
        Toolkit.getDefaultToolkit().
          getImage("background02.gif");
    mediaTracker.addImage(
                   backGroundImage, 0);
    
    //Get and track 6 images to use 
    // for sprites
    gifImages[0] = 
           Toolkit.getDefaultToolkit().
               getImage("redball.gif");
    mediaTracker.addImage(
                      gifImages[0], 0);
    gifImages[1] = 
           Toolkit.getDefaultToolkit().
             getImage("greenball.gif");
    mediaTracker.addImage(
                      gifImages[1], 0);
    gifImages[2] = 
           Toolkit.getDefaultToolkit().
              getImage("blueball.gif");
    mediaTracker.addImage(
                      gifImages[2], 0);
    gifImages[3] = 
           Toolkit.getDefaultToolkit().
            getImage("yellowball.gif");
    mediaTracker.addImage(
                      gifImages[3], 0);
    gifImages[4] = 
           Toolkit.getDefaultToolkit().
            getImage("purpleball.gif");
    mediaTracker.addImage(
                      gifImages[4], 0);
    gifImages[5] = 
           Toolkit.getDefaultToolkit().
            getImage("orangeball.gif");
    mediaTracker.addImage(
                      gifImages[5], 0);
    
    //Block and wait for all images to 
    // be loaded
    try {
      mediaTracker.waitForID(0);
    }catch (InterruptedException e) {
      System.out.println(e);
    }//end catch
    
    //Base the Frame size on the size 
    // of the background image.
    //These getter methods return -1 if
    // the size is not yet known.
    //Insets will be used later to 
    // limit the graphics area to the 
    // client area of the Frame.
    int width = 
        backGroundImage.getWidth(this);
    int height = 
       backGroundImage.getHeight(this);

    //While not likely, it may be 
    // possible that the size isn't
    // known yet.  Do the following 
    // just in case.
    //Wait until size is known
    while(width == -1 || height == -1){
      System.out.println(
                  "Waiting for image");
      width = backGroundImage.
                        getWidth(this);
      height = backGroundImage.
                       getHeight(this);
    }//end while loop
    
    //Display the frame
    setSize(width,height);
    setVisible(true);
    setTitle(
        "Copyright 2001, R.G.Baldwin");

    //Create and start animation thread
    animationThread = new Thread(this);
    animationThread.start();
  
    //Anonymous inner class window 
    // listener to terminate the 
    // program.
    this.addWindowListener(
                   new WindowAdapter(){
      public void windowClosing(
                        WindowEvent e){
        System.exit(0);}});
    
  }//end constructor
  //---------------------------------//

  public void run() {
    //Create and add sprites to the 
    // sprite manager
    spriteManager = new SpriteManager(
             new BackgroundImage(
               this, backGroundImage));
    //Create 15 sprites from 6 gif 
    // files.
    for (int cnt = 0; cnt < 15; cnt++){
      Point position = spriteManager.
        getEmptyPosition(new Dimension(
           gifImages[0].getWidth(this),
           gifImages[0].
                     getHeight(this)));
      spriteManager.addSprite(
        makeSprite(position, cnt % 6));
    }//end for loop

    //Loop, sleep, and update sprite 
    // positions once each 83 
    // milliseconds
    long time = 
            System.currentTimeMillis();
    while (true) {//infinite loop
      spriteManager.update();
      repaint();
      try {
        time += animationDelay;
        Thread.sleep(Math.max(0,time - 
          System.currentTimeMillis()));
      }catch (InterruptedException e) {
        System.out.println(e);
      }//end catch
    }//end while loop
  }//end run method
  //---------------------------------//
  
  private Sprite makeSprite(
      Point position, int imageIndex) {
    return new Sprite(
          this, 
          gifImages[imageIndex], 
          position, 
          new Point(rand.nextInt() % 5,
                  rand.nextInt() % 5));
  }//end makeSprite()
  //---------------------------------//

  //Overridden graphics update method 
  // on the Frame
  public void update(Graphics g) {
    //Create the offscreen graphics 
    // context
    if (offScreenGraphicsCtx == null) {
      offScreenImage = 
         createImage(getSize().width, 
                     getSize().height);
      offScreenGraphicsCtx = 
          offScreenImage.getGraphics();
    }//end if
    
    // Draw the sprites offscreen
    spriteManager.drawScene(
                 offScreenGraphicsCtx);

    // Draw the scene onto the screen
    if(offScreenImage != null){
         g.drawImage(
           offScreenImage, 0, 0, this);
    }//end if
  }//end overridden update method
  //---------------------------------//

  //Overridden paint method on the 
  // Frame
  public void paint(Graphics g) {
    //Nothing required here.  All 
    // drawing is done in the update 
    // method above.
  }//end overridden paint method
    
}//end class Animate01
//===================================//
