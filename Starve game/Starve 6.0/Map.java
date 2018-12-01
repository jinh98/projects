//Moving on a Map/Moving a map around a player   Patrick
//ask for how to delay health regen
//also how to get the amount of wood in the store Panel
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;
import java.awt.image.*;
import javax.imageio.*;

class Map extends World { 
  int boxWidth;
  int boxHeight;
  int visibleWidth, visibleHeight;
  int playerX, playerY;
  int map[][];  //this can be laoded from a file
  ArrayList<Tree> trees = new ArrayList<Tree>();
  ArrayList<Stone> stones = new ArrayList<Stone>();
  ArrayList<Barries> barries = new ArrayList<Barries>();
  //**************
  World world[][];
  
  Image treeFH = Toolkit.getDefaultToolkit().getImage("treeFH.png");
  //**************
  Player player;
  public Map(int xResolution,int yResolution, Player player) { 
    visibleWidth=20; //The size of the visible portion of the map
    visibleHeight=16;  
    
    boxWidth = 1024/visibleWidth;
    boxHeight = 768/visibleHeight;
    
    playerX = 5;
    playerY = 5;
    this.player=player;
    map = loadMapData("map.txt"); 
    world = createMap();
    
  }
  
  public int[][] loadMapData(String filename) { 
    int data[][]=null;
    try {                                 
      
      File f = new File(filename);
      Scanner input = new Scanner(f);   
      data = new int[input.nextInt()][input.nextInt()]; // first two line are map size
      
      for (int j=0;j<data.length;j++){
        for (int i=0;i<data[0].length;i++)   {    
          data[j][i]=input.nextInt();
          //if ((j+1)== data[0].length)
          // System.out.print(data[j][i]);
        }
        //System.out.println("");
      }
      input.close();    
    } catch(Exception E){};
    return data;
  }
  
  //********************************
  /* 0 = empty 
   * 1 = block
   * 2 = tree
   * 3 = stone 
   * ............
   */
  /*public World[][] createMap(String filename){
   World world[][]=null;
   try{
   File f = new File(filename);
   Scanner input = new Scanner(f);
   world = new Resource[input.nextInt()][input.nextInt()];
   for(int i=0;i<world.length;i++){
   for(int j=0;j<world[0].length;j++)
   if (input.nextInt() == 0){
   world[i][j] = null;
   }else if (input.nextInt() == 1){
   world[i][j] = new Block();
   }else if (input.nextInt() == 2 ){
   world[i][j] = new Tree();
   System.out.println("T");
   }else if (input.nextInt() == 3){
   world[i][j] = new Stone();
   }else{
   world[i][j] = null;
   }
   
   }
   
   input.close();
   }catch(Exception E){};
   return world;
   }*/
  
  public World[][] createMap(){
    World[][] world = new World[map[1].length][map.length];
    for(int i=0;i<world.length;i++){
      for(int j=0;j<world[0].length;j++){
        if (map[j][i] == 2){
          world[i][j] =new Tree(i, j);
          trees.add((Tree)(world[i][j]));
        }else if (map[j][i] ==3){
          world[i][j] = new Stone(i,j);
          stones.add((Stone)(world[i][j]));
        }else if (map[j][i]== 4){
          world[i][j] = new Barries(i,j);
          barries.add((Barries)(world[i][j]));
        }
      }
    }
    
    return world;
  }
  
  
//**********************
  public void draw(Graphics g) { 
    Image water = Toolkit.getDefaultToolkit().getImage("water.png");
    Image grass = Toolkit.getDefaultToolkit().getImage("grass.png");
    Image stone = Toolkit.getDefaultToolkit().getImage("stone1.png");
    Image tree = Toolkit.getDefaultToolkit().getImage("tree1.png");
    Image barries = Toolkit.getDefaultToolkit().getImage("barries1.png");
    
    
      for (int j=0;j<visibleHeight;j++)
        for (int i=0;i<visibleWidth;i++) {
        if (((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==0) {
          
          // g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
        }else if(((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==2) {
          //g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
          g.drawImage(tree,(i)*boxWidth,(j-1)*boxHeight-20,null);
        }else if(((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==3){
          // g.setColor(Color.YELLOW);
          //g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
          g.drawImage(stone,i*boxWidth,j*boxHeight-20,null);
          
        }else if(((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==4){
          //g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
          g.drawImage(barries,i*boxWidth,j*boxHeight+15,null);
        }else{
          g.setColor(Color.BLUE);
          g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(water,i*boxWidth,j*boxHeight,null);
        }
        
        
        //draw the rectange
        //-1 to see edges
      
    }
  }
  
  public void drawNight(Graphics g) { 
    Image water = Toolkit.getDefaultToolkit().getImage("waterN.png");
    Image grass = Toolkit.getDefaultToolkit().getImage("grassN.png");
    Image stone = Toolkit.getDefaultToolkit().getImage("stone1N.png");
    Image tree = Toolkit.getDefaultToolkit().getImage("tree1N.png");
    Image barries = Toolkit.getDefaultToolkit().getImage("barries1N.png");
    
    
      for (int j=0;j<visibleHeight;j++)
        for (int i=0;i<visibleWidth;i++) {
        if (((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==0) {
          
          // g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
        }else if(((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==2) {
          //g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
          g.drawImage(tree,(i)*boxWidth,(j-1)*boxHeight-20,null);
        }else if(((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==3){
          // g.setColor(Color.YELLOW);
          //g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
          g.drawImage(stone,i*boxWidth,j*boxHeight-20,null);
          
        }else if(((playerY-visibleHeight/2+j) >=0) && ((playerY-visibleHeight/2+j) < map.length) && ((playerX-visibleWidth/2+i) < map[0].length) && ((playerX-visibleWidth/2+i) >=0 ) && map[playerY-visibleHeight/2+j][playerX-visibleWidth/2+i]==4){
          //g.setColor(Color.LIGHT_GRAY);
          //g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(grass,(i)*boxWidth,j*boxHeight,null);
          g.drawImage(barries,i*boxWidth,j*boxHeight+15,null);
        }else{
          g.setColor(Color.BLUE);
          g.fillRect(i*boxWidth, j*boxHeight, boxWidth-1, boxHeight-1);
          g.drawImage(water,i*boxWidth,j*boxHeight,null);
        }
        
        
        //draw the rectange
        //-1 to see edges
      
    }
  }
  
}