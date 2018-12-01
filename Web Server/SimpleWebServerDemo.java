/* [SimpleWebServerDemo.java]
 * Description: This is an example of a project.
 * This server demonstrates how to employ multithreading to accepts multiple clients
 * @author Chris
 * credits to Mr. Mangat for being a real g
 */

/*************************README************************
 * uses get and SQL
 * no post
 * database deleted before submission, so must use registration function
 */

//imports for network communication
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.sql.*;


class SimpleWebServerDemo {
  
  ServerSocket serverSock;// server socket for connection
//  static int page = -1; //wait this is supposed to work for more than one, so i need to not do this
  static Boolean running = true;  // controls if the server is accepting clients
  static Boolean accepting = true;
  /** Main
    * @param args parameters from command line
    */
  public static void main(String[] args) {
    createDatabase(); //for SQL
    new SimpleWebServerDemo().go(); //start the server
  }
  
  /** Go
    * Starts the server
    */
  public void go() {
    System.out.println("Waiting for a client connection..");
    
    Socket client = null;//hold the client connection
    
    try {
      serverSock = new ServerSocket(80);  //assigns an port to the server
      
      // serverSock.setSoTimeout(15000);  //5 second timeout
      
      while(accepting) {  //this loops to accept multiple clients
        client = serverSock.accept();  //wait for connection
        System.out.println("Client connected");
        //Note: you might want to keep references to all clients if you plan to broadcast messages
        //Also: Queues are good tools to buffer incoming/outgoing messages
        Thread t = new Thread(new ConnectionHandler(client)); //create a thread for the new client and pass in the socket
        t.start(); //start the new thread
      }
    }catch(Exception e) {
      // System.out.println("Error accepting connection");
      //close all and quit
      
      try {
        client.close();
      }catch (Exception e1) { 
        System.out.println("Failed to close socket");
      }
      System.exit(-1);
      
    }
  }
  
  //***** Inner class - thread for client connection
  class ConnectionHandler implements Runnable {
    private DataOutputStream output; //assign printwriter to network stream
    private BufferedReader input; //Stream for network input
    private Socket client;  //keeps track of the client socket
    private boolean running;
    
    
    
    /* ConnectionHandler
     * Constructor
     * @param the socket belonging to this client connection
     */    
    ConnectionHandler(Socket s) {
      this.client = s;  //constructor assigns client to this    
      try {  //assign all connections to client
        this.output =  new DataOutputStream(client.getOutputStream());
        InputStreamReader stream = new InputStreamReader(client.getInputStream());
        this.input = new BufferedReader(stream);
      }catch(IOException e) {
        e.printStackTrace();        
      }            
      running = true;
    } //end of constructor
    
    
    /* run
     * executed on start of thread
     */
    public void run() {
      int page = -1; //page -1 means that that you are at the first instance of the homepage
      //when page = 0, assumes that you are trying to get somewhere from the homepage
      
      //Get a message from the client and store it here
      String msg="";
      //get and store username from the login page
      String username = "";
      
      //ready = true when a URL I can use (not an image request) is being sent
      boolean ready = false;
      
      //Get a message from the client
      while(running) {  // loop until a message is received  
        try {
          if (input.ready()) { //check for an incoming messge
            String URL = "";
            int gotURL = 0;
            int contentLength = 0;
            while (input.ready()) {
              msg = input.readLine();  //get a message from the client
              System.out.println(msg);
              if (gotURL < 1) {
                URL = msg;
                gotURL++;
              }

            }
            

            URL = URL.substring(URL.indexOf("/") + 1, URL.indexOf(" HTTP"));            
            
            if (URL.equals("")) {
              
              //the first time, there will be nothing in the URL after its been cut
              //so you want to send the homepage, just so that the user can get started
              sendHTML("homepage.html");
            }
            
            //the browser waits for the server to fulfil its image request before sending another message
            //thus, we only get real messages to do something after the page is fully loaded
            //so, another round after the last image is requested and givem
            //that's why ready is set to false each time
            if (URL.equals("favicon.ico")) {
              sendHTML(URL);
              ready = false;
            }
            if (URL.equals("meme.jpg")) {
              sendHTML(URL);
              ready = false;
            }
            
            /*How the pages thing works
             * -1 = nothing, no such page associated (basically, I want to do nothing, maybe because im sending the homepage)
             * 0 = homepage, with the log-in/register buttons
             * 1 = createProfile page
             * 2 = profile page
             * 3 = profileEdit page
             **/
            
            if (ready == true) { //dont go through the first time otherwise it tries to run this on the homepage with nothing in the url or it will crash
              if (URL.substring(0,8).equals("sign-in?")) { //if the first part of the URL says "sign-in", you know we're at the homepage
                page = 0; //set page = 0 so we know were at the homepage
                username = URL.substring(URL.indexOf("username=") + 9, URL.indexOf("&pass")); //get username, get password from URL using string manipulation
              } else if(URL.substring(0,8).equals("profile?")) { 
                page = 2; //so we know we're at the profile page
              } else if(URL.substring(0,8).equals("log-out?")) {
                sendHTML("homepage.html");
                page = -1; //so we know we just logged out and do nothing until another request arrives
              } else if (URL.substring(0,9).equals("register?")) {
                page = 1; //so we know we're at the registration page
              } else if (URL.substring(0,12).equals("profileEdit?")) {
                page = 3; //so we know we're at the profileEdit page
              }
            }
            ready = true; // the second time around, we can do this thing
            
            if (page == 0) { //if at the homepage, so therefore the log-in/register button is being pressed
              String password = URL.substring(URL.indexOf("password=") + 9, URL.indexOf("&opti")); //get password using string maipulation
              int option = Integer.parseInt(URL.substring(URL.indexOf("option=") + 7)); //get the option (there are 2: log-in and register)
              
              if (option == 0) { //attempt log in and display profile (what if wrong user/pass)
                Boolean authenticated = authenticate(username, password); //check username and password
                if (authenticated == true) { //if correct password matches username
                  sendProfile("profile.html", username);  //send profile corresponding to username
                } else if (authenticated == false) { //if password does not match username, or no username is found
                  sendHTML("nouserpass.html"); //send a webpage that says that "either the username or password is incorrect"
                }
              } else if (option == 1) { //user wants to register
                Boolean alreadyReg = checkUsername(username); //check if username already exists
                if (alreadyReg == false) {
                  sendHTML("alreadyReg.html");
                } else if (alreadyReg == true) {
                  register(username, password);
                  sendHTML("createProfile.html");
                }
              }
              
            }
            if (page == 1) { //if at the create profile page
              //use string manipulation to get the name, favourite color, and description from the submitted URL
              String name = URL.substring(URL.indexOf("name=") + 5, URL.indexOf("&color"));
              String backgroundColor = URL.substring(URL.indexOf("color=") + 6, URL.indexOf("&description"));
              String description = URL.substring(URL.indexOf("description=") + 12);
              
              //save the name, favcolor, and description in the sql database
              insert(username, name, backgroundColor, description);
              //automatically open their profile page after successful registration
              sendProfile("profile.html", username);
            }
            
            if (page == 2) { //if at the profile page, and edit profile was clicked
              sendProfile("profileEdit.html", username);
            }
            
            if (page == 3) { //if at the profileEdit page
              //if a color is picked, then profile can be updated, but if no color picked just reload the page
              //couldn't find a way to have their color preselected, so...
              if (URL.indexOf("&color") != -1) {
                //string manipulation of the URL as usual
                String name = URL.substring(URL.indexOf("name=") + 5, URL.indexOf("&color"));
                String backgroundColor = URL.substring(URL.indexOf("color=") + 6, URL.indexOf("&description"));
                String description = URL.substring(URL.indexOf("description=") + 12);
                update(username, name, backgroundColor, description);
                sendProfile("profile.html", username); //send back the profile if everything is filled in correctly
              } else {
                sendProfile("profileEdit.html", username); //reload the page if not
              }
            }
          }
        }catch (IOException e) {
          System.out.println("Failed to receive msg from the client");
          e.printStackTrace();
        }
      }
      
    } // end of run()
    
    
    //*********************useful methods, tried to keep the main program less cluttered*********************
    
    /**
     * returns true if the user is authenticated, false if not
     * @param username the username to check
     * @param password the password to check with the username
     */
    
    public boolean authenticate (String username, String password) {
      File userpass = new File("userpass.txt");
      try {
        
        Scanner sc = new Scanner(userpass);
        while (sc.hasNextLine()) {
          String user = sc.nextLine();
          if (user.equals(username)) {
            String pass = sc.nextLine();
            if (pass.equals(password)) {
              return true;
            }
          }
        }
        sc.close();
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      
      return false;
      
    }
    
    /**
     * returns true if there is another user with the same username, false if not
     * capitalization does not matter
     * @param username the username to check
     */
    public boolean checkUsername (String username) {
      username = username.toLowerCase();
      File userpass = new File("userpass.txt");
      try {
        
        Scanner sc = new Scanner(userpass);
        while (sc.hasNextLine()) {
          String user = sc.nextLine().toLowerCase();
          if (user.equals(username)) {
            return false;
          }
        }
        sc.close();
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      
      return true;
      
    }
    
    
    /**
     * registers people to the server
     * prints it into a designated text file
     * @param user the username to check
     * @param pass the password to check with the username
     */
    public void register (String user, String pass) {
      File userpass = new File("userpass.txt");
      
      try {
        
        PrintWriter print = new PrintWriter(new FileOutputStream(userpass,true));
        print.println(user);
        print.println(pass);
        print.println("");
        print.close();
        System.out.println("successful registration");
      }
      
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    
    /**
     * method to remove '+' from a string and replace it with ' '
     * because for some reason the spaces are replaced with plusses when you store a parameter in the SQL database
     * returns basically the original string, but the plusses are now spaces
     * @param string the original string
     */
    public String removeSpaces(String input) {
      while (input.indexOf("+") != -1) {
        input = input.substring(0, input.indexOf("+")) + " " + input.substring(input.indexOf("+") +1);
      }
      return input;
    }
    
    /**
     * sends the profile of a user, based on their username
     * uses the SQL database
     * @param HTML determines which file to send, usually either "profile.html", or "profileEdit.html" depending on
     * which page the request comes from
     * @param username the username to use for reference when searching SQL database for other details
     */
    public void sendProfile (String HTML, String username) {
      File original = new File(HTML);
      File webFile = new File("temp.html");
      
      //I use a .html file with variable parameters ex. %COLOR% rather than blue
      //so this code
      //copies the original file to temp.html, but replace anything between % signs
      
      try {
        Scanner sc = new Scanner(original);
        PrintWriter print = new PrintWriter(new FileOutputStream(webFile));
        while (sc.hasNextLine()) {
          //check if % signs in a line and replace text
          String line = sc.nextLine();
          String editedLine = line;
          if (line.indexOf('%') != -1) { //if the %character exists within a line
            int first = line.indexOf('%');
            int second = line.substring(line.indexOf('%') + 1).indexOf('%') + first +1;
            String toReplace = line.substring(first + 1, second);
            if (toReplace.equals("NAME")) {
              //get name from SQL, using the select(username, wantedOutput) method
              editedLine = line.substring(0, first) + select(username, "name") + line.substring(second + 1);
            } else if (toReplace.equals("COLOR")) {
              //get color from SQL, same as above
              editedLine = line.substring(0, first) + select(username, "backgroundcolor") + line.substring(second + 1);
            } else if (toReplace.equals("DESCRIPTION")) {
              editedLine = line.substring(0, first) + select(username, "description") + line.substring(second + 1);
            }
          }
          //print edited line to the temp html file
          //plusses are changed to spaces
          print.println(removeSpaces(editedLine));
        }
        print.close(); //close the PrintWriter
        sendHTML("temp.html"); //send the edited version of the html file, see the below method
        
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    /**
     * sends an html file, method is mostly for convenience and because I didn't want to type this every single time
     * different from sendProfile, becuase you don't use SQL with this
     * for the files like the 'nousepass.html' which doesn't have variables
     * uses a BufferedInputStream to send html to browser
     * @param HTML determines which file to send
     */
    public void sendHTML (String HTML) {
      File webFile = new File(HTML); //file
      BufferedInputStream in = null; 
      
      try {
        in = new BufferedInputStream(new FileInputStream(webFile));
        int data;
        
        System.out.println("File Size: " +webFile.length());
        byte[] d = new byte[(int)webFile.length()];
        
        output.writeBytes("HTTP/1.1 200 OK" + "\n");
        output.flush();
        
        output.writeBytes("Content-Type: text/html"+"\n");
        output.flush();
        output.writeBytes("Content-Length: " + webFile.length() + "\n\n");
        output.flush();
        
        
        
        in.read(d,0,(int)webFile.length());
        
        
        System.out.print(d[0]);
        System.out.println();
        System.out.println("read: " +webFile.length()+" bytes");
        output.write(d,0,d.length);
        System.out.println("sent: " +webFile.length()+" bytes");
        
        
        output.flush();
        
      } catch (IOException e) { 
        e.printStackTrace();}               
      
      
      return;
    }
    
    
    
    
    
  } //end of inner class   
  
  /******************SQL METHODS START HERE*********************************************/
  
  /**
   * updates the SQL database
   * @param username the username to use for reference when updating the database
   * @param name new name
   * @param backgroundcolor new bgcolor
   * @param description new description
   */
  public static void update(String username, String name, String backgroundcolor, String description) {
    Connection c = null;
    Statement stmt = null;
    
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");
      
      stmt = c.createStatement();
      
      //pretty self explanatory (UPDATEs the CUSTOM table, setting NAME to name if USERNAME = username)
      String sql1 = "UPDATE CUSTOM set NAME = '"+name+"' where USERNAME= '"+username+"';";
      String sql2= "UPDATE CUSTOM set BACKGROUNDCOLOR = '"+backgroundcolor+"' where USERNAME= '"+username+"';";
      String sql3 = "UPDATE CUSTOM set DESCRIPTION = '"+description+"' where USERNAME= '"+username+"';";
      stmt.executeUpdate(sql1);
      stmt.executeUpdate(sql2);
      stmt.executeUpdate(sql3);
      c.commit();
      
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
  }
  
  /**
   * creates the SQL database
   * should only run once
   */
  public static void createDatabase() {
    Connection c = null;
    Statement stmt = null;
    
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      System.out.println("Opened database successfully");
      
      stmt = c.createStatement();
      //create table if it doesn't exist
      //with 'columns' of "username", "name", "text", "backgroundcolor", and "description"
      String sql = "CREATE TABLE IF NOT EXISTS CUSTOM" +
        "(USERNAME TEXT PRIMARY KEY NOT NULL, " + 
        " NAME TEXT NOT NULL, " + 
        " BACKGROUNDCOLOR TEXT NOT NULL, " + 
        " DESCRIPTION TEXT)"; 
      
      //properties:
      //primary key = must be a unique value
      //text = must be text
      //not null = must exist
      
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
  
  /**
   * add new 'row' to the SQL database
   * @param username the username to use for reference adding new entry
   * @param name new name
   * @param backgroundColor new bgcolor
   * @param description new description
   */
  public static void insert(String username, String name, String backgroundColor, String description ) {
    Connection c = null;
    Statement stmt = null;
    
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");
      
      stmt = c.createStatement();
      String sql = "INSERT INTO CUSTOM (USERNAME,NAME,BACKGROUNDCOLOR,DESCRIPTION) " +
        "VALUES ('"+username+"', '"+name+"', '"+backgroundColor+"', '"+description+"');"; //pretty self explanatory, see insert method for reference
      stmt.executeUpdate(sql);
      
      stmt.close();
      c.commit();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    }
    System.out.println("Records created successfully");
  }
  
  /**
   * get something from the SQL database
   * @param username the username to use for reference
   * @param need what parameter to be acquired
   */
  public static String select(String username, String need) {
    String query = "";
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");
      
      stmt = c.createStatement();
      //just gets all parameters from the CUSTOM table where for the input username
      ResultSet rs = stmt.executeQuery( "SELECT * FROM CUSTOM WHERE USERNAME = '"+username+"';"); 
      
      while ( rs.next() ) {
        //store all parameters in variable
        String  name = rs.getString("name");
        String  backgroundcolor = rs.getString("backgroundcolor");
        String description = rs.getString("description");
        
        //set query equal to what is needed
        if (need.equals("name" )) {
          query = name;
        } else if (need.equals("backgroundcolor")) {
          query = backgroundcolor;
        } else if (need.equals("description")) {
          query = description;
        }
        
//        System.out.println( "USERNAME = " + username );
//        System.out.println( "NAME = " + name );
//        System.out.println( "BACKGROUNDCOLOR = " + backgroundcolor );
//        System.out.println( "DESCRIPTION = " + description );
//        System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
    return query;
  }
  
} //end of SillyServer class