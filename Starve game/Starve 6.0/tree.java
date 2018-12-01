// resource class
// tree ; similar to barries and stone
import java.util.Random;

class Tree extends World{
  Random myRandom = new Random();
  
  private Boolean block = true;
  private int health = 10;
  private int row, column;
  private int originalHealth = health;
  private Clock tClock ;
  
  public Boolean checkBlock (){
    return this.block;
  }
  
  public Tree(int row, int column){
    this.row = row;
    this.column = column;
    tClock = new Clock();
   // clock.update();
  //  System.out.println("tree @"+row+","+column);
  }
  
  //checks for its health
  public void loseHealth(){
    super.loseHealth();
    if (health>0){
    health--;
   // System.out.println("health: "+ health);
    }else{
      System.out.println("Resourse is empty");
    }
  }
  
  public boolean noHealth(){
    if (health==0){
      System.out.println("Resourse is empty");
      return true;
    }else{
      
      return false;
    }
  }
  
  public void restoreHealth(){
   tClock.update();
   // Clock clock = new Clock();
   // super.restoreHealth();
   // clock.update();
 // System.out.println(tClock.getElapsedTime());
    if (health < 10 && tClock.getElapsedTime() > 20.0){
      health= health + 1;
     // System.out.println("restored: " + row+ ", " + column);
     
      tClock.reset();
    }
  }
}
