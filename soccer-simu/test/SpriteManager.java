import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;


class SpriteManager extends Vector {
  private BackgroundImage 
                       backgroundImage;

  public SpriteManager(
     BackgroundImage backgroundImage) {
    this.backgroundImage = 
                       backgroundImage;
  }//end constructor
  //---------------------------------//
  
  public Point getEmptyPosition(
                 Dimension spriteSize){
    Rectangle trialSpaceOccupied = 
      new Rectangle(0, 0, 
                    spriteSize.width, 
                    spriteSize.height);
    Random rand = 
         new Random(
           System.currentTimeMillis());
    boolean empty = false;
    int numTries = 0;

    // Search for an empty position
    while (!empty && numTries++ < 100){
      // Get a trial position
      trialSpaceOccupied.x = 
        Math.abs(rand.nextInt() %
                      backgroundImage.
                      getSize().width);
      trialSpaceOccupied.y = 
        Math.abs(rand.nextInt() %
                     backgroundImage.
                     getSize().height);

      // Iterate through existing 
      // sprites, checking if position 
      // is empty
      boolean collision = false;
      for(int cnt = 0;cnt < size();
                                cnt++){
        Rectangle testSpaceOccupied = 
              ((Sprite)elementAt(cnt)).
                    getSpaceOccupied();
        if (trialSpaceOccupied.
                 intersects(
                   testSpaceOccupied)){
          collision = true;
        }//end if
      }//end for loop
      empty = !collision;
    }//end while loop
    return new Point(
                 trialSpaceOccupied.x, 
                 trialSpaceOccupied.y);
  }//end getEmptyPosition()
  //---------------------------------//
  
  public void update() {
    Sprite sprite;
    
    //Iterate through sprite list
    for (int cnt = 0;cnt < size();
                                cnt++){
      sprite = (Sprite)elementAt(cnt);
      //Update a sprite's position
      sprite.updatePosition();

      //Test for collision. Positive 
      // result indicates a collision
      int hitIndex = 
              testForCollision(sprite);
      if (hitIndex >= 0){
        //a collision has occurred
        bounceOffSprite(cnt,hitIndex);
      }//end if
    }//end for loop
  }//end update
  //---------------------------------//
  
  private int testForCollision(
                   Sprite testSprite) {
    //Check for collision with other 
    // sprites
    Sprite  sprite;
    for (int cnt = 0;cnt < size();
                                cnt++){
      sprite = (Sprite)elementAt(cnt);
      if (sprite == testSprite)
        //don't check self
        continue;
      //Invoke testCollision method 
      // of Sprite class to perform
      // the actual test.
      if (testSprite.testCollision(
                               sprite))
        //Return index of colliding 
        // sprite
        return cnt;
    }//end for loop
    return -1;//No collision detected
  }//end testForCollision()
  //---------------------------------//
  
  private void bounceOffSprite(
                    int oneHitIndex,
                    int otherHitIndex){
    //Swap motion vectors for 
    // bounce algorithm
    Sprite oneSprite = 
        (Sprite)elementAt(oneHitIndex);
    Sprite otherSprite = 
      (Sprite)elementAt(otherHitIndex);
    Point swap = 
           oneSprite.getMotionVector();
    oneSprite.setMotionVector(
        otherSprite.getMotionVector());
    otherSprite.setMotionVector(swap);
  }//end bounceOffSprite()
  //---------------------------------//
  
  public void drawScene(Graphics g){
    //Draw the background and erase 
    // sprites from graphics area
    //Disable the following statement 
    // for an interesting effect.
    backgroundImage.
                drawBackgroundImage(g);

    //Iterate through sprites, drawing
    // each sprite
    for (int cnt = 0;cnt < size();
                                 cnt++)
      ((Sprite)elementAt(cnt)).
                    drawSpriteImage(g);
  }//end drawScene()
  //---------------------------------//
  
  public void addSprite(Sprite sprite){
    add(sprite);
  }//end addSprite()
  
}//end class SpriteManager
//===================================//