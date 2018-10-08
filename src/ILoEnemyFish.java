import javalib.funworld.WorldScene;

interface ILoEnemyFish {
  ILoEnemyFish add(EnemyFish enemyFish);

  ILoEnemyFish update();

  WorldScene addToScene(WorldScene scene);

  int length();

  //returns -2 if no collision, -1 if the fish should die, else the index of the fish it eats
  int didCollide(UserFish user);
  
  int didCollideHelp(UserFish user, int index);
  
  ILoEnemyFish removeAt(int index);
  ILoEnemyFish removeAtHelp(int index, int acc);
  
  // returns the EnemyFish at the given index in the list
  EnemyFish at(int index);
  EnemyFish atHelp(int index, int acc);
  
  // returns true if the given UserFish is bigger than all of the enemy fish
  boolean isBiggestFish(UserFish user);
}

class MtLoEnemyFish implements ILoEnemyFish {
  MtLoEnemyFish() {
  }

  @Override
  public ILoEnemyFish add(EnemyFish enemyFish) {
    return new ConsLoEnemyFish(enemyFish, new MtLoEnemyFish());
  }

  @Override
  public ILoEnemyFish update() {
    return this;
  }

  @Override
  public WorldScene addToScene(WorldScene scene) {
    return scene;
  }

  @Override
  public int length() {
    return 0;
  }

  //returns -2 if no collision, -1 if the fish should die, else the index of the fish it eats
  @Override
  public int didCollide(UserFish user) {
    return -2;
  }

  @Override
  public int didCollideHelp(UserFish user, int index) {
    return -2;
  }

  @Override
  public ILoEnemyFish removeAt(int index) {
    return this;
  }

  @Override
  public ILoEnemyFish removeAtHelp(int index, int acc) {
    return this;
  }

  @Override
  public EnemyFish at(int index) {
    return null;
  }

  @Override
  public EnemyFish atHelp(int index, int acc) {
    return null;
  }

  @Override
  public boolean isBiggestFish(UserFish user) {
    return true;
  }
}

class ConsLoEnemyFish implements ILoEnemyFish {
  EnemyFish first;
  ILoEnemyFish rest;

  ConsLoEnemyFish(EnemyFish first, ILoEnemyFish rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public ILoEnemyFish add(EnemyFish enemyFish) {
    return new ConsLoEnemyFish(enemyFish, this);
  }

  @Override
  public ILoEnemyFish update() {
    return new ConsLoEnemyFish(this.first.update(), this.rest.update());
  }

  @Override
  public WorldScene addToScene(WorldScene scene) {
    return this.rest.addToScene(
        scene.placeImageXY(this.first.enemyFishImage(), this.first.getX(), this.first.getY()));
  }

  @Override
  public int length() {
    return 1 + this.rest.length();
  }

  // returns 0 if no collision, -1 if the fish should die, else the index of the fish it eats
  @Override
  public int didCollide(UserFish user) {
    return this.didCollideHelp(user, 0);
  }

  @Override
  public int didCollideHelp(UserFish user, int index) {
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
    return this.removeAtHelp(index, 0);
  }

  @Override
  public ILoEnemyFish removeAtHelp(int index, int acc) {
    if (index == acc) {
      return this.rest;
    } else {
      return new ConsLoEnemyFish(this.first, this.rest.removeAtHelp(index, acc + 1));
    }
  }

  @Override
  public EnemyFish at(int index) {
    return atHelp(index, 0);
  }

  @Override
  public EnemyFish atHelp(int index, int acc) {
    if (index == acc) {
      return this.first;
    } else {
      return this.rest.atHelp(index, acc + 1);
    }
  }

  @Override
  public boolean isBiggestFish(UserFish user) {
    if (this.first.getSize() > user.getSize()) {
      return false;
    } else {
      return this.rest.isBiggestFish(user);
    }
  }
}
