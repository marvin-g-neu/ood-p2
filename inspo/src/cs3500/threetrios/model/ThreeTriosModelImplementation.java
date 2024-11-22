package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cs3500.threetrios.controller.DeckConfigReader;
import cs3500.threetrios.controller.GridConfigReader;
import cs3500.threetrios.controller.ModelFeatures;

/**
 * Implementation of ThreeTriosModel.
 */
public class ThreeTriosModelImplementation implements ThreeTriosModel<ThreeTriosCard> {

  // The grid is 0-indexed such that the top row is represented by grid[0]. The cell in the top-left
  // corner of the grid is represented by grid[0][0].
  private Cell[][] grid;

  // Contains two lists of ThreeTrios card. The first represents Player Red's hand and the second
  // represents Player Blue's hand.
  // Constructor adds two lists to playerHands and no methods mutate playerHands.
  // INVARIANT (1): playerHands.size() == 2
  // INVARIANT (2): playerHands.get(hand) != null if 0 <= hand < 2
  private final List<List<Card>> playerHands;


  // Cards are removed from the front of the list when cards are distributed to the players' hands.
  // Players do not draw cards after playing to the grid in this implementation.
  private final List<ThreeTriosCard> deck;

  private int gridWidth;
  private int gridHeight;

  private PlayerColor currentPlayer;
  private GameStatus status;

  private List<ModelFeatures> features;

  /**
   * Initializes the Three Trios game such that it is ready for the user to call the startGame
   * method and begin playing the game.
   * Player Red goes first.
   * The game has not started yet.
   */
  public ThreeTriosModelImplementation() {
    grid = new Cell[1][1]; // placeholder until startGame is called
    playerHands = new ArrayList<>();
    playerHands.add(new ArrayList<>());
    playerHands.add(new ArrayList<>());
    deck = new ArrayList<>();
    currentPlayer = PlayerColor.RED;
    status = GameStatus.NotStarted;
    features = new ArrayList<>();
  }

  @Override
  public void playToCell(int row, int col, int cardInHandIdx) {
    if (status.equals(GameStatus.NotStarted) || status.equals(GameStatus.GameOver)) {
      throw new IllegalStateException("Game is not in progress");
    }
    if (cardInHandIdx < 0 || cardInHandIdx >= getHand(currentPlayer).size()) {
      throw new IllegalArgumentException("Invalid card in hand index");
    }
    Cell cell = getCell(row, col); // throws IllegalArgumentException if cell is out of bounds
    if (cell.getCellType().equals(CellType.HOLE)) {
      throw new IllegalArgumentException("Given cell is not a card cell");
    }
    if (cell.getCard() != null) {
      throw new IllegalArgumentException("Given cell is occupied by another card");
    }
    // placing phase
    ThreeTriosCard card = (ThreeTriosCard) playerHands.get(getPlayerIdx(currentPlayer))
            .remove(cardInHandIdx);
    cell.setCard(card);
    cell.setOwner(currentPlayer);
    // battle phase
    battlePhase(row, col);
    // change players
    currentPlayer = getNextPlayer();
    // check if game is over and update status if necessary
    if (isGameOver()) {
      status = GameStatus.GameOver;
      if (!features.isEmpty()) {
        for (ModelFeatures features : this.features) {
          features.refreshView();
        }
        features.get(0).notifyGameOver();
      }
    } else {
      if (!features.isEmpty()) {
        for (ModelFeatures features : this.features) {
          features.refreshView();
        }
        this.features.get(getPlayerIdx(currentPlayer)).notifyCurrentPlayer();
      }
    }
  }

  /**
   * Performs the battle phase for a given cell.
   * The card in the given cell battles its adjacent cards that belong to the opponent.
   * If the card in the given cell wins, the adjacent card is flipped and
   * performs the battle phase again (the combo step).
   * The battle phase continues until no cards are flipped.
   *
   * @param row a 0-index number representing the row of the card performing the battle phase
   * @param col a 0-index number representing the column of the card performing the battle phase
   */
  private void battlePhase(int row, int col) {
    Cell currentCell = getCell(row, col);
    // adjacent card above the card that was played
    if (row - 1 >= 0) {
      Cell adjacentCellAbove = getCell(row - 1, col);
      if (isFlippableCell(adjacentCellAbove)
              && currentCell.getCard().getNorth() > adjacentCellAbove.getCard().getSouth()) {
        adjacentCellAbove.setOwner(currentPlayer);
        battlePhase(row - 1, col); // combo step
      }
    }
    // adjacent card below the card that was played
    if (row + 1 < gridHeight) {
      Cell adjacentCellBelow = getCell(row + 1, col);
      if (isFlippableCell(adjacentCellBelow)
              && currentCell.getCard().getSouth() > adjacentCellBelow.getCard().getNorth()) {
        adjacentCellBelow.setOwner(currentPlayer);
        battlePhase(row + 1, col); // combo step
      }
    }
    // adjacent card to the left the card that was played
    if (col - 1 >= 0) {
      Cell adjacentCellLeft = getCell(row, col - 1);
      if (isFlippableCell(adjacentCellLeft)
              && currentCell.getCard().getWest() > adjacentCellLeft.getCard().getEast()) {
        adjacentCellLeft.setOwner(currentPlayer);
        battlePhase(row, col - 1); // combo step
      }
    }
    // adjacent card to the right the card that was played
    if (col + 1 < gridWidth) {
      Cell adjacentCellRight = getCell(row, col + 1);
      if (isFlippableCell(adjacentCellRight)
              && currentCell.getCard().getEast() > adjacentCellRight.getCard().getWest()) {
        adjacentCellRight.setOwner(currentPlayer);
        battlePhase(row, col + 1); // combo step
      }
    }
  }

  /**
   * Determines whether the card in a given cell is eligible to be flipped during the battle phase.
   * A card is flippable if it contains a card and is not owner by the current player.
   *
   * @param cell the cell that contains the card being evaluated
   * @return true if the card is flippable, false if not
   */
  private boolean isFlippableCell(Cell cell) {
    return cell.getCard() != null && cell.getOwner() != currentPlayer;
  }


  /**
   * Returns the index corresponding to each player.
   * This is used to retrieve a specific player's hand from playerHands.
   *
   * @param player the given player
   * @return the index of the player's hand in playerHands
   */
  private int getPlayerIdx(PlayerColor player) {
    switch (player) {
      case RED:
        return 0;
      case BLUE:
        return 1;
      default:
        throw new IllegalArgumentException("Player cannot be null");
    }
  }

  @Override
  public void startGame(List<ThreeTriosCard> deck, int cols, int rows,
                        List<List<CellType>> cellTypes) {
    checkNotStarted();
    checkNullParams(deck, cellTypes);
    checkGridDimensions(cols, rows);
    checkDeckContents(deck);
    checkCellTypes(cols, rows, cellTypes);
    checkDeckSize(deck, cellTypes);

    this.deck.addAll(deck);
    grid = new Cell[rows][cols];
    gridWidth = cols;
    gridHeight = rows;
    for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
      for (int columnIdx = 0; columnIdx < cols; columnIdx++) {
        grid[rowIdx][columnIdx] = new Cell(cellTypes.get(rowIdx).get(columnIdx));
      }
    }
    for (int playerIdx = 0; playerIdx < 2; playerIdx++) {
      for (int handCount = 0; handCount < (getCardCellCount(cellTypes) + 1) / 2; handCount++) {
        playerHands.get(playerIdx).add(this.deck.remove(0));
      }
    }
    status = GameStatus.Playing;
    if (features.size() == 2) { // does nothing if no features
      this.features.get(1).makeVisible();
      this.features.get(0).makeVisible();
      this.features.get(0).notifyCurrentPlayer();
    }
  }

  @Override
  public void startGame(String gridFilePath, String deckFilePath) {
    if (gridFilePath == null || deckFilePath == null) {
      throw new IllegalArgumentException("File paths cannot be null");
    }

    // getting # of columns, # of rows, and cell types for grid
    GridConfigReader gridConfigReader = new GridConfigReader();
    gridConfigReader.readConfigFile(gridFilePath);

    // getting cards for game
    DeckConfigReader deckConfigReader = new DeckConfigReader();
    deckConfigReader.readConfigFile(deckFilePath);

    List<ThreeTriosCard> gameCards = new ArrayList<>();
    for (Card gameCard : deckConfigReader.getGameCards()) {
      gameCards.add((ThreeTriosCard) gameCard);
    }

    startGame(gameCards, gridConfigReader.getColCount(),
            gridConfigReader.getRowCount(), gridConfigReader.getCellTypes());
  }

  @Override
  public void addFeatures(ModelFeatures features) {
    this.features.add(features);
  }

  /**
   * Throws an exception if the deck is too small to start the game.
   *
   * @param deck      deck of cards
   * @param cellTypes cell types list
   */
  private void checkDeckSize(List<ThreeTriosCard> deck, List<List<CellType>> cellTypes) {
    if (deck.size() < getCardCellCount(cellTypes)) {
      throw new IllegalArgumentException("Deck is not large enough to set up the game");
    }
  }

  /**
   * Throws an exception if the cell types list has invalid dimensions, contains null, or has
   * an even number of card cells.
   *
   * @param cols      number of columns in grid
   * @param rows      number of rows in grid
   * @param cellTypes cell types list
   */
  private void checkCellTypes(int cols, int rows, List<List<CellType>> cellTypes) {
    if (cellTypes.size() != rows) {
      throw new IllegalArgumentException("Cell types list dimensions do not match grid");
    }
    for (List<CellType> row : cellTypes) {
      if (row == null) {
        throw new IllegalArgumentException("Row of cell types cannot be null");
      }
      if (row.size() != cols) {
        throw new IllegalArgumentException("Cell types list dimensions do not match grid");
      }
      if (row.contains(null)) {
        throw new IllegalArgumentException("Cell types list cannot contain null");
      }
    }
    if (getCardCellCount(cellTypes) % 2 == 0) {
      throw new IllegalArgumentException("There must be an odd number of card cells");
    }
  }

  /**
   * Throws an exception if the deck or cell types list is null.
   *
   * @param deck      deck of cards
   * @param cellTypes list of cell types for grid
   */
  private void checkNullParams(List<ThreeTriosCard> deck, List<List<CellType>> cellTypes) {
    if (deck == null || cellTypes == null) {
      throw new IllegalArgumentException("Deck and cell types list cannot be null");
    }
  }

  /**
   * Throws an exception if columns or rows is less than 1.
   *
   * @param cols number of columns in grid
   * @param rows number of rows in grid
   */
  private void checkGridDimensions(int cols, int rows) {
    if (cols < 1 || rows < 1) {
      throw new IllegalArgumentException("Invalid grid dimensions");
    }
  }

  /**
   * Throws an exception if the deck contains null or non-unique cards.
   *
   * @param deck the deck of cards
   */
  private void checkDeckContents(List<ThreeTriosCard> deck) {
    if (deck.contains(null)) {
      throw new IllegalArgumentException("Deck cannot contain null");
    }
    List<String> deckNames = new ArrayList<>();
    for (ThreeTriosCard card : deck) {
      deckNames.add(card.getName());
    }
    if (new HashSet<>(deckNames).size() < deck.size()) {
      throw new IllegalArgumentException("The deck cannot contain non-unique cards");
    }
  }

  /**
   * Throws an exception if the game has started.
   */
  private void checkNotStarted() {
    if (!status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game must not have already started");
    }
  }

  /**
   * Returns the number of card cells from a list of cell types.
   *
   * @param cellTypes the list of cell types
   * @return the card cell count
   */
  private int getCardCellCount(List<List<CellType>> cellTypes) {
    int cardCellCount = 0;
    for (List<CellType> row : cellTypes) {
      for (CellType cellType : row) {
        if (cellType.equals(CellType.CARDCELL)) {
          cardCellCount++;
        }
      }
    }
    return cardCellCount;
  }

  @Override
  public int numOfCardsInDeck() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    return deck.size();
  }

  @Override
  public int getGridWidth() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    return gridWidth;
  }

  @Override
  public int getGridHeight() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    return gridHeight;
  }

  @Override
  public boolean isGameOver() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    boolean gameIsOver = true;
    for (Cell[] row : grid) {
      for (Cell cell : row) {
        if (cell.getCellType().equals(CellType.CARDCELL) && cell.getCard() == null) {
          gameIsOver = false;
          break;
        }
      }
    }
    return gameIsOver;
  }

  @Override
  public boolean isGameWon() {
    if (!status.equals(GameStatus.GameOver)) {
      throw new IllegalStateException("Game is not over");
    }
    int redCardCount = getPlayerScore(PlayerColor.RED);
    int blueCardCount = getPlayerScore(PlayerColor.BLUE);
    return redCardCount != blueCardCount;
  }

  @Override
  public PlayerColor getWinner() {
    if (!status.equals(GameStatus.GameOver)) {
      throw new IllegalStateException("Game is not over");
    }
    if (!isGameWon()) {
      throw new IllegalStateException("There is no winner.");
    }
    if (getPlayerScore(PlayerColor.RED) > getPlayerScore(PlayerColor.BLUE)) {
      return PlayerColor.RED;
    } else {
      return PlayerColor.BLUE;
    }
  }

  /**
   * Returns the next player to play in the game. Called after the current player plays to a cell
   * so that the current player is updated.
   *
   * @return the next player to play
   */
  private PlayerColor getNextPlayer() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    if (currentPlayer.equals(PlayerColor.RED)) {
      return PlayerColor.BLUE;
    } else {
      return PlayerColor.RED;
    }
  }

  @Override
  public List<ThreeTriosCard> getHand(PlayerColor player) {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    List<ThreeTriosCard> handCopy = new ArrayList<>();
    for (Card card : playerHands.get(getPlayerIdx(player))) {
      handCopy.add((ThreeTriosCard) card);
    }
    return handCopy;
  }

  @Override
  public ThreeTriosCard getCardAt(int row, int col) {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    if (getCellTypeAt(row, col).equals(CellType.HOLE)) {
      throw new IllegalArgumentException("Given cell is not a card cell");
    }
    return (ThreeTriosCard) getCell(row, col).getCard();
  }

  /**
   * Retrieves the cell at the given grid coordinates.
   *
   * @param row a 0-index number representing the row of the cell to be queried
   * @param col a 0-index number representing the column of the cell to be queried
   * @return the cell at the given coordinates
   * @throws IllegalArgumentException if the given cell is out of bounds
   */
  private Cell getCell(int row, int col) {
    try {
      return grid[row][col];
    } catch (IndexOutOfBoundsException ex) {
      throw new IllegalArgumentException("Given cell is out of bounds");
    }
  }

  @Override
  public CellType getCellTypeAt(int row, int col) {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    Cell cell = getCell(row, col);
    return cell.getCellType();
  }

  @Override
  public PlayerColor getCellPlayerAt(int row, int col) {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game didn't start");
    }
    Cell cell = getCell(row, col);
    if (cell.getCellType() == CellType.HOLE) {
      throw new IllegalArgumentException("This cell cannot not have a player");
    }
    return cell.getOwner();
  }

  @Override
  public GameStatus getStatus() {
    return this.status;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    if (!status.equals(GameStatus.Playing)) {
      throw new IllegalStateException("Game must be in progress");
    }
    return this.currentPlayer;
  }

  @Override
  public Cell[][] gridCopy() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    Cell[][] gridCopy = new Cell[gridHeight][gridWidth];
    for (int rowIdx = 0; rowIdx < gridHeight; rowIdx++) {
      for (int colIdx = 0; colIdx < gridWidth; colIdx++) {
        gridCopy[rowIdx][colIdx] = new Cell(getCellTypeAt(rowIdx, colIdx));
        gridCopy[rowIdx][colIdx].setCard(getCardAt(rowIdx, colIdx));
        if (gridCopy[rowIdx][colIdx].getCard() != null) {
          gridCopy[rowIdx][colIdx].setOwner(getCellPlayerAt(rowIdx, colIdx));
        }
      }
    }
    return gridCopy;
  }

  @Override
  public boolean legalPlayToCell(int row, int col) {
    if (!status.equals(GameStatus.Playing)) {
      throw new IllegalStateException("Game must be in progress");
    }
    Cell cell = getCell(row, col); // checks out of bounds
    return cell.getCellType() == CellType.CARDCELL && cell.getCard() == null;
  }

  @Override
  public int numCardsFlippedWhenPlayed(int row, int col, int cardInHandIdx) {
    if (!status.equals(GameStatus.Playing)) {
      throw new IllegalStateException("Game must be in progress");
    }
    if (!legalPlayToCell(row, col)) { // also checks out of bounds
      throw new IllegalArgumentException("Cell cannot be played to");
    }
    if (cardInHandIdx < 0 || cardInHandIdx >= playerHands.get(getPlayerIdx(currentPlayer)).size()) {
      throw new IllegalArgumentException("Invalid card in hand index");
    }
    return battlePhaseFlipCount(row, col, playerHands.get(getPlayerIdx(currentPlayer))
            .get(cardInHandIdx));
  }

  /**
   * Helped method for numCardsFlippedWhenPlayed.
   *
   * @param row  a 0-index number representing the row of the card for the battle phase
   * @param col  a 0-index number representing the column of the card for the battle phase
   * @param card the current card doing battle
   * @return the number of cards that would be flipped by playing the given card to the given
   *      coordinates
   */
  private int battlePhaseFlipCount(int row, int col, Card card) {
    int flipCount = 0;
    // adjacent card above the card that was played
    if (row - 1 >= 0) {
      Cell adjacentCellAbove = getCell(row - 1, col);
      if (isFlippableCell(adjacentCellAbove)
              && card.getNorth() > adjacentCellAbove.getCard().getSouth()) {
        flipCount++;
        flipCount += battlePhaseFlipCount(row - 1, col, getCardAt(row - 1, col)); // combo step
      }
    }
    // adjacent card below the card that was played
    if (row + 1 < gridHeight) {
      Cell adjacentCellBelow = getCell(row + 1, col);
      if (isFlippableCell(adjacentCellBelow)
              && card.getSouth() > adjacentCellBelow.getCard().getNorth()) {
        flipCount++;
        flipCount += battlePhaseFlipCount(row + 1, col, getCardAt(row + 1, col)); // combo step
      }
    }
    // adjacent card to the left the card that was played
    if (col - 1 >= 0) {
      Cell adjacentCellLeft = getCell(row, col - 1);
      if (isFlippableCell(adjacentCellLeft)
              && card.getWest() > adjacentCellLeft.getCard().getEast()) {
        flipCount++;
        flipCount += battlePhaseFlipCount(row, col - 1, getCardAt(row, col - 1)); // combo step
      }
    }
    // adjacent card to the right the card that was played
    if (col + 1 < gridWidth) {
      Cell adjacentCellRight = getCell(row, col + 1);
      if (isFlippableCell(adjacentCellRight)
              && card.getEast() > adjacentCellRight.getCard().getWest()) {
        flipCount++;
        flipCount += battlePhaseFlipCount(row, col + 1, getCardAt(row, col + 1)); // combo step
      }
    }
    return flipCount;
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    int count = 0;
    for (Cell[] row : grid) {
      for (Cell cell : row) {
        if (cell.getOwner() != null && cell.getOwner().equals(playerColor)) {
          count++;
        }
      }
    }
    count += playerHands.get(getPlayerIdx(playerColor)).size();
    return count;
  }

  /**
   * Returns a text representation of the current game state.
   * First, the current player is displayed. Then, the grid is printed.
   * Empty card cells are represented by "_". An occupied card cell is represented by a letter
   * representing its owner ("R" or "B" for Red or Blue, respectively).
   * Holes in the grid are represented by a space (" ").
   * Finally, the current player's hand is displayed, showing each card's name and attack values
   * (in the order north, south, east, west).
   *
   * @throws IllegalStateException if the game has not been started
   */
  @Override
  public String toString() {
    if (status.equals(GameStatus.NotStarted)) {
      throw new IllegalStateException("Game has not started");
    }
    StringBuilder gameState = new StringBuilder();
    gameState.append("Player: ").append(currentPlayer.toString()).append("\n");

    // grid render
    for (Cell[] row : grid) {
      for (Cell cell : row) {
        switch (cell.getCellType()) {
          case CARDCELL: {
            if (cell.getOwner() == null) {
              gameState.append("_");
            } else {
              gameState.append(cell.getOwner().toString().charAt(0)); // R or B
            }
            break;
          }
          case HOLE: {
            gameState.append(" ");
            break;
          }
          default: // each cell will have a cell type once the game has started
        }
      }
      gameState.append("\n");
    }

    // current player's hand
    gameState.append("Hand:\n");
    for (int cardInHandIdx = 0; cardInHandIdx < getHand(currentPlayer).size(); cardInHandIdx++) {
      gameState.append(getHand(currentPlayer).get(cardInHandIdx).toString());
      if (cardInHandIdx != getHand(currentPlayer).size() - 1) {
        gameState.append("\n");
      }
    }
    return gameState.toString();
  }
}
