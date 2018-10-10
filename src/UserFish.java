import java.awt.Color;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

// represents the player
public class UserFish extends Fish {

  // creates a new UserFish given a x and y coordinate along with a size
  UserFish(int x, int y, int size) {
    super(x, y, size);
  }
  
  /* template for UserFish
   * Fields:
   * this.x ... int
   * this.y ... int
   * this.size ... int
   * Methods:
   * moveUser ... UserFish
   * update ... UserFish
   * collide .. boolean
   * grow ... UserFish
   * userFishImage ... WorldImage
   * Methods for fields:
   * 
   */

  UserFish moveUser(String ke) {
    // returns a new UserFish in an updated location
    if (ke.equals("right")) {
      return new UserFish(this.x + 5, this.y, this.size);
    }
    else if (ke.equals("left")) {
      return new UserFish(this.x - 5, this.y, this.size);
    }
    else if (ke.equals("up")) {
      return new UserFish(this.x, this.y - 5, this.size);
    }
    else if (ke.equals("down")) {
      return new UserFish(this.x, this.y + 5, this.size);
    }
    else {
      return this;
    }
  }

  UserFish update() {
    // updates the location of the UserFish
    // checks if the player is out of bounds, and loops to the other side of the scene
    if (this.x < 0) {
      return new UserFish(400, this.y, this.size);
    }
    else if (this.x > 400) {
      return new UserFish(0, this.y, this.size);
    }
    else if (this.y < 0) {
      return new UserFish(this.x, 400, this.size);
    }
    else if (this.y > 400) {
      return new UserFish(this.x, 0, this.size);
    }
    else {
      return this;
    }
  }

  boolean collides(EnemyFish enemy) {
    // determines if the UserFish collided with the EnemyFish
    return Math.pow(enemy.getX() - this.x, 2) + Math.pow(enemy.getY() - this.y, 2) <= Math
        .pow(enemy.getSize() + this.size, 2);
  }
  
  UserFish grow(EnemyFish enemyEaten) {
    // returns a larger EnemyFish
    return new UserFish(this.x, this.y, this.size + enemyEaten.getSize() / 5);
  }

  WorldImage userFishImage() {
    // produces the image of the UserFish
    return new CircleImage(this.size, "solid", new Color(255, 0, 0));
  }

}