import tester.*;
import java.util.Random;

import java.awt.Color;

import javalib.funworld.*;
import javalib.worldimages.*;

// represents all fish in the game
public class Fishy extends World {

  int width = 400; // of the scene
  int height = 400; // of the scene
  UserFish userFish;
  ILoEnemyFish enemyFish;
  final int NUM_ENEMY_FISH = 10;

  // creates a new Fishy game
  Fishy() {
    super();
    this.userFish = new UserFish(this.width / 2, this.height / 2, 10);
    this.enemyFish = new MtLoEnemyFish();

    for (int i = 0; i < NUM_ENEMY_FISH; i++) {
      this.enemyFish = this.enemyFish.add(new EnemyFish());
    }
  }

  // creates a new Fishy game with a given UserFish and list of EnemyFish
  // used in the onTick method to update the UserFish and the list of EnemyFish
  Fishy(UserFish userFish, ILoEnemyFish enemyFish) {
    super();
    this.userFish = userFish;
    this.enemyFish = enemyFish;

    int result = this.enemyFish.didCollide(this.userFish);
    if (result == -2 || result == -1) {
      return;
    }
    else {
      this.userFish = this.userFish.grow(this.enemyFish.at(result));
      this.enemyFish = this.enemyFish.removeAt(result);
    }
  }

  /*
   * template for Fishy Fields: this.width ... int this.height ... int
   * this.userFish ... UserFish this.enemyFish ... ILoEnemyFish
   * this.NUM_ENEMY_FISH ... final int Methods: makeScene ... WorldScene lastScene
   * ... WorldScene worldEnds ... WorldEnd onKeyEvent ... World onTick ... World
   * Methods for fields: this.userFish.moveUser ... UserFish this.userFish.update
   * ... UserFish this.userFish.collide .. boolean this.userFish.grow ... UserFish
   * this.userFish.userFishImage ... WorldImage this.enemyFish.add ...
   * ILoEnemyFish this.enemyFish.update ... ILoEnemyFish this.enemyFish.addToScene
   * ... WorldScene this.enemyFish.length ... int this.enemyFish.didCollide ...
   * int this.enemyFish.didCollideHelp ... int this.enemyFish.removeAt ...
   * ILoEnemyFish this.enemyFish.removeAtHelp ... ILoEnemyFish this.enemyFish.at
   * ... EnemyFish this.enemyFish.atHelp ... EnemyFish this.enemyFish.isBiggerFish
   * ... boolean
   */

  // represents the background of the game
  public WorldImage background = new RectangleImage(this.width, this.height, OutlineMode.SOLID,
      Color.BLUE);

  // produces the image of this world
  @Override
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene()
        .placeImageXY(this.background, this.width / 2, this.height / 2)
        .placeImageXY(this.userFish.userFishImage(), this.userFish.x, this.userFish.y);
    scene = this.enemyFish.addToScene(scene);
    return scene;
  }

  // produces the last image of this world by adding text to the image
  public WorldScene lastScene(String s) {
    return this.makeScene().placeImageXY(new TextImage(s, Color.red), 200, 200);
  }

  // checks whether the fish was eaten
  public WorldEnd worldEnds() {

    int result = this.enemyFish.didCollide(this.userFish);
    // fish got eaten
    if (result == -1) {
      return new WorldEnd(true, this.lastScene("YOU LOSE"));
    }
    // winner
    else if (this.enemyFish.isBiggestFish(this.userFish)) {
      return new WorldEnd(true, this.lastScene("YOU WIN"));
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // moves the UserFish when the player presses a key
  public World onKeyEvent(String ke) {
    if (ke.equals("x"))
      return this.endOfWorld("Goodbye");
    else
      return new Fishy(this.userFish.moveUser(ke), this.enemyFish);
  }

  // On tick move all of the enemy fish in their appropriate direction
  public World onTick() {
    return new Fishy(this.userFish.update(), this.enemyFish.update());
  }

}

// examples for all types of fish
class FishyExamples {
  UserFish userFish1 = new UserFish(12, 55, 6);
  UserFish userFish1Left = new UserFish(7, 55, 6);
  UserFish userFish1Right = new UserFish(17, 55, 6);
  UserFish userFish1Top = new UserFish(12, 50, 6);
  UserFish userFish1Bot = new UserFish(12, 60, 6);
  UserFish userFish2 = new UserFish(88, 70, 12);
  UserFish userFish3 = new UserFish(76, 70, 12);
  UserFish userFish4 = new UserFish(0, 0, 0);
  UserFish userFish5 = new UserFish(10, 9, 4);
  UserFish userFish6 = new UserFish(100, 93, 21);
  UserFish userFish7 = new UserFish(-8, 93, 21);
  UserFish userFish8 = new UserFish(987, 93, 21);
  UserFish userFish9 = new UserFish(59, 865, 21);
  UserFish userFish10 = new UserFish(232, -90, 21);
  EnemyFish enemyFish1 = new EnemyFish(new Random(7));
  EnemyFish enemyFish2 = new EnemyFish(new Random(82));
  EnemyFish enemyFish3 = new EnemyFish(new Random(4));
  EnemyFish enemyFish4 = new EnemyFish(new Random(142));
  EnemyFish enemyFish5 = new EnemyFish(new Random(21));
  EnemyFish enemyFish6 = new EnemyFish(1, 76, 70, 20, new Random(7)); // collides with userFish3
  ILoEnemyFish iLoEnemyFish1 = new MtLoEnemyFish();
  ILoEnemyFish iLoEnemyFish2 = new ConsLoEnemyFish(this.enemyFish1, this.iLoEnemyFish1);
  ILoEnemyFish iLoEnemyFish3 = new ConsLoEnemyFish(this.enemyFish3, this.iLoEnemyFish2);
  ILoEnemyFish iLoEnemyFish4 = new ConsLoEnemyFish(this.enemyFish6, this.iLoEnemyFish3);
  
  int width = 400;
  int height = 400;
  public WorldImage background = new RectangleImage(this.width, this.height, OutlineMode.SOLID,
      Color.BLUE);
  WorldScene scene1 = new WorldScene(width, height).placeImageXY(this.background, this.width / 2,
      this.height / 2);

  Fishy fishyWinner = new Fishy(this.userFish6, this.iLoEnemyFish3);
  Fishy fishyLoser = new Fishy(this.userFish3, this.iLoEnemyFish4);
  Fishy fishyStable = new Fishy(this.userFish1, this.iLoEnemyFish1);
  Fishy fishy1 = new Fishy(this.userFish1, this.iLoEnemyFish1);
  Fishy fishyL = new Fishy(this.userFish1Left, this.iLoEnemyFish1);
  Fishy fishyR = new Fishy(this.userFish1Right, this.iLoEnemyFish1);
  Fishy fishyT = new Fishy(this.userFish1Top, this.iLoEnemyFish1);
  Fishy fishyB = new Fishy(this.userFish1Bot, this.iLoEnemyFish1);

  boolean testGetY(Tester t) {
    return t.checkExpect(this.userFish1.getY(), 55) && t.checkExpect(this.userFish2.getY(), 70)
        && t.checkExpect(this.enemyFish2.getY(), 135) && t.checkExpect(this.enemyFish1.getY(), 236);
  }

  boolean testGetX(Tester t) {
    return t.checkExpect(this.userFish1.getX(), 12) && t.checkExpect(this.userFish2.getX(), 88)
        && t.checkExpect(this.enemyFish4.getX(), 400) && t.checkExpect(this.enemyFish1.getX(), 0);
  }

  boolean testGetSize(Tester t) {
    return t.checkExpect(this.userFish1.getSize(), 6) && t.checkExpect(this.userFish2.getSize(), 12)
        && t.checkExpect(this.enemyFish2.getSize(), 11)
        && t.checkExpect(this.enemyFish1.getSize(), 19);
  }

  boolean testMoveUser(Tester t) {
    return t.checkExpect(this.userFish3.moveUser("right"), new UserFish(81, 70, 12))
        && t.checkExpect(this.userFish3.moveUser("left"), new UserFish(71, 70, 12))
        && t.checkExpect(this.userFish5.moveUser("up"), new UserFish(10, 4, 4))
        && t.checkExpect(this.userFish3.moveUser("down"), new UserFish(76, 75, 12))
        && t.checkExpect(this.userFish6.moveUser("l"), new UserFish(100, 93, 21));
  }

  boolean testUpdate(Tester t) {
    return t.checkExpect(this.userFish7.update(), new UserFish(400, 93, 21))
        && t.checkExpect(this.userFish8.update(), new UserFish(0, 93, 21))
        && t.checkExpect(this.userFish9.update(), new UserFish(59, 0, 21))
        && t.checkExpect(this.userFish10.update(), new UserFish(232, 400, 21))
        && t.checkExpect(this.userFish3.update(), new UserFish(76, 70, 12))
        && t.checkExpect(this.enemyFish2.update(), new EnemyFish(-1, 394, 135, 11, new Random(82)))
            && t.checkExpect(this.enemyFish5.update(), new EnemyFish(-1, 394, 278, 14, new Random(21)));
  }

  boolean testCollides(Tester t) {
    return t.checkExpect(this.userFish3.collides(enemyFish1), false)
        && t.checkExpect(this.userFish4.collides(enemyFish3), false);
  }

  boolean testGrow(Tester t) {
    return t.checkExpect(this.userFish3.grow(this.enemyFish1), new UserFish(76, 70, 15))
        && t.checkExpect(this.userFish4.grow(this.enemyFish3), new UserFish(0, 0, 2))
        && t.checkExpect(this.userFish5.grow(this.enemyFish1), new UserFish(10, 9, 7));
  }

  boolean testUserFishImage(Tester t) {
    return t.checkExpect(this.userFish3.userFishImage(),
        new CircleImage(12, "solid", new Color(255, 0, 0)));
  }

  boolean testEnemyFishImage(Tester t) {
    return t.checkExpect(this.enemyFish1.enemyFishImage(),
        new CircleImage(19, "solid", new Color(0, 255, 0)))
        && t.checkExpect(this.enemyFish3.enemyFishImage(),
            new CircleImage(12, "solid", new Color(0, 255, 0)));
  }

  boolean testAdd(Tester t) {
    return t.checkExpect(this.iLoEnemyFish3.add(this.enemyFish4),
        new ConsLoEnemyFish(this.enemyFish4, this.iLoEnemyFish3))
        && t.checkExpect(this.iLoEnemyFish2.add(this.enemyFish3),
            new ConsLoEnemyFish(this.enemyFish3, this.iLoEnemyFish2))
        && t.checkExpect(this.iLoEnemyFish1.add(this.enemyFish5),
            new ConsLoEnemyFish(this.enemyFish5, this.iLoEnemyFish1));
  }

  boolean testAddToScene(Tester t) {
    return t.checkExpect(this.iLoEnemyFish2.addToScene(this.scene1),
        scene1.placeImageXY(this.enemyFish1.enemyFishImage(), this.enemyFish1.getX(),
            this.enemyFish1.getY()))
        && t.checkExpect(this.iLoEnemyFish1.addToScene(this.scene1), scene1);
  }

  boolean testWorldEnds(Tester t) {
    return t.checkExpect(this.fishyWinner.worldEnds(),
        new WorldEnd(true,
            this.fishyWinner.makeScene().placeImageXY(new TextImage("YOU WIN", Color.red), 200,
                200)))
        && t.checkExpect(this.fishyLoser.worldEnds(), new WorldEnd(true, this.fishyWinner
            .makeScene().placeImageXY(new TextImage("GAME OVER", Color.red), 200, 200)));
  }

  boolean testOnKeyEvent(Tester t) {
    return t.checkExpect(this.fishy1.onKeyEvent("up"), this.fishyT)
        && t.checkExpect(this.fishy1.onKeyEvent("down"), this.fishyB)
        && t.checkExpect(this.fishy1.onKeyEvent("left"), this.fishyL)
        && t.checkExpect(this.fishy1.onKeyEvent("right"), this.fishyR)
        && t.checkExpect(this.fishy1.onKeyEvent("x"), this.fishy1);
  }

  boolean testOnTick(Tester t) {
    return t.checkExpect(this.fishyLoser.testOnTick().lastWorld,
        new WorldEnd(true,
            this.fishyLoser.makeScene().placeImageXY(new TextImage("GAME OVER", Color.red), 200,
                200)))
        && t.checkExpect(this.fishyStable.onTick(), this.fishyStable)
        && t.checkExpect(this.fishy1.testOnTick().lastWorld, new WorldEnd(true, this.fishy1.makeScene()
            .placeImageXY(new TextImage("YOU WIN", Color.red), 200, 200)));
  }

// TODO:

  // tests for didCollide, didCollideHelp and collides that actually show
  // collisions
  // tests for removeAt and removeAtHelp
  // tests for at and atHelp
  // tests for isBiggestFish

  boolean testLength(Tester t) {
    return t.checkExpect(this.iLoEnemyFish3.length(), 2)
        && t.checkExpect(this.iLoEnemyFish1.length(), 0)
        && t.checkExpect(this.iLoEnemyFish2.length(), 1);
  }

  boolean testDidCollide(Tester t) {
    return t.checkExpect(this.iLoEnemyFish3.didCollide(this.userFish10), -2)
        && t.checkExpect(this.iLoEnemyFish2.didCollide(this.userFish6), -2);
  }

  boolean testDidCollideHelp(Tester t) {
    return t.checkExpect(this.iLoEnemyFish3.didCollideHelp(this.userFish10, 2), -2)
        && t.checkExpect(this.iLoEnemyFish2.didCollideHelp(this.userFish6, 1), -2);
  }

  boolean testRemoveAt(Tester t) {
    return t.checkExpect(this.iLoEnemyFish3.removeAt(3), this.iLoEnemyFish3);
  }

  boolean testIsBiggestFish(Tester t) {
    return t.checkExpect(this.iLoEnemyFish3.isBiggestFish(this.userFish6), true)
        && t.checkExpect(this.iLoEnemyFish2.isBiggestFish(this.userFish3), false)
        && t.checkExpect(this.iLoEnemyFish3.isBiggestFish(this.userFish5), false);
  }

  public static void main(String[] argv) {

    // run the game
    Fishy w = new Fishy();
    w.bigBang(400, 400, 0.3);
  }
}
