import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.lang.Integer;



public class FileCompressionPart1 {
  
    public static void main(String[] args) throws IOException {
      
      ArrayList <String> keyList= new ArrayList <String> ();
      String inputFileName="HarryPotter1.txt";
      String outputFileName="testout.txt";
      HashMap<String, Integer> map= new HashMap<String, Integer>();
      PriorityQueue pQueue = new PriorityQueue(1000000);
      Stack nodeStack= new Stack();
      BinaryTree <String> characterTree = new BinaryTree<String>();
      String treeString="";
      int bitDepth;
      int charCount=0;
      
      // read from file
      FileInputStream in = null;
      String output;
      char temp;
      int c;
      
      try {
        in = new FileInputStream(inputFileName);
        
        while ((c = in.read()) != -1) {
          //System.out.println("Input Stream Begins");
          temp= (char)c;
          
          if(map.containsKey(Character.toString(temp))){
            map.replace(Character.toString(temp), Integer.valueOf((map.get(Character.toString(temp))).intValue()+1));// construct the hashmap as the file is read
            
            //System.out.println("map modified");
          }else{
            map.put(Character.toString(temp),1);
            keyList.add(Character.toString(temp));
            //System.out.println("new key made");
          } 
          charCount=charCount+1;
        }
      } finally {
        if (in != null) {
          in.close();
        }
      }  
      
      System.out.println("key list size = ");
      System.out.println(keyList.size());
      // make and insert nodes (trees)
      for (int i=0; i<keyList.size(); i++){
        BinaryTree<String> tempNode=new BinaryTree<String>();
          tempNode.addOrdered(keyList.get(i));
        pQueue.insert(tempNode, map);
        //System.out.println("inserted");
      }
      
      //combine nodes
      while (pQueue.size()>1){
        BinaryTree <String> internalTree= new BinaryTree <String>();// make a sub tree
        internalTree.addOrdered(null);// make a root
        internalTree.getRoot().setRight(pQueue.remove().getRoot());// add two leaves
        internalTree.getRoot().setLeft(pQueue.remove().getRoot());
        pQueue.insert(internalTree, map);// reinsert into the tree
      }
      
      characterTree=(pQueue.remove());// make a tree

      // get the string representation of the tree
      treeString=characterTree.makeTreeString(characterTree.getRoot());
      System.out.println(treeString);
      
      // write to file
      FileInputStream input = null;
      FileOutputStream out = null;

        try {
            in = new FileInputStream(inputFileName);
            out = new FileOutputStream(outputFileName);
            
            int d;
            char temp2;
            String dataPrintOut="";

            out.write(inputFileName.getBytes());// print file name and extension
            out.write(treeString.getBytes());// print tree
            out.write (charCount%8); // print excess characters
            
            while ((d = in.read()) != -1) {
              temp2= (char)d;// convert to string
              dataPrintOut = characterTree.findPath(temp2, characterTree.getRoot(),"");// encode
              out.write((char)Integer.parseInt(dataPrintOut, 2));// print data
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
      
     System.out.println("Completed");
    }// end of main

}// end of class


