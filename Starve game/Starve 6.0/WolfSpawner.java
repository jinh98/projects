

class WolfSpawner extends World{
  private Boolean block = true;
  private int row, column;
  private Clock tClock ;
  
  public Boolean checkBlock (){
    return this.block;
  }
  
  public WolfSpawner(int row, int column){
    this.row = row;
    this.column = column;
    tClock = new Clock();
   // clock.update();
  //  System.out.println("tree @"+row+","+column);
  }
  
  public void doSpawn(){
    tClock.update();
    if (tClock.getElapsedTime() > 15.0){
      spawn();
      tClock.reset();
    }
  }
  public int spawn(){
    //return 1,2,3 or 4;
    
    return (int)Math.random()*(4)+1;
  }
  
}