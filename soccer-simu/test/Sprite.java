import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;


class Sprite {
  private Component component;
  private Image image;
  private Rectangle spaceOccupied;
  private Point motionVector;
  private Rectangle bounds;
  private Random rand; 

  public Sprite(Component component,
                Image image,
                Point position,
                Point motionVector){
    //Seed a random number generator 
    // for this sprite with the sprite
    // position.
    rand = new Random(position.x);
    this.component = component;
    this.image = image;
    setSpaceOccupied(new Rectangle(
          position.x,
          position.y,
          image.getWidth(component),
          image.getHeight(component)));
    this.motionVector = motionVector;
    //Compute edges of usable graphics
    // area in the Frame.
    int topBanner = (
                 (Container)component).
                       getInsets().top;
    int bottomBorder = 
                ((Container)component).
                    getInsets().bottom;
    int leftBorder = (
                (Container)component).
                     getInsets().left;
    int rightBorder = (
                (Container)component).
                    getInsets().right;
    bounds = new Rectangle(
         0 + leftBorder,
         0 + topBanner,
         component.getSize().width - 
            (leftBorder + rightBorder),
         component.getSize().height -
           (topBanner + bottomBorder));
  }//end constructor
  //---------------------------------//

  public Rectangle getSpaceOccupied(){
    return spaceOccupied;
  }//end getSpaceOccupied()
  //---------------------------------//
  
  void setSpaceOccupied(
              Rectangle spaceOccupied){
    this.spaceOccupied = spaceOccupied;
  }//setSpaceOccupied()
  //---------------------------------//
  
  public void setSpaceOccupied(
                       Point position){
    spaceOccupied.setLocation(
               position.x, position.y);
  }//setSpaceOccupied()
  //---------------------------------//
  
  public Point getMotionVector(){
    return motionVector;
  }//end getMotionVector()
  //---------------------------------//
  
  public void setMotionVector(
                   Point motionVector){
    this.motionVector = motionVector;
  }//end setMotionVector()
  //---------------------------------//
  
  public void setBounds(
                     Rectangle bounds){
    this.bounds = bounds;
  }//end setBounds()
  //---------------------------------//
  
  public void updatePosition() {
    Point position = new Point(
     spaceOccupied.x, spaceOccupied.y);
    
    //Insert random behavior.  During 
    // each update, a sprite has about
    // one chance in 10 of making a 
    // random change to its 
    // motionVector.  When a change 
    // occurs, the motionVector
    // coordinate values are forced to
    // fall between -7 and 7.  This 
    // puts a cap on the maximum speed
    // for a sprite.
    if(rand.nextInt() % 10 == 0){
      Point randomOffset = 
         new Point(rand.nextInt() % 3,
                   rand.nextInt() % 3);
      motionVector.x += randomOffset.x;
      if(motionVector.x >= 7) 
                   motionVector.x -= 7;
      if(motionVector.x <= -7) 
                   motionVector.x += 7;
      motionVector.y += randomOffset.y;
      if(motionVector.y >= 7) 
                   motionVector.y -= 7;
      if(motionVector.y <= -7) 
                   motionVector.y += 7;
    }//end if
    
    //Move the sprite on the screen
    position.translate(
       motionVector.x, motionVector.y);

    //Bounce off the walls
    boolean bounceRequired = false;
    Point tempMotionVector = new Point(
                       motionVector.x,
                       motionVector.y);
    
    //Handle walls in x-dimension
    if (position.x < bounds.x) {
      bounceRequired = true;
      position.x = bounds.x;
      //reverse direction in x
      tempMotionVector.x = 
                   -tempMotionVector.x;
    }else if ((
      position.x + spaceOccupied.width)
          > (bounds.x + bounds.width)){
      bounceRequired = true;
      position.x = bounds.x + 
                  bounds.width - 
                   spaceOccupied.width;
      //reverse direction in x
      tempMotionVector.x = 
                   -tempMotionVector.x;
    }//end else if
    
    //Handle walls in y-dimension
    if (position.y < bounds.y){
      bounceRequired = true;
      position.y = bounds.y;
      tempMotionVector.y = 
                   -tempMotionVector.y;
    }else if ((position.y + 
                  spaceOccupied.height)
         > (bounds.y + bounds.height)){
      bounceRequired = true;
      position.y = bounds.y + 
                 bounds.height - 
                  spaceOccupied.height;
      tempMotionVector.y = 
                   -tempMotionVector.y;
    }//end else if
    
    if(bounceRequired)
      //save new motionVector
                   setMotionVector(
                     tempMotionVector);
    //update spaceOccupied
    setSpaceOccupied(position);
  }//end updatePosition()
  //---------------------------------//
  
  public void drawSpriteImage(
                           Graphics g){
    g.drawImage(image,
                spaceOccupied.x,
                spaceOccupied.y,
                component);
  }//end drawSpriteImage()
  //---------------------------------//
  
  public boolean testCollision(
                    Sprite testSprite){
    //Check for collision with 
    // another sprite
    if (testSprite != this){
      return spaceOccupied.intersects(
        testSprite.getSpaceOccupied());
    }//end if
    return false;
  }//end testCollision
}//end Sprite class
//===================================//