
// resource class
// Barries
// The class of foood, very similar to tree and stone
import java.util.Random;

class Barries extends World{
  Random myRandom = new Random();
  
  private Boolean block = true;
  private int health = 5;
  private int row, column;
  private int originalHealth = health;
  private Clock tClock ;
  
  public Boolean checkBlock (){
    return this.block;
  }
  
  public Barries(int row, int column){
    this.row = row;
    this.column = column;
    tClock = new Clock();
   // clock.update();
  //  System.out.println("Barries @"+row+","+column);
  }
  public void loseHealth(){
    super.loseHealth();
    if (health>0){
    health--;
    //System.out.println("health: "+ health);
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
    if (health < 5 && tClock.getElapsedTime() > 20.0){
      health= health + 1;
     System.out.println("restored: " + row+ ", " + column);
     
      tClock.reset();
    }
  }
  
  
}
