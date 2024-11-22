package cs3500.threetrios.controller;

import java.util.List;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Mock model where the game is won. Red has won 5-3.
 */
public class MockModelGameWon implements ThreeTriosModel<ThreeTriosCard> {
  @Override
  public int numOfCardsInDeck() {
    return 0;
  }

  @Override
  public int getGridWidth() {
    return 0;
  }

  @Override
  public int getGridHeight() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return true;
  }

  @Override
  public boolean isGameWon() {
    return true;
  }

  @Override
  public PlayerColor getWinner() {
    return PlayerColor.RED;
  }

  @Override
  public List<ThreeTriosCard> getHand(PlayerColor player) {
    return null;
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
    return false;
  }

  @Override
  public int numCardsFlippedWhenPlayed(int row, int col, int cardInHandIdx) {
    return 0;
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    switch (playerColor) {
      case RED:
        return 5;
      case BLUE:
        return 3;
      default:
        throw new IllegalStateException();
    }
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
