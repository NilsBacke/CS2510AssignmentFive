import javalib.funworld.WorldScene;

interface ILoEnemyFish {
  ILoEnemyFish add(EnemyFish enemyFish);

  ILoEnemyFish update();

  WorldScene addToScene(WorldScene scene);

  int length();
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
}
