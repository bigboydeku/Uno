import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;


public class Server {

  //socket maintainence attributes
  private ServerSocket socket;
  int playerCount;

  //Server side connection
  private ServerSideConnection playerOne;
  private ServerSideConnection playerTwo;

  private enum SERVER_STATE {WAITING_ON_PLAYERS, START, PLAYER_1_TURN, PLAYER_2_TURN, DETERMINING, END}; //TURN_ENDED
  private SERVER_STATE MY_STATE;

  /**
    * Sets up the socket for the server.
  **/
  public Server() {
    playerCount = 0;
    System.out.println("------------ Server --------------");
    MY_STATE = SERVER_STATE.WAITING_ON_PLAYERS;
    try {
      socket = new server_socket(25565);
    }catch(IOException ex) {System.out.println("IO Exception from default constructor.");}
  }


  private void stateHandler(String message, String option, ServerSideConnection ssc) {
    System.out.print("State before stateHandler: "+ this.MY_STATE + "| Input: " message + " " + option);
    switch(MY_STATE) {
      case SERVER_STATE.WAITING_ON_PLAYERS:
        //if we're waiting on these players...
        if(message.equals("ALL_READY")) {
          MY_STATE = SERVER_STATE.START;
          broadcastMessageToAllPlayers("ALL_READY -1");
        }
        break;
      case SERVER_STATE.START:
        if (message.equals("PLAYER_1_TURN") && this.playerOne == ssc) {
          this.MY_STATE = SERVER_STATE.PLAYER_1_TURN;
          return;
        }
        else {
          this.MY_STATE = SERVER_STATE.PLAYER_2_TURN;
          return;
        }
        break;
      case SERVER_STATE.PLAYER_1_TURN:
        break;
      case SERVER_STATE.PLAYER_2_TURN:
        break;
      case SERVER_STATE.DETERMINING:
        break;
      case SERVER_STATE.END:
        break;
    }
    System.out.print("State after stateHandler: " + this.MY_STATE + "| Input: " message + " " + option);
  }

  public void onMessageFromPlayer(String msg, ServerSideConnection ssc) {
    String message = msg.split(" ")[0];
    String option = msg.split(" ")[1];
    if(message.equals("NAME_REG")) {
      ssc.setPlayerName(option);
      ssc.sendMessageToPlayer("NAME_ACCEPT" + option);
    }
    else {
      stateHandler(message, option, ssc);
    }
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
        Thread thread = new Thread(ssc);
        thread.start();
      }
      System.out.println(playerCount + " players have connected.");
      Thread.sleep(1000);


    } catch(IOException e) { System.out.println("IOException within acceptConnections() method.");}
  }

  /* Make new server; else, send stack trace.*/
  public static void main(String args[]) {
    Server serv = new Server();
    try {
      serv.acceptConnections();
    } catch (Exception e) {e.printStackTrace(); }
  }
}
