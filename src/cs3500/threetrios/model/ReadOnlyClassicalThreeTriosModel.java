package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.card.PlayerColor;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;

import java.util.List;

/**
 * A read only implementation of the Three Trios game for testing.
 */
public class ReadOnlyClassicalThreeTriosModel extends BaseThreeTriosModel {
  @Override
  public PlayerColor getCurrentPlayer() {
    // TODO: implement
    return null;
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    // TODO: implement
    return null;
  }

  @Override
  public boolean attackerWinsBattle(CustomCard attacker, CustomCard defender, Direction attackDirection) {
    return false;
  }

  @Override
  public int getScore(PlayerColor player) {
    return 0;
  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) {

  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck, boolean shuffle) {

  }

  @Override
  public CellState getCellStateAt(int row, int col) {
    // TODO: implement
    return null;
  }

  @Override
  public void playTurn(int row, int col, int handIndex) {

  }

  @Override
  public void endTurn() {

  }

  @Override
  public Grid endGame() {
    return null;
  }
}
