/**
 * This interface provides the minimum set of methods that any implementation
 * of a two player game should provide.
 */
public interface TwoPlayerGame {
  /**
   * Returns the current value for a given resource.
   *
   * @param resource Describes the game element.
   * @returns The current value of the resource.
   */  
  public int getResource(int resource);
  
  /**
   * Sets the game state. Should check whether the given parameters are valid
   * by calling isValidMove.  If a move is valid, the 
   *
   * @param resource Which resource to alter (e.g., position, gamepiece, pile
   *                 of matches, etc.)
   * @param change How many resources to add/remove (e.g., how many matches to
   *               remove, the distance a piece moves, etc.)
   */
  public boolean setResource(int resource, int change);
  
  /**
   * Returns the number representing the current player.
   * 
   * @returns The current player.
   */
  public int getPlayer();
  
  /**
   * Changes the current player to the given player.
   * 
   * @param player A player number.
   */
  public void setPlayer(int player);
  
  /**
   * Returns true if the specification of a move describes a legal move given
   * all the rules of the game. Note: this does not check whether the move is
   * good move, only whether it is legal.
   *
   * @param resource Which resource to alter (e.g., position, gamepiece, pile
   *                 of matches, etc.)
   * @param change How many resources to add/remove (e.g., how many matches to
   *               remove, the distance a piece moves, etc.)
   * @return True iff the move is valid.
   */
  public boolean isValidMove(int resource, int change);
  
  /**
   * Returns true if the game is over.
   * @returns True if the game is over, false otherwise.
   */
  public boolean isGameOver();
  
  /**
   * Displays the board on screen.
   */
  public void displayBoard();
}
