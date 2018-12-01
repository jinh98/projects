

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


class Wolf extends World{ 
  
  double xPosition,yPosition;
  double xSpeed, ySpeed;
  int height,width;
  Rectangle boundingBox;
  static int xPos, yPos; 
  
  int health = 200;
  private int attack = 50;
  private Clock tClock ;
  //contructor
  public Wolf(int x, int y, int w, int h) {
    tClock = new Clock();
    //this.xPos=row;
    //this.yPos=column;
    xPosition = x;
    yPosition = y;
    width=w;
    height=h;
    xSpeed=1;
    boundingBox=new Rectangle((int)xPosition, (int)yPosition, width, height);
  }
  public void sendAway(int x, int y, int w, int h){
    this.health =2000;
    xPosition = x;
    yPosition = y;
    width=w;
    height=h;
    xSpeed=1;
    boundingBox=new Rectangle((int)xPosition, (int)yPosition, width, height);
  }
  public void update(double elapsedTime, Player player){
    //System.out.println(elapsedTime*10+"\n");
    //update the content
    if (xPosition<=(player.xPos)) {
      xSpeed=1;
    }else if (xPosition>player.xPos) {
      xSpeed=-1;
    }
    xPosition= xPosition+xSpeed*elapsedTime*100;  //d = d0 + vt
    boundingBox.x=(int)xPosition;
    
    if (yPosition<=(player.yPos)) {
      ySpeed=1;
    }else if (yPosition>player.yPos) {
      ySpeed=-1;
    }
    
    yPosition= yPosition+ySpeed*elapsedTime*50;  //d = d0 + vt
    boundingBox.y=(int)yPosition;
  }
  
  public void draw(Graphics g, Player player) { 
    Graphics2D g2d=(Graphics2D)g;
    double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
    double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
    
    AffineTransform rotateImage = new AffineTransform();
    rotateImage.translate(xPosition,yPosition);
    int dx = (int)xPosition-player.xPos;
    int dy = (int)yPosition-player.yPos;
    rotateImage.rotate(Math.atan2(dy,dx) + Math.PI/2 ,50,42);
    
    Image dragon = Toolkit.getDefaultToolkit().getImage("Dragon.png");
    //g.setColor(Color.RED); //There are many graphics commands that Java can use
    //g.fillRect((int)xPosition, (int)yPosition, width, height); //notice the y is a variable that we control from our animate method  
    g.drawImage(dragon,(int)xPosition,(int)yPosition,null);
 }
  //**********************
}

