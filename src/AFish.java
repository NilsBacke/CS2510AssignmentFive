// represents all the AFish
public class AFish {
  int x;
  int y;
  int size;
  
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
  
  int getX() {
    // returns the x
    return this.x;
  }
  
  int getY() {
    // returns the y
    return this.y;
  }
  
  int getSize() {
    // returns the size
    return this.size;
  }
}