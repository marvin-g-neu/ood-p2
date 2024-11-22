package cs3500.threetrios.strategy;

import java.io.File;
import java.util.List;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.controller.ModelFeatures;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelImplementation;

/**
 * Mock 3x3 board where the corners cannot be played to.
 */
public class MockModelNoCornersOpen implements ThreeTriosModel<ThreeTriosCard> {

  private final ThreeTriosModelImplementation model;

  /**
   * Constructs a new mock model.
   */
  public MockModelNoCornersOpen() {
    model = new ThreeTriosModelImplementation();
    model.startGame("gridConfigFiles" + File.separator + "5x3AllCardCells.txt",
            "deckConfigFiles" + File.separator + "bigDeck.txt");
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
    return null;
  }

  @Override
  public Cell[][] gridCopy() {
    return new Cell[0][];
  }

  @Override
  public boolean legalPlayToCell(int row, int col) {
    if ((row == 0 && col == 0) || (row == 0 && col == 4) || (row == 2 && col == 0)
            || (row == 2 && col == 4)) {
      return false;
    }
    return model.legalPlayToCell(row, col);
  }

  @Override
  public int numCardsFlippedWhenPlayed(int row, int col, int cardInHandIdx) {
    return 0;
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    return 0;
  }

  @Override
  public void playToCell(int row, int col, int cardInHandIdx) {
    // Stub
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
