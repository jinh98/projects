//Moving on a Map/Moving a map around a player   
//there is a cheat; press 0
// This has the main launcher
//Jin Cong Huang Patrick Wei
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;
import java.awt.image.*;
import javax.imageio.*;

//store Panel
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;



//This class is used to start the program and manage the windows
class PlayerMapMovement { 
  
  public static void main(String[] args) { 
    //GameWindow game= new GameWindow();  
    new StartingFrame();
  }
  
}

//This class represents the game window
class GameWindow extends JFrame { 
  GamePanel p;
  
  //Window constructor
  public GameWindow() { 
    setTitle("Starve");
    //setSize(1280,1024);  // set the size of my window to 400 by 400 pixels
    setResizable(true);  // set my window to allow the user to resize it
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
    p = new GamePanel();
    getContentPane().add( p);
    pack(); //makes the frame fit the contents
    setVisible(true);
    JButton storeButton = new JButton("Store"); //add the store button
    storeButton.addActionListener(new StoreButtonListener());
    getContentPane().add(storeButton,BorderLayout.SOUTH);//after clicking the store wasd movement can't work
  }
  
  //This is an inner class that is used to detect a button press
  class StoreButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  { 
      
      storeFrame store = new storeFrame(p.backpack.inventory);
    }
    
  }
  
  
  
// An inner class representing the panel on which the game takes place
  static class GamePanel extends JPanel implements KeyListener{
    
    
    FrameRate frameRate;
    Clock clock;
    Clock wClock;
    Map map;
    Player player;
    Backpack backpack;
    Wolf w1= new Wolf (-10, 50, 100, 88);
    MyKeyListener keyListener = new MyKeyListener();
    boolean collide;
    boolean holdSword, holdPick;
    //constructor
    public GamePanel() { 
      setPreferredSize(new Dimension(1024,768));
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
      frameRate = new FrameRate();
      clock=new Clock();
      wClock = new Clock();
      player= new Player();
      map = new Map(1024,768,player);
      player = new Player();
      backpack = new Backpack(this);
      collide = false;
      holdSword = false;
      holdPick = false;
      //box = new MovingBox(map);
      MyKeyListener keyListener = new MyKeyListener();
      this.addKeyListener(keyListener);
      
      MyMouseListener mouseListener = new MyMouseListener();
      this.addMouseListener(mouseListener);
      
      this.requestFocusInWindow(); 
    }
    
    //*************************************************************************************************** Every round
    public void paintComponent(Graphics g) {
      clock.update();
      frameRate.update();
      this.requestFocusInWindow(); 
      super.paintComponent(g); //required to ensure the panel is correctly redrawn
      
      if (player.getHealth() <= 0){
        
        g.drawString("YOU ARE DEAD" , 500,400);
        g.drawString("Rounds of dragon: " + w1.round, 500,450);
        
        System.out.println("YOU ARE DEAD");
      }else{
        //update the content
        
        player.update();
        //draw the screen
        if(player.isNight()==false){
          map.draw(g);
        }else{
          map.drawNight(g);
        }
        // draw player   
        Graphics2D g2d=(Graphics2D)g;
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int mouseX = (int) b.getX();
        int mouseY = (int) b.getY();
        
        player.draw(g,mouseX,mouseY);
        backpack.draw(g);
        
        player.restoreHealth();
        player.updateHealth(-25);
        //order is important
        player.updateHunger(25);
        player.updateWarm(25);
        
        player.paintHealth(g);
        player.paintHunger(g);
        player.paintWarm(g);
        
        for (int i = 0; i<map.trees.size(); i++){
          map.trees.get(i).restoreHealth();
        }
        
        for (int i = 0; i<map.stones.size(); i++){
          map.stones.get(i).restoreHealth();
        }
        
        for (int i = 0; i<map.barries.size(); i++){
          map.barries.get(i).restoreHealth();
        }
        //update wolf
        wClock.update();
        w1.update(wClock.getElapsedTime(), player);
        w1.draw(g, player);
        w1.paintHealth(g);
        wClock.reset();
        
        
        if (w1.boundingBox.intersects(player.boundingBox)){
          //System.out.println("Collide!");
          collide = true;
          player.loseHealth(5);
        }else{
          collide = false;
        }
        
        //the number of resources in inventory
        g.setColor(Color.BLACK);
        g.fillRect(10,90,100,18);
        g.setColor(Color.WHITE);
        g.drawString("Day/Night:"+player.nightYet(), 10, 102);
        
        //the number of resources in inventory
        g.drawString("1", 224,700);
        g.drawString("2", 290,700);
        g.drawString("3", 360,700);
        g.drawString("4", 432,700);
        g.drawString("5", 495,700);
        g.drawString("6", 568,700);
        
        g.drawString("Wood: " + backpack.inventory.getItem(0), 224,730);
        g.drawString("Stone: " + backpack.inventory.getItem(1), 290,730);
        g.drawString("Barry: " + backpack.inventory.getItem(2), 360,730);
        g.drawString("Fire: " + backpack.inventory.getItem(3), 432,730);
        g.drawString("PickAxe:" + backpack.inventory.getItem(4), 495,730);
        g.drawString("Sword:" + backpack.inventory.getItem(5), 568,730);
        
        //request a repaint
        repaint();
      }//*********************************************************************************
    }
    
    public void keyTyped(KeyEvent e) {      
      
    }
    
    public void keyPressed(KeyEvent e) {
      int x = map.playerX;
      int y = map.playerY;
      
      if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
        System.out.println("left");
        player.move("left");
        w1.xPosition+=10;
        if (x>0 && map.map[y][x-1]==0){
          map.playerX--;
        }
      } else if(e.getKeyChar() == 's' ){
        System.out.println("down");
        player.move("down");
        w1.yPosition-=10;
        if (y < map.map.length-1 && map.map[y+1][x]==0)
          map.playerY++;
        
      } else if(e.getKeyChar() == 'd' ){
        System.out.println("right");
        player.move("right");
        w1.xPosition-=10;
        if (x < map.map[0].length-1 && map.map[y][x+1]==0)
          map.playerX++;
        
      } else if(e.getKeyChar() == 'w' ){
        System.out.println("up");
        player.move("up");
        w1.yPosition+=10;
        if (y>0 && map.map[y-1][x]==0)
          map.playerY--;
        //below are for back pack consumtion
      }else if(e.getKeyChar() == '3' ){
        System.out.println("Pressed 3");
        if (backpack.inventory.getItem(2)>0){
          backpack.inventory.consume(2, 1);
          player.eatBarry();
          //System.out.println("Player Health: "+ player.getHealth());
        }
      }else if(e.getKeyChar() == '4' ){
        System.out.println("Pressed 4");
        if (backpack.inventory.getItem(3)>0){
          backpack.inventory.consume(3, 1);
          player.besideFire();
          player.playerOnFire();
        }
      }else if(e.getKeyChar() == '5'){
        System.out.println("pressed 5");
        if(backpack.inventory.getItem(4)>0){
          player.playerPA();
          holdSword=false;
          holdPick=!holdPick;
        }
      }else if(e.getKeyChar() == '6'){
        System.out.println("pressed 6");
        if(backpack.inventory.getItem(5)>0){
          player.playerS();
          holdPick=false;
          holdSword=!holdSword;
          System.out.println(holdSword);
        }
      }else if(e.getKeyChar() == '0' ){// fire cheat
        System.out.println("Pressed 0");
        for (int i=0; i<99; i++){
          backpack.inventory.storeItem(0);
          backpack.inventory.storeItem(1);
          backpack.inventory.storeItem(2);
        }
        backpack.inventory.storeItem(3);
        backpack.inventory.storeItem(4);
        backpack.inventory.storeItem(5);
      }
      //else if (e.getKeyChar() == '0'){     //this is a fire cheat
      //backpack.inventory.storeItem(3);
      //}
      
    }
    
    
    public void keyReleased(KeyEvent e) { 
      if(e.getKeyChar() == '4'){
        player.playerNormal();
      }
      
      
      
    }
    private class MyKeyListener implements KeyListener {
      
      public void keyTyped(KeyEvent e) {  
      }
      
      public void keyPressed(KeyEvent e) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
        
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
          //System.out.println("YIKES D KEY!");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
          System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
        } 
      }   
      
      public void keyReleased(KeyEvent e) {
      }
    } //end of keyboard listener
    
    // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
    private class MyMouseListener implements MouseListener {
        Boolean draged0 = false;
      Boolean draged1 = false;
      Boolean draged2 = false;
      Boolean draged3 = false;
      public void mouseClicked(MouseEvent e) {
        int playerX=map.playerX;
        int playerY=map.playerY;
        int xPos = player.xPos;
        int yPos = player.yPos;
        int mouseX = e.getX();
        int mouseY = e.getY();
        int playerBoxX;
        int playerBoxY;
        
        int xAway;
        int yAway;
        int newX, newY;
        
        int gift;
        //System.out.println("Mouse Clicked");
        //System.out.println("X:"+e.getX() + " y:"+e.getY());
        
        playerBoxX= 10 * map.boxWidth;
        playerBoxY = 9 * map.boxHeight;
        
        
        //System.out.println(playerBoxX);
        //System.out.println(playerBoxY);   //the lower left corner of the box
        
        xAway = mouseX - playerBoxX;
        yAway = mouseY - playerBoxY;
        
        if (xAway > 0){
          newX = playerX + xAway/map.boxWidth;
        }else if (xAway <0){
          newX = playerX-1 + xAway/map.boxWidth;
        }else {
          newX= playerX;
        }
        
        if (yAway > 0){
          newY = playerY+1 + yAway/map.boxHeight;
        }else if (yAway <0){
          newY = playerY + yAway/map.boxHeight;
        }else{
          newY=playerY;
        }
        
        // System.out.println(newX);
        // System.out.println(newY);
        
        //This is for the wolf/dragon
        if (collide ==true){
          if (mouseX>= w1.xPosition && mouseX <= (w1.xPosition+w1.width) && mouseY>= w1.yPosition && mouseY <= (w1.yPosition+w1.height)){
            if (backpack.inventory.getItem(5) ==0 || holdSword == false){
              //System.out.println("hello?");
              w1.health-=20;
            }else if(holdSword == true && backpack.inventory.getItem(5)>=1){ //every sword upgrade gives twice as more damage
              w1.health-=20;
              w1.health = w1.health - 20*(backpack.inventory.getItem(5));
            }
            System.out.println(w1.health);
          }
          if (w1.health <= 0){
            w1.sendAway(-4000, -2000, 100, 88);
            //give player some rewards each time they slay dragon
            gift = (int)(Math.random()*(3));
            for (int i=0; i< 40; i++){
              backpack.inventory.storeItem(gift); //give gift to player
            }
          }
        }
        
        //check if it is a tree
        if (map.world[newX][newY] instanceof Tree){
          System.out.println("This is a tree!");
          if (((playerX - newX ==1 || newX - playerX ==1 )&& (playerY == newY)) || ((playerY - newY ==1 || newY - playerY ==1 )&& (playerX == newX))){
            
            if (((Tree)map.world[newX][newY]).noHealth() == false && backpack.inventory.getItem(0) <9999){
              // System.out.println("Tree +1");
              ((Tree)map.world[newX][newY]).loseHealth();
              backpack.inventory.storeItem(0);// store the wood
              if (holdPick == true){
              for (int i=0; i< backpack.inventory.getItem(4); i++){
                backpack.inventory.storeItem(0); //
              }
              }
            }
            //System.out.println("Wood amount: "+ backpack.inventory.getItem(0));//out put the amount of wood
               if (player.playerSWA() == true) {
                map.world[newX][newY] = null;
                map.map[newY][newX] = 0;
              }
          }
        }else if (map.world[newX][newY] instanceof Stone){
          System.out.println("This is a stone!");
          if (((playerX - newX ==1 || newX - playerX ==1 )&& (playerY == newY)) || ((playerY - newY ==1 || newY - playerY ==1 )&& (playerX == newX))){
            
            if (((Stone)map.world[newX][newY]).noHealth() == false && backpack.inventory.getItem(1) <9999){
              //System.out.println("Stone +1");
              ((Stone)map.world[newX][newY]).loseHealth();
              if (player.playerCPA() == true){ //if there is pick axe
                if (holdPick == true){
                for (int i=0; i< backpack.inventory.getItem(4); i++){
                backpack.inventory.storeItem(1); //give gift to player
                }
              }
              }else {
                System.out.println("Please get a pick axe first!");
              }
            }
            //System.out.println("Stone amount: "+ backpack.inventory.getItem(1));//out put the amount of stone
               if (player.playerSWA() == true) {
                map.world[newX][newY] = null;
                map.map[newY][newX] = 0;
              }
          }
        }else if (map.world[newX][newY] instanceof Barries){
          System.out.println("This is a barry bush!");
          if (((playerX - newX ==1 || newX - playerX ==1 )&& (playerY == newY)) || ((playerY - newY ==1 || newY - playerY ==1 )&& (playerX == newX))){
            
            if (((Barries)map.world[newX][newY]).noHealth() == false && backpack.inventory.getItem(2) <9999){
              //System.out.println("barries +1");2
              ((Barries)map.world[newX][newY]).loseHealth();
              backpack.inventory.storeItem(2);// store the food
            }
            //System.out.println("Barries amount: "+ backpack.inventory.getItem(2));//out put the amount of stone
            
          }
             if (player.playerSWA() == true) {
                map.world[newX][newY] = null;
                map.map[newY][newX] = 0;
              }
        }
        
      }
      
      public void mousePressed(MouseEvent e) {
        Boolean playerHit = true;
        player.playerHit(playerHit);
         int mX = e.getX();
    int mY = e.getY();
   
   if(mX>=215 && mX<=275 && mY>=667 && mY<=720 && backpack.inventory.getItem(0)>0){
    // mX = backpack.getMouseX(e.getX());
    // mY = backpack.getMouseY(e.getY());
     backpack.clicked0 ();
     this.draged0 = true;
   }else if(mX>=283 && mX<=345 && mY>=667 && mY<=720 && backpack.inventory.getItem(1)>0){
     backpack.clicked1();
     this.draged1 = true;
   }else if (mX>= 352 && mX<= 414 && mY>=667 && mY<=720 && backpack.inventory.getItem(2)>0){
     backpack.clicked2();
     this.draged2 = true;
   }else if (mX>= 420 && mX<=482 && mY>=667 && mY<=720 && backpack.inventory.getItem(3)>0){
     backpack.clicked3();
     this.draged3 = true;
        
      }
        
      }
      
      public void mouseReleased(MouseEvent e) {
        Boolean playerHit = false;
        player.playerHit(playerHit);
          int playerX=map.playerX;
        int playerY=map.playerY;
        int xPos = player.xPos;
        int yPos = player.yPos;
        int mouseX = e.getX();
        int mouseY = e.getY();
        int playerBoxX;
        int playerBoxY;
        
        int xAway;
        int yAway;
        int newX, newY;
        System.out.println("Mouse Clicked");
        System.out.println("X:"+e.getX() + " y:"+e.getY());
        
        
         
        
        playerBoxX= 10 * map.boxWidth;
        playerBoxY = 9 * map.boxHeight;
        
        
        System.out.println(playerBoxX);
        System.out.println(playerBoxY);   //the lower left corner of the box
        
        xAway = mouseX - playerBoxX;
        yAway = mouseY - playerBoxY;
        
        if (xAway > 0){
          newX = playerX + xAway/map.boxWidth;
        }else if (xAway <0){
          newX = playerX-1 + xAway/map.boxWidth;
        }else {
          newX= playerX;
        }
        
        if (yAway > 0){
          newY = playerY+1 + yAway/map.boxHeight;
        }else if (yAway <0){
          newY = playerY + yAway/map.boxHeight;
        }else{
          newY=playerY;
        }
        
       
        
        if (map.world[newX][newY] == null && draged0 == true){
         
         // System.out.println("available for plant tree ");
          if (((playerX - newX ==1 || newX - playerX ==1 )&& (playerY == newY)) || ((playerY - newY ==1 || newY - playerY ==1 )&& (playerX == newX))){
         
              backpack.inventory.consume(0,1);
               
               draged0 = false;
               backpack.clicked0 ();
               System.out.println("this draged" + draged0);
               map.world[newX][newY] = new Tree(newX,newY);
               map.map[newY][newX] = 2;
            }
            
            
          }else if (map.world[newX][newY] == null && draged1 == true){
         
      
          if (((playerX - newX ==1 || newX - playerX ==1 )&& (playerY == newY)) || ((playerY - newY ==1 || newY - playerY ==1 )&& (playerX == newX))){
         
              backpack.inventory.consume(1,1);
               
               draged1 = false;
               backpack.clicked1 ();
              // System.out.println("this draged" + draged1);
               map.world[newX][newY] = new Stone(newX,newY);
               map.map[newY][newX] = 3;
            }
          
      }else if (map.world[newX][newY] == null && draged2 == true){
         
      
          if (((playerX - newX ==1 || newX - playerX ==1 )&& (playerY == newY)) || ((playerY - newY ==1 || newY - playerY ==1 )&& (playerX == newX))){
         
              backpack.inventory.consume(2,1);// 
               
               draged2 = false;
               backpack.clicked2 ();
              // System.out.println("this draged" + draged1);
               map.world[newX][newY] = new Barries(newX,newY);
               map.map[newY][newX] = 4;
            }
      }
      }
      
      public void mouseEntered(MouseEvent e) {
      }
      
      public void mouseExited(MouseEvent e) {
      }
    } //end of mouselistener
  }
  
  
}





//************************      
