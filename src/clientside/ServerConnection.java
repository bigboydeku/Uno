/*
  This class is used to establish a connection to the server for the client.
  This will also handle sending and receiving information for the client to use
  to interact with the server and other clients.

*/

import java.util.*;
import java.io.*;

class ServerConnection {
  Socket serverSocket;
  private int attempts = 0;
  private static int MAX_ATTEMPTS = 5;

  public ServerConnection(int port) {
    System.out.println("Attempting to connect to server port:" + port);
    while(!attemptConnection(port)) {
      Thread.sleep(1000);
      this.attempts++;
      if (attempts >= MAX_ATTEMPTS) {
        System.out.println("Failure to connect to server... Exiting program.");
        System.exit(2);
      }
    }
  }

  public boolean attemptConnection(int port) {
    try {
      this.serverSocket socket = new Socket(port);
    }catch(IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public InputStream getInputStream() {
    return this.serverSocket.getInputStream();
  }

  public OutputStream getOutputStream() {
    return this.serverSocket.getOutputStream();
  }

  public boolean isConnected() {
    return this.serverSocket.isConnected();
  }
}
