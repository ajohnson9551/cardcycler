package cardcycler;

import java.util.Scanner;

public class helper {
  public static final int totalCards = 52;
  
  public static String[] deck = new String[52];
  
  public static int[] deckNums = new int[52];
  
  public static void main(String[] args) {
    System.out.println("I am Alex's virtual assistant!");
    System.out.println("Please shuffle the deck well.");
    System.out.println("Then, from top to bottom, enter the cards in the deck.");
    System.out.println("Separate by space or comma, for example type 3c for the 3 of clubs.");
    getDeck();
    checkToFlip();
    System.out.println("All done!");
  }
  
  public static void checkToFlip() {
    int[] checked = new int[52];
    int[] cycle = new int[52];
    int first = 0;
    int current = 0;
    int next = 0;
    int length = 0;
    boolean mustCut = false;
    int mode = 0;
    while (mode < 3) {
      int i;
      int i1;
      int i2;
      switch (mode) {
        case 0:
          length = 0;
          first = 0;
          current = 0;
          next = 0;
          mustCut = false;
          cycle = new int[52];
          i = 0;
          while (i < 52 && checked[i] > 0)
            i++; 
          if (i == 52) {
            System.out.println("The deck is good as is!");
            mode = 3;
            continue;
          } 
          first = deckNums[i];
          cycle[0] = i;
          checked[i] = 1;
          if (deckNums[first - 1] == first)
            continue; 
          current = first;
          mode = 1;
        case 1:
          next = deckNums[current - 1];
          checked[current - 1] = 1;
          if (next != first) {
            length++;
            cycle[length] = current - 1;
          } else {
            if (!mustCut) {
              mode = 0;
              continue;
            } 
            mode = 2;
            continue;
          } 
          if (length > 26)
            mustCut = true; 
          current = next;
        case 2:
          i1 = cycle[length];
          i2 = cycle[length / 2];
          System.out.println("Please switch the cards " + deck[i1] + " and " + deck[i2] + ".");
          mode = 3;
      } 
    } 
  }
  
  public static void getDeck() {
    Scanner scan = new Scanner(System.in);
    while (true) {
      String input = scan.nextLine().toLowerCase();
      input = input.replace("10", "1");
      if (!checkInputFormat(input)) {
        System.out.println("Invalid input, please try again.");
        continue;
      } 
      deckNums = new int[52];
      deck = new String[52];
      for (int i = 0; i < 52; i++) {
        char a = input.charAt(3 * i);
        char b = input.charAt(3 * i + 1);
        if (a == '1') {
          deck[i] = String.valueOf(a) + "0" + b;
        } else {
          deck[i] = a + b + "";
        } 
        int val = cardVal(a + b + "");
        if (val == 0) {
          System.out.println("Error! Invalid card! Please try again.");
          break;
        } 
        if (checkForDuplicateCard(val)) {
          System.out.println("Duplicate card!");
          break;
        } 
        deckNums[i] = val;
      } 
      if (deckNums[51] > 0)
        break; 
    } 
    scan.close();
  }
  
  public static boolean checkForDuplicateCard(int val) {
    byte b;
    int i;
    int[] arrayOfInt;
    for (i = (arrayOfInt = deckNums).length, b = 0; b < i; ) {
      int j = arrayOfInt[b];
      if (j > 0 && j == val)
        return true; 
      b++;
    } 
    return false;
  }
  
  public static boolean checkInputFormat(String input) {
    int l = input.length();
    int n = 0;
    int mode = 0;
    for (int i = 0; i < l; i++) {
      char c = input.charAt(i);
      switch (mode) {
        case 0:
          if (checkGoodValue(c)) {
            mode = 1;
            break;
          } 
          System.out.println("Wrong card value!");
          return false;
        case 1:
          if (checkGoodSuit(c)) {
            mode = 2;
            n++;
            break;
          } 
          System.out.println("Wrong suit! c = " + c);
          return false;
        case 2:
          if (c == ' ' || c == ',') {
            mode = 0;
            break;
          } 
          System.out.println("Wrong separator!");
          return false;
      } 
    } 
    if (n == 52)
      return true; 
    System.out.println("Wrong number of cards! n = " + n);
    return false;
  }
  
  public static int cardVal(String input) {
    if (input.length() != 2)
      return 0; 
    char a = input.charAt(0);
    char b = input.charAt(1);
    if (!checkGoodValue(a) || !checkGoodSuit(b))
      return 0; 
    int value = 0;
    if (Character.isDigit(a)) {
      int k = Character.getNumericValue(a);
      if (k == 1)
        k = 10; 
      value += k - 1;
    } else {
      switch (a) {
        case 'j':
          value += 10;
          break;
        case 'q':
          value += 11;
          break;
        case 'k':
          value += 12;
          break;
        case 'a':
          value += 13;
          break;
        default:
          return 0;
      } 
    } 
    switch (b) {
      case 'c':
        return value;
      case 'h':
        value += 13;
      case 's':
        value += 26;
      case 'd':
        value += 39;
    } 
    return 0;
  }
  
  public static boolean checkGoodSuit(char c) {
    return ((c == 'c') | (c == 'h') | (c == 's') | (c == 'd'));
  }
  
  public static boolean checkGoodValue(char c) {
    return Character.isDigit(c) & ((1 <= Character.getNumericValue(c))) & (
      (Character.getNumericValue(c) <= 9)) | ((c == 'j')) | ((c == 'q')) | ((c == 'k')) | ((c == 'a'));
  }
}
