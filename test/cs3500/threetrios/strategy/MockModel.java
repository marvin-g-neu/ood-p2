package cs3500.threetrios.strategy;

import cs3500.threetrios.model.*;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.cell.CellState;

import java.util.ArrayList;
import java.util.List;

public class MockModel implements ThreeTriosModelInterface {
  private final StringBuilder log;
  private final List<CustomCard> hand;
  private final Grid grid;
  private GameState state;

  public MockModel(StringBuilder log, List<CustomCard> hand, Grid grid) {
    this.log = log;
    this.hand = hand;
    this.grid = grid;
    this.state = GameState.IN_PROGRESS;
  }

  @Override
  public void startGame(Grid grid, List<CustomCard> deck, boolean shuffle) {
    log.append("startGame called with shuffle: ").append(shuffle).append("\n");
  }

  @Override
  public void startGame(Grid grid, List<CustomCard> deck) {
    log.append("startGame called without shuffle: ").append("\n");
  }


  @Override
  public void playTurn(int cardIndex, int row, int col) {
    log.append(String.format("playTurn called with card: %d, row: %d, col: %d\n", 
        cardIndex, row, col));
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    log.append("getCurrentPlayerHand called\n");
    return hand;
  }

  @Override
  public List<CustomCard> getPlayerHand(PlayerColor color) {
    log.append("Getting current player hand\n");
    return hand;
  }

  @Override
  public Grid getGrid() {
    log.append("Getting grid state\n");
    return grid;
  }

  @Override
  public GameState getGameState() {
    log.append("getGameState called\n");
    return state;
  }

  @Override
  public Grid endGame() {
    log.append("endGame called\n");
    return grid;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    log.append("getCurrentPlayer called\n");
    return PlayerColor.RED;
  }

  @Override
  public int getScore(PlayerColor color) {
    log.append("getScore called for color: ").append(color).append("\n");
    return 0;
  }

  @Override
  public ThreeTriosModelInterface copy() {
    return new MockModel(new StringBuilder(log.toString()), new ArrayList<>(hand), grid);
  }

  @Override
  public CellState getCellStateAt(int row, int col) {
    log.append("getCellStateAt called for row: ").append(row).append(", col: ").append(col).append("\n");
    return CellState.EMPTY;
  }

  public void checkCell(int row, int col) {
    log.append(String.format("Checking cell: (%d, %d)\n", row, col));
  }

  public void checkCornerVulnerability(int row, int col) {
    log.append(String.format("Calculating vulnerability for corner (%d, %d)\n", row, col));
  }
}
