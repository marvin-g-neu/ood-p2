package cs3500.threetrios.controller;

import java.io.IOException;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Mock computer player that logs actions.
 */
public class MockComputerPlayerLog implements Player {

  private Appendable log;

  /**
   * Creates a new mock.
   *
   * @param log Appendable object
   */
  public MockComputerPlayerLog(Appendable log) {
    this.log = log;
  }

  @Override
  public void getMove(ReadonlyThreeTriosModel<?> model) {
    try {
      log.append("Got the best move for the model\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PlayerColor getPlayerColor() {
    return PlayerColor.RED;
  }

  @Override
  public void setFeatures(Features features) {
    // Stub
  }

  @Override
  public boolean isComputer() {
    return true;
  }
}
