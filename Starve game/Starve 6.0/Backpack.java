import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.SwingUtilities.*;



//************************ back pack class
//draws out the backpack itself and the stuff in it

class Backpack {
  static int xPos, yPos; 
  BufferedImage[] backpackItem;
  Image backpack = Toolkit.getDefaultToolkit().getImage("backpack.png");
  Image itemWood = Toolkit.getDefaultToolkit().getImage("wood.png");
  Image itemStone = Toolkit.getDefaultToolkit().getImage("stone.png");
  Image itemBarrie = Toolkit.getDefaultToolkit().getImage("barries.png");
  Image itemFire = Toolkit.getDefaultToolkit().getImage("fire.png");
  Image itemAxe = Toolkit.getDefaultToolkit().getImage("axe.png");
  Image itemSword = Toolkit.getDefaultToolkit().getImage("woodSword.png");
  Image itemPAxe = Toolkit.getDefaultToolkit().getImage("pickAxe.png");
  Inventory inventory;
    Boolean clickedWood = false;
  Boolean clickedStone = false;
  Boolean clickedBarrie = false;
  Boolean clickedFire = false;
   JPanel mainPanelReference;
 
  // BufferedImage[] backpack;
  public Backpack(JPanel main){
    this.xPos=158;
    this.yPos=665;
    inventory = new Inventory();
     this.mainPanelReference = main;
  }
  public void backPackItem(){
    //  loadBackPackItem();
    this.xPos = 200;
    this.yPos = 100;
  }
  /* public void loadBackPackItem(){
   try{
   BufferedImage sheet = ImageIO.read(new File("backpackitem.png"));
   backpackItem = new BufferedImage();
   }catch (Exception e) { System.out.println("error loading sheet");};
   }
   */    
public void draw(Graphics g) { 
     g.drawImage(backpack,xPos,yPos,null);
    
    if(inventory.getItem(0)>0){
    g.drawImage(itemWood,215,677,null);
    }
    if(inventory.getItem(1)>0){
      g.drawImage(itemStone,283,677,null);
    }
    if(inventory.getItem(2)>0){
      g.drawImage(itemBarrie,352,677,null);
      
    }
     if(inventory.getItem(3)>0){
      g.drawImage(itemFire,420,677,null);
      
    }
     if(inventory.getItem(4)>0){
      g.drawImage(itemPAxe,490,677,null);
      
    }
   if(inventory.getItem(5)>0){
      g.drawImage(itemSword,560,677,null);
   }
   
      Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
 SwingUtilities.convertPointFromScreen(mouseLocation,mainPanelReference);
 int mX = mouseLocation.x;
 int mY = mouseLocation.y;
        
  if( this.clickedWood == true){
      g.drawImage(itemWood,mX-40,mY-20,null);
  }else if(this.clickedStone == true){
    g.drawImage(itemStone,mX-40,mY-20,null);
  }else if (this.clickedBarrie == true){
    g.drawImage(itemBarrie,mX-40,mY-20,null);
  }else if (this.clickedFire == true){
    g.drawImage(itemFire,mX-40,mY-20,null);
  
  }
    }
public void clicked0(){
 
  if (clickedWood == false){
    clickedWood = true;
    //System.out.println("clickedWood" + clickedWood);
  }else{
    clickedWood = false;
  }
}


public void clicked1(){
  if (clickedStone == false){
    clickedStone = true;
  }else{
    clickedStone = false;
  }
}
 

public void clicked2(){
  if (clickedBarrie == false){
    clickedBarrie = true;
  }else{
    clickedBarrie = false;
  }
}


public void clicked3(){
  if (clickedFire == false){
    clickedFire = true;
  }else{
    clickedFire = false;
  }
}
  
  }     
  
