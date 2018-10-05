import java.awt.Color;

import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

public class UserFish extends AFish {

  UserFish(int x, int y, int size) {
    super(x, y, size);
  }
  
  UserFish moveUser(String ke) {
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
    } else {
      return this;
    }
  }
  
  UserFish update() {
    if (this.x < 0) { 
      return new UserFish(400, this.y, this.size);
    } else if (this.x > 400) {
      return new UserFish(0, this.y, this.size);
    } else {
      return this;
    }
  }
  
  /** produce the image of this blob */
  WorldImage userFishImage() {
    return new CircleImage(this.size, "solid", new Color(255, 0, 0));
  }

}
