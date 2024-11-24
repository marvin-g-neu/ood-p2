package cs3500.threetrios.controller;

import cs3500.threetrios.controller.players.Player;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.view.ThreeTriosGUIViewInterface;

/**
 * Implementation of the controller manager which allows swapping between the
 * two controllers based on which player is active.
 */
public class ControllerManager implements ControllerManagerInterface {
  private final ThreeTriosController redController;
  private final ThreeTriosController blueController;

  public ControllerManager(Player redPlayer, Player bluePlayer,
                           ThreeTriosModelInterface model, ThreeTriosGUIViewInterface redView,
                           ThreeTriosGUIViewInterface blueView) {
    this.redController = new ThreeTriosController(model, redPlayer, redView, this);
    this.blueController = new ThreeTriosController(model, bluePlayer, blueView, this);
  }

  @Override
  public void swapTurn(PlayerColor playerColor) {
    if (playerColor == PlayerColor.RED) {
      redController.refreshScreen();
      redController.setScreenVisible(false);
      blueController.refreshScreen();
      blueController.setScreenVisible(true);
    } else if (playerColor == PlayerColor.BLUE) {
      blueController.refreshScreen();
      blueController.setScreenVisible(false);
      redController.refreshScreen();
      redController.setScreenVisible(true);
    }
  }

  @Override
  public ThreeTriosController getController(PlayerColor playerColor) {
    if (playerColor == PlayerColor.RED) {
      return redController;
    } else {
      return blueController;
    }
  }
}
