package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.card.PlayerColor;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of a Three Trios game model using classic rules.
 */
public class ClassicalThreeTriosModel extends BaseThreeTriosModel {
  // implement the methods in the ThreeTriosModelInterface interface
  // for the classical version of the game in hw5
  private Grid grid;
  private List<CustomCard> fullDeck;
  private List<CustomCard> deck;
  private PlayerColor currentPlayer;
  private GameState gameState;
  private List<CustomCard> redHand;
  private List<CustomCard> blueHand;
  private boolean shuffle;

  /**
   * Creates a model for a classic game of Three Trios
   */
  public ClassicalThreeTriosModel() {
    this(false);
  }

  /**
   * Creates a model for a classic game of Three Trios where
   * the deck may be shuffled before the game
   *
   * @param shuffle whether the deck should be shuffled
   */
  public ClassicalThreeTriosModel(boolean shuffle) {
    this.gameState = GameState.NOT_STARTED;
    this.shuffle = shuffle;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    checkGameInProgress();
    return currentPlayer;
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    checkGameInProgress();
    switch (currentPlayer) {
      case RED:
        return redHand;
      case BLUE:
        return blueHand;
      default: // should never happen
        throw new IllegalStateException("Unknown current player");
    }
  }

  @Override
  public boolean attackerWinsBattle(CustomCard attacker, CustomCard defender, Direction attackDirection) {
    if (attacker == null || defender == null || attackDirection == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    int attackerStrength = attacker.getStrength(attackDirection).getStrength();
    switch (attackDirection) {
      case NORTH:
        return attackerStrength > defender.getStrength(Direction.SOUTH).getStrength();
      case EAST:
        return attackerStrength > defender.getStrength(Direction.WEST).getStrength();
      case SOUTH:
        return attackerStrength > defender.getStrength(Direction.NORTH).getStrength();
      case WEST:
        return attackerStrength > defender.getStrength(Direction.EAST).getStrength();
      default: // should never happen
        throw new IllegalArgumentException("Unknown Direction");
    }
  }

  @Override
  public int getScore(PlayerColor player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    checkGameInProgress();

    int score = 0;
    for (Cell cell : getCardCells()) {
      if (cell.getCellColor() == player) {
        score++;
      }
    }
    return score;
  }

  private List<Cell> getCardCells() {
    List<Cell> cells = new ArrayList<>();
    for (int r = 0; r < grid.getCols(); r++) {
      for (int c = 0; c < grid.getRows(); c++) {
        Cell cell = grid.getCell(r, c);
        if (!cell.isHole()) {
          cells.add(cell);
        }
      }
    }
    return cells;
  }

  @Override
  public Grid getGrid() {
    checkGameInProgress();
    return grid;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck, boolean shuffle) {
    grid = gameGrid;
    fullDeck = new ArrayList<>(deck);
    this.deck = new ArrayList<>(deck);
    this.shuffle = shuffle;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) {
    startGame(gameGrid, deck, true);
  }

  @Override
  public CellState getCellStateAt(int row, int col) {
    checkGameInProgress();
    checkInRange(row, col);
    return grid.getCell(row, col).getCardState();
  }

  @Override
  public void playTurn(int row, int col, int handIndex) {
    checkGameInProgress();
    checkInRange(col, row);
    if (handIndex < 0 || handIndex >= getCurrentPlayerHand().size()) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }
    if (grid.getCell(row, col).isHole()) {
      throw new IllegalStateException("Cell is a hole");
    }
    if (!grid.getCell(row, col).isEmpty()) {
      throw new IllegalStateException("Cell already occupied");
    }

    grid.placeCard(getCurrentPlayerHand().get(handIndex), row, col);
  }

  @Override
  public void endTurn() {
    checkGameInProgress();
    if (blueHand.isEmpty() && redHand.isEmpty()) {
      endGame();
    } else {
      switch (currentPlayer) {
        case RED:
          currentPlayer = PlayerColor.BLUE;
        case BLUE:
          currentPlayer = PlayerColor.RED;
      }
    }
  }

  @Override
  public Grid endGame() {
    checkGameInProgress();
    if (redHand.isEmpty() && blueHand.isEmpty()) {
      if (getScore(PlayerColor.RED) > getScore(PlayerColor.BLUE)) {
        gameState = GameState.RED_WIN;
      } else {
        gameState = GameState.BLUE_WIN;
      }
    } else {
      gameState = GameState.EARLY_QUIT;
    }
    return grid;
  }

  private void checkGameInProgress() {
    if (gameState != GameState.IN_PROGRESS) {
      throw new IllegalStateException("Game is not in progress");
    }
  }

  private void checkInRange(int col, int row) {
    if (col < 0 || col >= grid.getCols()) {
      throw new IllegalArgumentException("Column out of bounds");
    }
    if (row < 0 || row >= grid.getRows()) {
      throw new IllegalArgumentException("Row out of bounds");
    }
  }
}