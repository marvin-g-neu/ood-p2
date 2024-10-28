package cs3500.threetrios.model;

import java.util.List;

import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.CellColor;
import cs3500.threetrios.model.grid.Grid;

public class ReadOnlyClassicalTTModel extends BaseTTModel {
  /**
   * {@inheritDoc}
   */
  @Override
  public String getCurrentPlayer() {
    // TODO: implement
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getCurrentPlayerHand() {
    // TODO: implement
    return null;
  }

  @Override
  public boolean attackerWinsBattle(AttackValue attacker, AttackValue defender) {
    return false;
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
  public CellColor getCellAt(int row, int col) {
    // TODO: implement
    return null;
  }

  @Override
  public void playTurn(int row, int col, int handIndex) {

  }

  @Override
  public void endTurn() {

  }
}
