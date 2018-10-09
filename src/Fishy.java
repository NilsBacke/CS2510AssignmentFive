import tester.*;

import java.awt.Color;

import javalib.funworld.*;
import javalib.worldimages.*;

public class Fishy extends World {
  
  int width = 400;
  int height = 400;
  UserFish userFish;
  ILoEnemyFish enemyFish;
  final int NUM_ENEMY_FISH = 10;
  
  Fishy() {
    super();
    this.userFish = new UserFish(this.width / 2, this.height / 2, 10);
    this.enemyFish = new MtLoEnemyFish();
    
    this.enemyFish = this.enemyFish.add(NUM_ENEMY_FISH);
  }
  
  Fishy(UserFish userFish, ILoEnemyFish enemyFish) {
    super();
    this.userFish = userFish;
    this.enemyFish = enemyFish;
    
    int result = this.enemyFish.didCollide(this.userFish);
    if (result == -2 || result == -1) {
      return;
    } else {
      this.userFish = this.userFish.grow(this.enemyFish.at(result));
      this.enemyFish = this.enemyFish.removeAt(result);
    }
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
  
  /** produce the last image of this world by adding text to the image */
  public WorldScene lastScene(String s) {
    return this.makeScene().placeImageXY(new TextImage(s, Color.red), 200, 200);
  }
  
  /**
   * Check whether the Blob is out of bounds, or fell into the black hole in the
   * middle.
   */
  public WorldEnd worldEnds() {
    
    int result = this.enemyFish.didCollide(this.userFish);
    // fish got eaten
    if (result == -1) {
      return new WorldEnd(true, this.lastScene("YOU LOSE"));
    } else if (this.enemyFish.isBiggestFish(this.userFish)) {
      return new WorldEnd(true, this.lastScene("YOU WIN"));
    } else {
      return new WorldEnd(false, this.makeScene());
    }
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
