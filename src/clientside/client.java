import java.util.io;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
  private String playerName;
  private ServerConnection ServerConnection;
  private ReadThread readThread;
  private Thread readingThread;


  private enum PLAYER_STATE {NOT_CONNECTED, NAME_SUBMITTED, PLAYER_TURN, WAITING};
  private PLAYER_STATE MY_STATE;


  public Client(String playerName) {
    this.playerName =  playername;
    this.MY_STATE = PLAYER_STATE.NOT_CONNECTED;
  }

  public void tryConnection(int port) {
    try {
      this.ServerConnection = new ServerConnection(port);
    }catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    if (this.ServerConnection != null) {
      this.MY_STATE = MY_STATE.NAME_SUBMITTED;
      //add methods to send message to server
    }
  }

  public void setUpThreads() {
    try {
      this.readThread = new ReadingThread(this.ServerConnection.getInputStream(), this);
      this.readingThread = new Thread(this.readThread);
      this.readingThread.start();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void onReceiveMessageFromServer(String serverMessage) {
    String message = serverMessage.split(" ")[0];
    String option = serverMessage.split(" ")[1];
    this.stateHandler(message,option);
  }

  private void stateHandler(String message, String option) {
    System.out.print("State before stateHandler: "+ this.MY_STATE + "| Input: " message + " " + option);
    switch(MY_STATE) {
      case MY_STATE.NOT_CONNECTED:
        if (message.equals("ALL_READY")) {
          this.MY_STATE = PLAYER_STATE.WAITING;
          return;
        }
        else if(message.equals("YOUR_TURN")) {
          this.MY_STATE = PLAYER_STATE.PLAYER_TURN;
          System.out.println(" ~~ Your turn ~~");
        }
        break;
      case MY_STATE.NAME_SUBMITTED:
        break;
      case MY_STATE.PLAYER_TURN:
        break;
      case MY_STATE.WAITING:
        break;

    }
    System.out.print("State after stateHandler: " + this.MY_STATE + "| Input: " message + " " + option);
  }

  public static void main(String args []) {
    Scanner s = new Scanner(System.in);
    System.out.print("What is your name?: ");
    String name = s.nextLine();
    Client player = new Client(name);
    System.out.println("Welcome to Uno, " + name + ". Now connecting to server...");
    player.tryConnection(25565);
    System.out.println("Successfully connected to the server.");
    player.setUpThreads();
  }
}
