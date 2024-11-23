package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
public class ControllerManager implements ControllerManagerInterface {
  private final GameListeners redController;
  private final GameListeners blueController;

  public ControllerManager(GameListeners redController, GameListeners blueController) {
    this.redController = redController;
    this.blueController = blueController;
  }

  @Override
  public void swapTurn(PlayerColor playerColor) {
    if (playerColor == PlayerColor.RED) {
      redController.setScreenVisible(false);
      blueController.setScreenVisible(true);
    } else if (playerColor == PlayerColor.BLUE) {
      blueController.setScreenVisible(false);
      redController.setScreenVisible(true);
    }
  }
}
