// a super class for EnemyFish and UserFish
public abstract class AFish {
  int x;
  int y;
  int size;
  
  // creates a new Fish given an x and y coordinate along with a size
  AFish(int x, int y, int size) {
    this.x = x;
    this.y = y;
    this.size = size;
  }
  
  /* template for AFish
   * Fields:
   * this.x ... int
   * this.y ... int
   * this.size ... int
   * Methods:
   * getX ... int
   * getY ... int
   * getSize ... int
   * Fields for methods:
   * 
   */
  
  // getter method
  int getX() {
    // returns the x
    return this.x;
  }
  
  // getter method
  int getY() {
    // returns the y
    return this.y;
  }
  
  // getter method
  int getSize() {
    // returns the size
    return this.size;
  }
}