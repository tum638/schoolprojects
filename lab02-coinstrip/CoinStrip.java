
// import dependencies
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class CoinStrip implements TwoPlayerGame {
  // declare static and instance variable
  protected ArrayList<Integer> board;
  protected int[] coinPositions;
  protected HashSet<Integer> coins;
  protected Player currentPlayer;
  protected ArrayList<Player> players = new ArrayList<>();
  protected static Random random = new Random();
  protected static final int BOARD_LENGTH = 8;
  protected static final int MIN_COIN_NUMBER = 3;
  protected static final int MAX_COIN_NUMBER = 6;

  public CoinStrip(Player playerOne, Player playerTwo) {
    board = new ArrayList<>();
    for (int i = 0; i < BOARD_LENGTH; i++) {
      board.add(0);
    }

    coinPositions = generateCoinPositions();
    coins = storeCoins(coinPositions);
    players.add(playerOne);
    players.add(playerTwo);

    currentPlayer = players.get(0);
    // System.out.println(coinPositions);
    addCoinsToBoard(coins, board);

  }

  /**
   * @returns the board of the current Coinstrip game instance
   */
  public ArrayList<Integer> getBoard() {

    // return the board

    return board;
  }

  /**
   * @returns the players list containing player objects
   */
  public ArrayList<Player> getPlayers() {
    return this.players;
  }

  /**
   * Function returns the size of the board.
   * 
   * @returns the board size
   */
  public int getBoardLength() {
    // return the length of the board
    return board.size();
  }

  /**
   * Returns the current index of a specified coin
   *
   * @param resource Describes the position of the coin.
   * @returns The current index of the coin.
   */
  public int getResource(int resource) {
    // returns the the number representation of the position inputed
    return this.getBoard().get(resource - 1);
  }

  /**
   * Sets the game state. i.e Moves a coin
   * 
   * @param resource Which coin to move
   * @param change   Which index to put the coin
   */
  public boolean setResource(int resource, int change) {
    // change the position of a coin.
    ArrayList<Integer> boad = this.getBoard();
    boad.set(change - 1, this.getResource(resource));
    coinPositions[change - 1] = this.getResource(resource);
    boad.set(resource - 1, 0);
    coinPositions[resource - 1] = 0;
    return true;
  }

  /**
   * Returns the number representing the current player.
   * 
   * @returns The current player.
   */
  public int getPlayer() {
    // returns the number representing the current player.
    return players.indexOf(this.currentPlayer);
  }

  /**
   * Changes the current player to the given player.
   * 
   * @param player A player number.
   */
  public void setPlayer(int player) {
    // changes the current player to the given player.
    this.currentPlayer = players.get(player);
  }

  /**
   * checks if game is over.
   */
  public boolean isGameOver() {
    // returns true if game is over.
    ArrayList<Integer> boad = this.getBoard();
    int n = this.coins.size();
    for (int i = 0; i < n; i++) {
      if (boad.get(i) == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Displays the current state of the board
   * 
   * @param none
   */
  public void displayBoard() {
    // prints the board with current coin positions.
    ArrayList<Integer> boad = this.getBoard();
    System.out.println("POSITION | 1   2   3   4   5   6   7   8 |");
    System.out.print("COIN     |");

    for (int i = 0; i < this.getBoardLength(); i++) {
      int coin = boad.get(i);
      if (i != this.getBoardLength() - 1) {
        if (coin != 0) {
          System.out.print(" " + "$" + " |");
        } else {
          System.out.print("   |");
        }
      } else {
        if (coin != 0) {
          System.out.println(" " + "$" + " |");
        } else {
          System.out.println("   |");
        }
      }

    }
  }

  /**
   * Checks if there are non zero entries betweeen start and end
   * 
   * @param start
   * @param end
   * @return boolean
   */

  public boolean containsNonZeroEntry(int start, int end) {
    // check for any coins (nonzero entries) in between where the coin is supposed
    // to go,
    // return false if no coins are in between, return true if there is at least one
    // coin.
    ArrayList<Integer> boad = this.getBoard();
    int startIndex = start;
    startIndex--;
    end--;
    while (startIndex != end) {
      if (boad.get(startIndex) != 0) {
        return true;
      }
      startIndex--;
    }

    return false;

  }

  /**
   * Function - A function that check if a move is valid
   * 
   * @param resource
   * @param change
   * @return returns true if the move is valid, else false.
   */
  public boolean isValidMove(int resource, int change) {
    // checks for all boundary cases and determine whether move is valid or not.
    ArrayList<Integer> boad = this.getBoard();

    if (resource < change) { // check if player is attempting to move a coin backwards
      System.out.println(" ");
      System.out.println("    MMMM, COINS CANNOT MOVE BACKWARD, TRY AGAIN");
      return false;
    }
    if ((resource > this.getBoardLength() || resource < 1)) { // check if the index of the coin is outside array bounds.
      System.out.println(" ");
      System.out.println("    MMMM,THE COIN YOU ENTERED WAS NOT VALID, TRY AGAIN");
      return false;
    }
    if (change > this.getBoardLength() || change < 1) { // check if whether index of board is outside of board.
      System.out.println(" ");
      System.out.println("    MMMM, THE FINAL POSITION WAS NOT VALID, TRY AGAIN");
      return false;
    }
    if (boad.get(resource - 1) == 0) { // check if the coin represented by the index entered is on the board.
      System.out.println(" ");
      System.out.println("    MMMM, THERE IS NO COIN AT THAT POSITION, TRY AGAIN");
      return false;
    }
    if (boad.get(change - 1) != 0) {
      System.out.println(" ");
      System.out.println(

          "   MMMM, THE PLACE YOU WANT TO PUT THE COIN HAS SOMETHING IN IT ALREADY, TRY AGAIN");
      System.out.println("REMEMBER: enter the POSITION that you want the coin to go, NOT the number of spaces moved.");
      return false;
    }
    if (!coins.contains(this.getResource(resource))) {
      System.out.println(" ");
      System.out.println("    MMMM, THE SET OF COINS DOES NOT HAVE THAT COIN, TRY AGAIN");
      return false;
    }
    if (this.containsNonZeroEntry(resource - 1, change)) {
      System.out.println(" ");
      System.out.println(
          "   MMMM, THERE IS A COIN AT AN INDEX BEFORE WHERE YOU WANT TO PUT THE COIN, WHICH IS NOT ALLOWED, TRY AGAIN");
      System.out.println("REMEMBER: enter the POSITION that you want the coin to go, NOT the number of spaces moved.");

      return false;
    }
    return true;
  }

  public static boolean addCoinsToBoard(HashSet<Integer> set, ArrayList<Integer> boad) {
    // check for elements in the set and put them in the correct positions on the
    // board.
    for (int i = 1; i < boad.size(); i++) {
      if (set.contains(i)) {
        boad.set(i, i);
      }
    }
    return true;
  }

  public static int[] generateCoinPositions() {
    // instantiate set object to store coin positions and coins, get random number
    // of coins
    // within constraints of board, return the set of coin positions and set of
    // coins

    int[] coinPos = new int[BOARD_LENGTH];

    // get random number of coins within constraints of board.
    // random.nextInt(MIN_COIN_NUMBER, BOARD_LENGTH);

    int coinsNumber = random.nextInt(MIN_COIN_NUMBER, MAX_COIN_NUMBER);

    int coinCount = 1;
    // get random coin positions.
    // set to keep track of coins that have already
    HashSet<Integer> seen = new HashSet<>();

    while (coinCount <= coinsNumber) {
      int coin = random.nextInt(1, BOARD_LENGTH);
      if (seen.contains(coin)) {
        continue;
      }
      seen.add(coin);
      coinPos[coin] = coin;
      coinCount++;

    }

    return coinPos;

  }

  public static HashSet<Integer> storeCoins(int[] coinPos) {
    HashSet<Integer> entries = new HashSet<>();
    for (int i = 0; i < coinPos.length; i++) { // populate HashSet with coins.
      if (coinPos[i] != 0) {
        entries.add(i);
      }

    }
    return entries;
  }

  public static void main(String[] args) {
    System.out.println("Hi!, Welcome to CoinStrip.");
    System.out.println("Type `javac *.java` then `java Play` to start.");

  }
}

class Player {
  private static int instanceCount = 0;
  protected String name;
  protected int id;

  public Player(String player) {
    instanceCount++;
    this.name = player;
    this.id = instanceCount;
  }

  public String toString() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }
}

class Play {
  public static void main(String[] args) {
    // initialize scanner to allow users to enter their names.
    Scanner s = new Scanner(System.in);

    System.out
        .println(
            "INSTRUCTIONS: The top row of the game describes the positions of coins on the board. To refer to a coin, type in its position and to move a coin to a certain position, type in the that position too. e.g the input `4 2` would move the coin at position 4 to position 2. A coin is represented by a `$`");
    System.out.println(" ");
    boolean reprompt = false;
    String[] names = new String[2];
    // prompt users to enter their names until two names are given.
    do {

      System.out.print(" Who's playing? Please enter two names.  e.g <playerOne> <playerTwo>: ");

      try {
        String namesInput = s.nextLine();
        reprompt = false;
        if (namesInput.split(" ").length < 2) {
          System.out.println("Sorry, you entered one player :(");
          reprompt = true;
        } else {
          names = namesInput.split(" ");
        }
      } catch (Exception e) {
        reprompt = true;
        System.out.println("Invalid input, please retry with given format <playerOne> <playerTwo>");
      }

    } while (reprompt);
    // instantiate the two players
    Player playerOne = new Player(names[0]);
    Player playerTwo = new Player(names[1]);

    boolean isPlayerOne = true;
    CoinStrip c = new CoinStrip(playerOne, playerTwo);

    while (!c.isGameOver()) {

      // tell the users whose turn it is.
      if (isPlayerOne) {
        System.out.println("Player: " + playerOne.toString());
        System.out.println(" ");
      } else {
        System.out.println("Player: " + playerTwo.toString());
        System.out.println(" ");
      }

      // display the current state of the board.
      c.displayBoard();

      // prompt the user to play.
      do {
        reprompt = false;
        System.out.println(" ");
        System.out.print("Which coin? Which position? <coin> <finalposition>: ");

        String coinAndPosition = s.nextLine();
        if (coinAndPosition.split(" ").length < 2) {
          reprompt = true;
          c.displayBoard();
          System.out.println("Player: " + playerTwo.toString());
          System.out.println(" ");
          System.out.println("Please enter both a coin and its final position");
        }
        String[] coinAndPositionArray = coinAndPosition.split(" ");
        try {
          int coin = Integer.parseInt(coinAndPositionArray[0]);
          int position = Integer.parseInt(coinAndPositionArray[1]);
          if (!c.isValidMove(coin, position)) {
            reprompt = true;
            System.out.println("Player: " + playerTwo.toString());
            System.out.println(" ");
            c.displayBoard();
          } else {
            c.setResource(coin, position);
          }
        } catch (Exception e) {
          reprompt = true;
          System.out.println("");
          System.out.println("please enter a valid coin and a valid postion");
        }

      } while (reprompt);

      if (isPlayerOne) {
        isPlayerOne = false;
        c.setPlayer(1);
      } else {
        isPlayerOne = true;
        c.setPlayer(0);
      }

    }
    if (isPlayerOne) {
      isPlayerOne = false;
      c.setPlayer(1);
    } else {
      isPlayerOne = true;
      c.setPlayer(0);
    }
    c.displayBoard();

    System.out.println("Congratulations " + c.getPlayers().get(c.getPlayer()) + "!, you have one this game");

  }
}
