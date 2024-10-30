package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.PlayerColor;
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
  protected List<CustomCard> fullDeck;
  protected List<CustomCard> deck;
  protected PlayerColor currentPlayer;
  protected GameState gameState;
  protected List<CustomCard> redHand;
  protected List<CustomCard> blueHand;
  protected boolean shuffle;

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) {
    startGame(gameGrid, deck, true);
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
    if (!rules.isLegalMove(cell, card)) {
      throw new IllegalStateException("Cell is a hole");
    }

    grid.placeCard(card, row, col);
    rules.executeBattlePhase(card, row, col, currentPlayer);
    endTurn();
  }

  @Override
  public void endTurn() {
    checkGameInProgress();
    if (rules.isGameOver()) {
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
    if (rules.isGameOver()) {
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
}
