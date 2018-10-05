import tester.*;

import java.awt.Color;

import javalib.funworld.*;
import javalib.worldimages.*;

public class Fishy extends World {
  
  int width = 400;
  int height = 400;
  UserFish userFish;
  ILoEnemyFish enemyFish;
  
  Fishy() {
    super();
    this.userFish = new UserFish(this.width / 2, this.height / 2, 10);
    this.enemyFish = new MtLoEnemyFish();
    
    for (int i = 0; i < 5; i++) {
      this.enemyFish = this.enemyFish.add(new EnemyFish());
    }
  }
  
  Fishy(UserFish userFish, ILoEnemyFish enemyFish) {
    super();
    this.userFish = userFish;
    this.enemyFish = enemyFish;
  }
  
  public WorldImage background = 
      new RectangleImage(this.width, this.height, OutlineMode.SOLID, Color.BLUE);

  /**
   * produce the image of this world
   */
  @Override
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene().placeImageXY(this.background, this.width / 2, this.height / 2)
        .placeImageXY(this.userFish.userFishImage(), this.userFish.x, this.userFish.y);
    scene = this.enemyFish.addToScene(scene);
    return scene;
  }
  
  /** Move the UserFish when the player presses a key */
  public World onKeyEvent(String ke) {
    if (ke.equals("x"))
      return this.endOfWorld("Goodbye");
    else
      return new Fishy(this.userFish.moveUser(ke), this.enemyFish);
  }
  
  /**
   * On tick move all of the enemy fish in their appropriate direction.
   */
  public World onTick() {
    return new Fishy(this.userFish.update(), this.enemyFish.update());
  }

}

class FishyExamples {
  public static void main(String[] argv) {

    // run the game
    Fishy w = new Fishy();
    w.bigBang(400, 400, 0.3);
  }
}
