import java.util.*;
import java.io.*;

public class ReadThread implements Runnable {
  private InputStream in;
  private Client client;

  public ReadThread(InputStream in, Client client) {
    this.in = in;
    this.client = client;
  }

  public void run() {
    try {
      while(true) {
        if (this.in.available()) {
          BufferedInputStream myIn = new BufferedInputStream(this.in);
          myIn.mark(this.in.available()); // maximum limit of bytes that can be read before mark becomes invalid
          myIn.reset();
          byte[] buffer = new byte[120000];
          myIn.read(buffer, 0, myIn.available());
          int len = buffer.length;
          this.client.onReceiveServerMessage(new String(buffer, 0, len));
        }
      }
    }catch(Exception e) {
      e.printStackTrace();

}
