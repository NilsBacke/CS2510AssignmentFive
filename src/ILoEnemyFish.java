import javalib.funworld.WorldScene;

/* template for ILoEnemyFish 
 * Fields:
 * Methods:
 * add ... ILoEnemyFish
 * update ... ILoEnemyFish
 * addToScene ... WorldScene
 * length ... int
 * didCollide ... int
 * didCollideHelp ... int
 * removeAt ... ILoEnemyFish
 * removeAtHelp ... ILoEnemyFish
 * at ... EnemyFish
 * atHelp ... EnemyFish
 * isBiggerFish ... boolean
 * Methods for fields:
 */

// represents all the enemy fish in the game
interface ILoEnemyFish {
  
  // adds a new EnemyFish to the list
  ILoEnemyFish add(EnemyFish enemyFish);

  // updates the list of fish
  ILoEnemyFish update();

  // adds a new fish to the image
  WorldScene addToScene(WorldScene scene);

  // returns the length of the list of fish
  int length();

  //returns -2 if no collision, -1 if the fish should die, else the index of the fish it eats
  int didCollide(UserFish user);
  
  // determines whether the fish is dead or alive 
  int didCollideHelp(UserFish user, int index);
  
  // removes the EnemyFish
  ILoEnemyFish removeAt(int index);
  
  // removes the EnemyFish
  ILoEnemyFish removeAtHelp(int index, int acc);
  
  // returns the EnemyFish at the given index in the list
  EnemyFish at(int index);
  
  // returns the EnemyFish at the given index in the list
  EnemyFish atHelp(int index, int acc);
  
  // determines if the UserFish is bigger than all the EnemyFish
  boolean isBiggestFish(UserFish user);
}

// represents an empty list of enemyFish
class MtLoEnemyFish implements ILoEnemyFish {
  MtLoEnemyFish() {
  }
  
  /* template for MtLoEnemyFish 
   * Fields:
   * Methods:
   * add ... ILoEnemyFish
   * update ... ILoEnemyFish
   * addToScene ... WorldScene
   * length ... int
   * didCollide ... int
   * didCollideHelp ... int
   * removeAt ... ILoEnemyFish
   * removeAtHelp ... ILoEnemyFish
   * at ... EnemyFish
   * atHelp ... EnemyFish
   * isBiggerFish ... boolean
   * Methods for fields:
   */

  @Override
  public ILoEnemyFish add(EnemyFish enemyFish) {
    // adds a new EnemyFish to the list
    return new ConsLoEnemyFish(enemyFish, new MtLoEnemyFish());
  }

  @Override
  public ILoEnemyFish update() {
    // updates the list of fish
    return this;
  }

  @Override
  public WorldScene addToScene(WorldScene scene) {
    // adds a new fish to the image
    return scene;
  }

  @Override
  public int length() {
    // returns the length of the list of fish
    return 0;
  }

  @Override
  public int didCollide(UserFish user) {
    //returns -2 if no collision, -1 if the fish should die, else the index of the fish it eats
    return -2;
  }

  @Override
  public int didCollideHelp(UserFish user, int index) {
    // determines whether the fish is dead or alive
    return -2;
  }

  @Override
  public ILoEnemyFish removeAt(int index) {
    // removes the EnemyFish
    return this;
  }

  @Override
  public ILoEnemyFish removeAtHelp(int index, int acc) {
    // removes the EnemyFish
    return this;
  }

  @Override
  public EnemyFish at(int index) {
    // returns the EnemyFish at the given index in the list
    return null;
  }

  @Override
  public EnemyFish atHelp(int index, int acc) {
    // returns the EnemyFish at the given index in the list
    return null;
  }

  @Override
  public boolean isBiggestFish(UserFish user) {
    // determines if the UserFish is bigger than all the EnemyFish
    return true;
  }
}

// represents a list of fish
class ConsLoEnemyFish implements ILoEnemyFish {
  EnemyFish first;
  ILoEnemyFish rest;

  ConsLoEnemyFish(EnemyFish first, ILoEnemyFish rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* template for ConsLoEnemyFish 
   * Fields:
   * this.first ... EnemyFish
   * this.rest ... ILoEnemyFish
   * Methods:
   * add ... ILoEnemyFish
   * update ... ILoEnemyFish
   * addToScene ... WorldScene
   * length ... int
   * didCollide ... int
   * didCollideHelp ... int
   * removeAt ... ILoEnemyFish
   * removeAtHelp ... ILoEnemyFish
   * at ... EnemyFish
   * atHelp ... EnemyFish
   * isBiggerFish ... boolean
   * Methods for fields:
   */

  @Override
  public ILoEnemyFish add(EnemyFish enemyFish) {
    // adds a new EnemyFish to the list
    return new ConsLoEnemyFish(enemyFish, this);
  }

  @Override
  public ILoEnemyFish update() {
    // updates the list of fish
    return new ConsLoEnemyFish(this.first.update(), this.rest.update());
  }

  @Override
  public WorldScene addToScene(WorldScene scene) {
    // adds a new fish to the image 
    return this.rest.addToScene(
        scene.placeImageXY(this.first.enemyFishImage(), this.first.getX(), this.first.getY()));
  }

  @Override
  public int length() {
    // returns the length of the list of fish
    return 1 + this.rest.length();
  }

  @Override
  public int didCollide(UserFish user) {
    // returns 0 if no collision, -1 if the fish should die, else the index of the fish it eats
    return this.didCollideHelp(user, 0);
  }

  @Override
  public int didCollideHelp(UserFish user, int index) {
    // determines whether the is dead or alive 
    if (user.collides(this.first)) {
      // determine if userfish dies or grows
      // remove enemy fish from the list
      if (this.first.getSize() <= user.getSize()) {
        // eat
        return index;
      }
      else {
        // die
        System.out.println("DIE");
        return -1;
      }
    }
    else {
      return this.rest.didCollideHelp(user, index + 1);
    }
  }

  @Override
  public ILoEnemyFish removeAt(int index) {
    // removes the EnemyFish
    return this.removeAtHelp(index, 0);
  }

  @Override
  public ILoEnemyFish removeAtHelp(int index, int acc) {
    // removes the ILoEnemyFish
    if (index == acc) {
      return this.rest;
    } else {
      return new ConsLoEnemyFish(this.first, this.rest.removeAtHelp(index, acc + 1));
    }
  }

  @Override
  public EnemyFish at(int index) {
    // returns the EnemyFish at the given index in the list
    return atHelp(index, 0);
  }

  @Override
  public EnemyFish atHelp(int index, int acc) {
    // returns the EnemyFish at the given index in the list
    if (index == acc) {
      return this.first;
    } else {
      return this.rest.atHelp(index, acc + 1);
    }
  }

  @Override
  public boolean isBiggestFish(UserFish user) {
    // determines if the UserFish is bigger than all the EnemyFish
    if (this.first.getSize() > user.getSize()) {
      return false;
    } else {
      return this.rest.isBiggestFish(user);
    }
  }
}