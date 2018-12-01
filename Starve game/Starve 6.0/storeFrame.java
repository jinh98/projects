/** 
 * this template can be used for a start menu
 * for your final project
 **/


//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

class storeFrame extends JFrame { 
  
  JFrame thisFrame;
  Inventory inventory;
  int swordUpgrade=1;
  int pickUpgrade=1;
  //Constructor - this runs first
  storeFrame(Inventory inventory) { 
    super("Store");
    this.thisFrame = this; //lol  
    this.inventory = inventory;
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    pickUpgrade=inventory.getItem(4)+1;
    swordUpgrade=inventory.getItem(5)+1;
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
    
    ///// buttons **********************************************
    //Create a JButton for the centerPanel
    JButton pickButton = new JButton(80*pickUpgrade+" Wood to trade/upgrade pick axe");
    pickButton.addActionListener(new pickButtonListener());
    
    JButton fireButton = new JButton("50 Wood and 50 Stone to trade for fire");
    fireButton.addActionListener(new fireButtonListener());
    
    JButton swordButton = new JButton(50*swordUpgrade+"Wood,"+ 50*swordUpgrade+ " Stone,and 5 fire to trade/upgrade for sword");
    swordButton.addActionListener(new swordButtonListener());
    
    ///// ******************************************************
    //Create a JButton for the centerPanel
    JLabel startLabel = new JLabel("Welcome to the store");
    
    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(pickButton);
    mainPanel.add(fireButton);
    mainPanel.add(swordButton);
    mainPanel.add(startLabel);
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class pickButtonListener implements ActionListener{  //this is the required class definition
    public void actionPerformed(ActionEvent event)  { 
      //System.out.println("Number of wood: " + inventory.getItem(0));
      
      if (inventory.getItem(0) >= 80*pickUpgrade){
        inventory.consume(0, 80*pickUpgrade);
        inventory.storeItem(4);
        System.out.println("bought");
        thisFrame.dispose();
      }
    }
    
  }
  
  class fireButtonListener implements ActionListener{  //this is the required class definition
    public void actionPerformed(ActionEvent event)  { 
      if (inventory.getItem(0) >= 50*swordUpgrade && inventory.getItem(1) >= 50*swordUpgrade){// wood and fire requirement
        inventory.consume(0, 50*swordUpgrade);
        inventory.consume(1, 50*swordUpgrade);
        inventory.storeItem(3);
        System.out.println("bought");
        thisFrame.dispose();
      }
    }
    
  }
  
  class swordButtonListener implements ActionListener{  //this is the required class definition
    public void actionPerformed(ActionEvent event)  { 
      if (inventory.getItem(0) >= 1 && inventory.getItem(1) >= 1 && inventory.getItem(3)>=1){// wood and fire requirement
        inventory.consume(0, 1);
        inventory.consume(1, 1);
        inventory.consume(3, 1);
        inventory.storeItem(5);
        System.out.println("bought");
        thisFrame.dispose();
      }
    }
    
  }
  
  //Main method starts this application
  /*public static void main(String[] args) { 
   new StartingFrame();
   
   }*/
  
}