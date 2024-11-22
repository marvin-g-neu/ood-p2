package cs3500.threetrios.strategy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.controller.ModelFeatures;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelImplementation;

/**
 * Mock 3x3 board that records when a strategy calls the model's legalPlayToCell method.
 */
public class MockModelRecordActions implements ThreeTriosModel<ThreeTriosCard> {
  private final ThreeTriosModelImplementation model;
  private final Appendable log;

  /**
   * Constructs a new mock.
   *
   * @param log appendable object
   */
  public MockModelRecordActions(Appendable log) {
    model = new ThreeTriosModelImplementation();
    model.startGame("gridConfigFiles" + File.separator + "3x3AllCardCellsTouch.txt",
            "deckConfigFiles" + File.separator + "smallDeck.txt");
    model.playToCell(1, 1, 0);
    this.log = log;
  }

  @Override
  public int numOfCardsInDeck() {
    return 0;
  }

  @Override
  public int getGridWidth() {
    return model.getGridWidth();
  }

  @Override
  public int getGridHeight() {
    return model.getGridHeight();
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean isGameWon() {
    return false;
  }

  @Override
  public PlayerColor getWinner() {
    return null;
  }

  @Override
  public List<ThreeTriosCard> getHand(PlayerColor player) {
    return model.getHand(player);
  }

  @Override
  public ThreeTriosCard getCardAt(int row, int col) {
    return null;
  }

  @Override
  public CellType getCellTypeAt(int row, int col) {
    return null;
  }

  @Override
  public PlayerColor getCellPlayerAt(int row, int col) {
    return null;
  }

  @Override
  public GameStatus getStatus() {
    return null;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return model.getCurrentPlayer();
  }

  @Override
  public Cell[][] gridCopy() {
    return new Cell[0][];
  }

  @Override
  public boolean legalPlayToCell(int row, int col) {
    try {
      log.append("Checking moves for cell: (" + row + ", " + col + ")\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return model.legalPlayToCell(row, col);
  }

  @Override
  public int numCardsFlippedWhenPlayed(int row, int col, int cardInHandIdx) {
    return model.numCardsFlippedWhenPlayed(row, col, cardInHandIdx);
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    return 0;
  }

  @Override
  public void playToCell(int row, int col, int cardInHandIdx) {
    try {
      log.append("Played card at index " + cardInHandIdx + " to (" + row + ", " + col + ")\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void startGame(List<ThreeTriosCard> deck, int width, int height,
                        List<List<CellType>> cellTypes) {
    // Stub
  }

  @Override
  public void startGame(String gridFilePath, String deckFilePath) {
    // Stub
  }

  @Override
  public void addFeatures(ModelFeatures features) {
    // Stub
  }
}
