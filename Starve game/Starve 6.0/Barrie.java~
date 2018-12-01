// resource class
// tree 
import java.util.Random;

class Barrie extends World{
  Random myRandom = new Random();
  
  private Boolean block = true;
  private int health = 5;
  
  public Boolean checkBlock (){
    return this.block;
  }
  
  public void loseHealth(){
    super.loseHealth();
    if (health>0){
    health--;
    System.out.println("health: "+ health);
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
    super.restoreHealth();
    if (health < 50){
      health+=1;
    }
  }
}
