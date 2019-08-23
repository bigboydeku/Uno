import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ServerSideConnection implements Runnable {
  private Socket socket;
  private DataInputStream input;
  private DataOutputStream output;
  private int playerID;
  private String playerName;
  private Server server;

  public ServerSideConnection(Socket socket, int playerID, Server server) {
    this.socket = socket;
    this.playerID = playerID;
    try {
      this.input = new DataInputStream(socket.getInputStream());
      this.output = new DataOutputStream(socket.getOutputStream());
      this.server = server;
    } catch(IOException e) {System.out.println("IOException from ssc.");}
  }

  public void run() {
    try {
      while(true) {
        if (this.input.available() > 0) {
          byte[] buffer = new byte[120000];
          int len = this.input.available();
          this.input.read(buffer, 0, this.input.available());
          //send message to server
          this.server.onMessageFromPlayer(new String(buffer,0,len), this);
        }
      }
    } catch(Exception e) { e.printStackTrace();}
  }


  public void sendMessageToPlayer(String message) {
    try {
      this.output.write(msg.getBytes());
      this.output.flush();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName; 
  }

  public String getPlayerName() {
    return this.playerName;
  }

  public int getPlayerID() {
    return this.playerID;
  }
}
