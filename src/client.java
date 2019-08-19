import java.util.io;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
  private String playerName;
  private ServerConnection ServerConnection;


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

  public static void main(String args []) {
    Scanner s = new Scanner(System.in);
    System.out.print("What is your name?: ");
    String name = s.nextLine();
    Client player = new Client(name);
    System.out.println("Welcome to Uno, " + name + ". Now connecting to server...");
    player.tryConnection(25565);

  }
}
