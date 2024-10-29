package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;

import java.util.List;

/**
 * A read only implementation of the Three Trios game for testing.
 */
public class ReadOnlyClassicalThreeTriosModel extends BaseThreeTriosModel {
  /**
   * {@inheritDoc}
   */
  @Override
  public PlayerName getCurrentPlayer() {
    // TODO: implement
    return null;
  }

  /**
   * {@inheritDoc}
   */
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
  public int getScore(PlayerName player) {
    return 0;
  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CellState getCellAt(int row, int col) {
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
