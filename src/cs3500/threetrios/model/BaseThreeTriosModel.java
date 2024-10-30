package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.rules.RuleKeeper;

import java.util.ArrayList;
import java.util.List;
/**
 * An abstract class with common Three Trios model implementations.
 */
public abstract class BaseThreeTriosModel implements ThreeTriosModelInterface {
  protected RuleKeeper rules;
  protected Grid grid;
  protected List<CustomCard> deck;
  protected PlayerColor currentPlayer;

  /*
   * Class invariant: only progresses NOT_STARTED -> IN_PROGRESS -> RED_WIN/BLUE_WIN/EARLY_QUIT.
   * It can only change in this order externally through setGameState() which has checks that it
   * is increasing in order. Only other place it is set internally is in endGame(), where it must
   * be IN_PROGRESS and only can change to one of the final states. An exception is thrown if any
   * calls try to break this rule when calling setGameState() or endGame().
   */
  private GameState gameState;

  protected List<CustomCard> redHand;
  protected List<CustomCard> blueHand;
  protected boolean shuffle;

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) {
    startGame(gameGrid, deck, true);
  }

  @Override
  public GameState getGameState() {
    return gameState;
  }

  /**
   * Set game state externally, only in order of
   * NOT_STARTED -> IN_PROGRESS -> RED_WIN/BLUE_WIN/EARLY_QUIT.
   *
   * @param gameState the state to set this.gameState to
   * @throws IllegalArgumentException if game state is not changed in order
   *                                  or changes between final states
   * @throws IllegalArgumentException if parameter is null
   */
  protected void setGameState(GameState gameState) {
    if (gameState == null) {
      throw new IllegalArgumentException("Parameter cannot be null");
    }
    if (gameState != GameState.NOT_STARTED && this.gameState == null) {
      throw new IllegalArgumentException("Game state must be initialized as NOT_STARTED");

    } else if (gameState.ordinal() != this.gameState.ordinal() + 1 || this.gameState.ordinal() > 1) {
      throw new IllegalArgumentException("Illegal game state change");
    }
    this.gameState = gameState;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    checkGameInProgress();
    return currentPlayer;
  }

  @Override
  public Grid getGrid() {
    checkGameInProgress();
    return grid;
  }

  @Override
  public void playTurn(int row, int col, int handIndex) {
    checkGameInProgress();
    checkInRange(col, row);
    if (handIndex < 0 || handIndex >= getCurrentPlayerHand().size()) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }

    Cell cell = grid.getCell(row, col);
    CustomCard card = getCurrentPlayerHand().get(handIndex);
    if (cell.isHole()) {
      throw new IllegalStateException("Cell is a hole");
    }
    if (!rules.isLegalMove(cell, card)) {
      throw new IllegalStateException("Cell already has a card");
    }

    grid.placeCard(getActualPlayerHand().remove(handIndex), row, col);
    rules.executeBattlePhase(row, col, currentPlayer);
    endTurn();
  }

  /**
   * If the game should end, end the game. Otherwise, pass the turn.
   *
   * @throws IllegalStateException if the game has not been started or is over
   */
  private void endTurn() {
    checkGameInProgress();
    if (rules.isGameCompleted()) {
      endGame();
    } else {
      switch (currentPlayer) {
        case RED:
          currentPlayer = PlayerColor.BLUE;
          break;
        case BLUE:
          currentPlayer = PlayerColor.RED;
          break;
      }
    }
  }

  @Override
  public Grid endGame() {
    // Only changes gameState to a final state and only if it is currently IN_PROGRESS
    checkGameInProgress();
    if (rules.isGameCompleted()) {
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

  protected void checkGameInProgress() {
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

  @Override
  public CellState getCellStateAt(int row, int col) {
    checkGameInProgress();
    checkInRange(row, col);
    return grid.getCell(row, col).getCellState();
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    checkGameInProgress();
    switch (currentPlayer) {
      case RED:
        return new ArrayList<>(redHand);
      case BLUE:
        return new ArrayList<>(blueHand);
      default: // should never happen
        throw new IllegalStateException("Unknown current player");
    }
  }

  private List<CustomCard> getActualPlayerHand() {
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
}
