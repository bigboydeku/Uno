import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

class Server {

  //socket maintainence attributes
  private ServerSocket socket;
  int playerCount;

  //Server side connection
  private ServerSideConnection playerOne;
  private ServerSideConnection playerTwo;


  /**
    * Sets up the socket for the server.
  **/
  public Server() {
    playerCount = 0;
    System.out.println("------------ Server --------------");
    try {
      socket = new server_socket(25565);
    }catch(IOException ex) {System.out.println("IO Exception from default constructor.");}
  }


  public void acceptConnections() throws InterruptedException {
    try {
      System.out.println("Awaiting connections...");
      while(playerCount < 2) {
        Socket s = socket.accept();
        playerCount++;
        System.out.println("Player #" + playerCount + " connected.");
        ServerSideConnection ssc = new  ServerSideConnection(); //to be determined parameters

        if (playerCount == 1)
          playerOne = ssc;
        else
          playerTwo = ssc;
      }

    } catch(IOException e) { System.out.println("IOException within acceptConnections() method.");}
  }

  /* Make new server; else, send stack trace.*/
  public static void main(String args[]) {
    try {
      Server serv = new Server();
    } catch (Exception e) {e.printStackTrace(); }
  }
}
