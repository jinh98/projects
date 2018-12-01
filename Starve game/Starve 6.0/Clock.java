//Clock class for all the uses in other classes

class Clock {
  long elapsedTime;
  long lastTimeCheck;

  public Clock() { 
    lastTimeCheck=System.nanoTime();
    elapsedTime=0;
  }
  
  public void update() {
  long currentTime = System.nanoTime();  //if the computer is fast you need more precision
  elapsedTime=currentTime - lastTimeCheck;
  //lastTimeCheck=currentTime;
  }
  
   public void reset() {
  long currentTime = System.nanoTime();  //if the computer is fast you need more precision
  elapsedTime=currentTime - lastTimeCheck;
  lastTimeCheck=currentTime;
   }
  
  //return elapsed time in milliseconds
  public double getElapsedTime() {
   // System.out.println("Elapsed: "+ (long)lastTimeCheck);
    return (elapsedTime/1.0E9);
  }
}