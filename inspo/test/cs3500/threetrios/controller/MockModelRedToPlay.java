package cs3500.threetrios.controller;

import java.util.List;

import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Mock model where it is Red's turn.
 */
public class MockModelRedToPlay implements ThreeTriosModel<ThreeTriosCard> {


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
    return PlayerColor.RED;
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
