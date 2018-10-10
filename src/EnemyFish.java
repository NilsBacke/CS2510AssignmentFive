import java.awt.Color;
import java.util.Random;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

// represents all the EnemyFish
public class EnemyFish extends AFish {

  // -1, 1
  // -1 = right to left
  // 1 = left to right
  int direction;
  Random rand;
  
  EnemyFish() {
    this(new Random());
  }
  
  EnemyFish(Random rand) {
    super(0, 0, 0);
    this.rand = rand;
    this.y = rand.nextInt(400); // 0 to 400
    this.size = rand.nextInt(15) + 5; // 5 to 20
    
    if (Math.random() < .5) {
      this.x = 400;
      this.direction = -1; // right to left
    } else {
      this.x = 0;
      this.direction = 1; // left to right
    }
  }
  
  /* template for EnemyFish
   * Fields:
   * this.direction ... int
   * this.rand ... Random
   * Methods:
   * update ... EnemyFish
   * enemyFishImage ... WorldImage
   * Fields for methods:
   * 
   */
  
  EnemyFish update() {
    // updates the location of a given EnemyFish
    this.x += this.direction * 5;
    if (this.x > 400) {
      this.x = 0;
    } else if (this.x < 0) {
      this.x = 400;
    }
    return this;
  }
  
  WorldImage enemyFishImage() {
    // produces the image of this EnemyFish 
    return new CircleImage(this.size, "solid", new Color(0, 255, 0));
  }

}