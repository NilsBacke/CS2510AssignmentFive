interface ILoEnemyFish {
  ILoEnemyFish add(EnemyFish enemyFish);
  ILoEnemyFish update();
}

class MtLoEnemyFish implements ILoEnemyFish {
  MtLoEnemyFish(){}

  @Override
  public ILoEnemyFish add(EnemyFish enemyFish) {
    return new ConsLoEnemyFish(enemyFish, new MtLoEnemyFish());
  }

  @Override
  public ILoEnemyFish update() {
    return this;
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
}
