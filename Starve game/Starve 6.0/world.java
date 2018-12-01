

//the abstract super class
//contains some ideas
abstract class World{
   private int health;
 private int size;
 private Boolean block;
 private int recipes;
 
 public Boolean checkBlock (){
    return this.block;
  }
  
 public int checkHealth (){
   return this.health;
 }
 public int checkSize (){
   return this.size;
 }
 public int checkRecipes (){
   return this.recipes;
 }
 public void loseHealth(){
    
  }
 public void restoreHealth(){
    
    
  }
}