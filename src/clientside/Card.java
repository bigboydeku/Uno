import java.util.*;

public class Card {



  private enum CARD_NUMBER_STATE { 0,1,2,3,4,5,6,7,8,9;
    public static CARD_NUMBER_STATE getRandom() {
      Random rand = new Random();
      int i = random.nextInt(values().length);
      return values()[i];
    }

  }

// lol
  public static void main(String [] args ) {

  }
}
