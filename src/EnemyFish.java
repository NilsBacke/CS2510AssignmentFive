import java.awt.Color;
import java.util.Random;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

// represents a background fish in the Fishy game
public class EnemyFish extends AFish {

  // -1, 1
  // -1 = right to left
  // 1 = left to right
  int direction;
  Random rand;
  
  // creates an EnemyFish with a new seed value
  EnemyFish() {
    this(new Random());
  }
  
  // creates an EnemyFish with the given seed value
  EnemyFish(Random rand) {
    super(0, 0, 0);
    this.rand = rand;
    
    // random y value
    this.y = rand.nextInt(400); // 0 to 400
    
    // random size
    this.size = rand.nextInt(15) + 5; // 5 to 20
    
    // determine direction of travel
    if (rand.nextInt(2) == 0) { // either equal 0 or 1
      this.x = 400;
      this.direction = -1; // right to left
    } else {
      this.x = 0;
      this.direction = 1; // left to right
    }
  }
  
  // for testing purposes only
  EnemyFish(int direction, int x, int y, int size, Random rand) {
    super(x, y, size);
    this.rand = rand;
    this.direction = direction;
  }
  
  /* template for EnemyFish
   * Fields:
   * this.direction ... int
   * this.rand ... Random
   * this.x ... int
   * this.y ... int
   * this.size ... int
   * 
   * Methods:
   * update ... EnemyFish
   * enemyFishImage ... WorldImage
   * 
   * Fields for methods:
   * this.rand.nextInt() ... int
   */
  
  // moves the fish in its direction
  // generates a new EnemyFish if its off the screen
  EnemyFish update() {
    this.x += this.direction * 6;
    if (this.x > 400 || this.x < 0) {
      return new EnemyFish(this.rand);
    }
    return this;
  }
  
  // produces an image of the enemy fish
  WorldImage enemyFishImage() {
    // produces the image of this EnemyFish 
    return new CircleImage(this.size, "solid", new Color(0, 255, 0));
  }

}