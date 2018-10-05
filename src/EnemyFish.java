import java.awt.Color;

import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;

public class EnemyFish extends AFish {

  // -1, 1
  // -1 = right to left
  // 1 = left to right
  int direction;
  
  EnemyFish(int x, int y, int size) {
    super(x, y, size);
    if (Math.random() < .5) {
      this.direction = -1; // right to left
    } else {
      this.direction = 1; // left to right
    }
  }
  
  EnemyFish() {
    super(0, 0, 0);
    this.y = (int) Math.floor(Math.random() * 400);
    this.size = (int) Math.floor(Math.random() * 15) + 5;
    
    if (Math.random() < .5) {
      this.x = 0;
      this.direction = -1; // right to left
    } else {
      this.x = 400;
      this.direction = 1; // left to right
    }
  }
  
  EnemyFish update() {
    return new EnemyFish(this.x + (this.direction * 5), y, size);
  }
  
  /** produce the image of this blob */
  WorldImage userFishImage() {
    return new CircleImage(this.size, "solid", new Color(0, 255, 0));
  }

}
