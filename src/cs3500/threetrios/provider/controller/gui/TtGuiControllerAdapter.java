package cs3500.threetrios.provider.controller.gui;

import cs3500.threetrios.controller.ControllerManagerInterface;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.provider.model.ThreeTrioColor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

/**
 * Adapter class that implements the provider's TtGuiController interface
 * and delegates actions to the user's ControllerManager.
 */
public class TtGuiControllerAdapter implements TtGuiController {

  private final ControllerManagerInterface controllerManager;
  private final ThreeTriosModelInterface model;
  private final Map<ThreeTrioColor, PlayerController> playerMap;

  /**
   * Constructs a TtGuiControllerAdapter.
   *
   * @param controllerManager The user's ControllerManager.
   * @param model The game model.
   */
  public TtGuiControllerAdapter(ControllerManagerInterface controllerManager, ThreeTriosModelInterface model) {
    if (controllerManager == null || model == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.controllerManager = controllerManager;
    this.model = model;
    this.playerMap = new HashMap<>();
    List<PlayerColor> colors = Arrays.asList(PlayerColor.RED, PlayerColor.BLUE);
    for (PlayerColor color : colors) {
      ThreeTriosController controller = controllerManager.getController(color);
      ThreeTrioColor providerColor = ThreeTrioColor.valueOf(color.name());
      PlayerController adapter = new PlayerControllerAdapter(controller, controller, providerColor);
      playerMap.put(providerColor, adapter);
    }
  }

  @Override
  public void playGame() {
    changeCurrentTurn();
  }

  @Override
  public ThreeTrioColor getCurrentTurn() {
    return ThreeTrioColor.valueOf(model.getCurrentPlayer().name());
  }

  @Override
  public Map<ThreeTrioColor, PlayerController> getPlayerMap() {
    return this.playerMap;
  }

  @Override
  public void changeCurrentTurn() {
    controllerManager.swapTurn(model.getCurrentPlayer());
  }

  @Override
  public void updateAllViews() {
    for (PlayerController pc : playerMap.values()) {
      pc.refreshView();
    }
  }
}
