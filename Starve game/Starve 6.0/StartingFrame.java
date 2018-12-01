/** 
 * Starting menu
 **/


//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

class StartingFrame extends JFrame { 
  
  JFrame thisFrame;
  
  //Constructor - this runs first
  StartingFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(400,400);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    
    //Create a JButton for the centerPanel
    JButton startButton = new JButton("START");
    startButton.addActionListener(new StartButtonListener());
    
    //Create a JButton for the centerPanel
    JButton howButton = new JButton("How To Play");
    howButton.addActionListener(new HowButtonListener());
    
    //Create a JButton for the centerPanel
    JLabel startLabel = new JLabel("                                                  Welome to Starve");
    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(startButton,BorderLayout.SOUTH);
    mainPanel.add(howButton,BorderLayout.NORTH);
    mainPanel.add(startLabel,BorderLayout.CENTER);
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class StartButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("Starting new Game");
      thisFrame.dispose();
      GameWindow game= new GameWindow();   //create a new FunkyFrame (another file that extends JFrame)
      
    }
    
  }
  
  class HowButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("How to play?");
      HowFrame how = new HowFrame();
    }
    
  }
  
  
  //Main method starts this application
  /*public static void main(String[] args) { 
   new StartingFrame();
   
   }*/
  
}