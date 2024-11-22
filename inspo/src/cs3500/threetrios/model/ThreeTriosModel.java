package cs3500.threetrios.model;

import java.util.List;

import cs3500.threetrios.controller.ModelFeatures;

/**
 * Behaviors for a game of Three Trios. This interface inherits methods from a read-only game model
 * and defines methods that mutate the model.
 *
 * @param <C> the type of cards used
 */
public interface ThreeTriosModel<C extends Card> extends ReadonlyThreeTriosModel<C> {

  /**
   * Play the given card in the hand of the current player to the cell at the given coordinates.
   * Two phases occur during the turn: the placing phase and the battle phase, in that order.
   * In the placing phase, the card is placed to an empty cell and removed from the current
   * player's hand.
   * In the battle phase, the newly placed card does battle against the opposing player's adjacent
   * cards. Any of the opposing player's cards that lose the battle are flipped.
   * Flipped cards become the current player's cards and remain on the grid.
   * Flipped cards then do battle with their adjacent cards that belong to the opposing player.
   * This is called the combo step, and continues until no cards are flipped.
   * After the battle phase, the turn changes over to the other player.
   *
   * @param row             a 0-index number representing the row of the cell to be played to
   * @param col             a 0-index number representing the column of the cell to be played to
   * @param cardInHandIdx a 0-index number representing the index of the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if the given cell is out of bounds,
   *                                  is currently occupied by another card, or is not a card cell
   * @throws IllegalArgumentException if cardInHandIdx < 0
   *                                  or greater/equal to the number of cards in hand
   */
  void playToCell(int row, int col, int cardInHandIdx);

  /**
   * Starts the game with the given options. The deck given is used to set up the players' hands.
   * Modifying the deck given to this method will not modify the game state in any way.
   * The cell types given are used to set up the grid.
   *
   * @param deck      the cards used to set up and play the game
   * @param width     the width of the grid in the game
   * @param height    the height of the grid in the game
   * @param cellTypes the cell types corresponding to each cell in the game grid
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if deck or cellTypes is null
   * @throws IllegalArgumentException if width or height < 1
   * @throws IllegalArgumentException if there are an even number of card cells in the game
   * @throws IllegalArgumentException if the deck's size is not large enough to set up the game
   * @throws IllegalArgumentException if the deck has non-unique cards or null cards
   * @throws IllegalArgumentException if cellTypes contains a null cell type
   * @throws IllegalArgumentException if the dimensions of cellTypes does not match the dimensions
   *                                  of the game grid
   */
  void startGame(List<C> deck, int width, int height, List<List<CellType>> cellTypes);


  /**
   * Starts the game from a given grid configuration file and a deck configuration file.
   * This method calls the above version of startGame after retrieving the parameters of the game
   * from the config files.
   *
   * @param gridFilePath the file path of the grid config file
   * @param deckFilePath the file path of the deck config file
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if there are an even number of card cells in the game
   * @throws IllegalArgumentException if the deck's size is not large enough to set up the game
   * @throws IllegalArgumentException if the deck has non-unique cards
   * @throws IllegalArgumentException if either of the given paths are null
   * @throws IllegalArgumentException if either of the given files cannot be found or is in the
   *                                  wrong format
   */
  void startGame(String gridFilePath, String deckFilePath);

  /**
   * Adds the feature callbacks (controller) to handle user actions.
   * There may be multiple controllers associated with the model.
   *
   * @param features the features (controller) to handle actions
   */
  void addFeatures(ModelFeatures features);
}
