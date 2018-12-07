public class Data implements Comparable <Data>{
  private String str;
  private int frequency;
  
  public Data (String s, int f){
    str=s;
    frequency=f;
  }

  public int getFrequency(){
    return frequency;
  }
  public String getString (){
    return str;
  }
  public void setFrequency(int f){
    this.frequency=f;
  }
  
  
  public int compareTo (Data other){
    if (this.equals (other)){
      return 0;
    }else if (getFrequency() > other.getFrequency()){
      return 1;
    }else{
      return -1;
    }
  }
}