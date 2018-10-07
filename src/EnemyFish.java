import java.awt.Color;
import java.util.Random;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

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
  
  EnemyFish update() {
    this.x += this.direction * 5;
    return this;
  }
  
  /** produce the image of this blob */
  WorldImage enemyFishImage() {
    return new CircleImage(this.size, "solid", new Color(0, 255, 0));
  }

}
