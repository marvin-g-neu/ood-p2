package cs3500.threetrios.provider.controller.gui;

import cs3500.threetrios.controller.Actions;
import cs3500.threetrios.controller.GameListeners;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.view.gui.TtGuiView;
/**
 * Adapter class that implements the provider's PlayerController interface
 * and delegates actions to the user's ThreeTriosController.
 */
public class PlayerControllerAdapter implements PlayerController {
  
  private final Actions controllerActions;
  private final GameListeners gameListeners;
  private final ThreeTrioColor playerColor;

  /**
   * Constructs a PlayerControllerAdapter.
   *
   * @param controllerActions The Actions implementation from ThreeTriosController.
   * @param gameListeners     The GameListeners implementation from ThreeTriosController.
   * @param playerColor       The color associated with this player.
   */
  public PlayerControllerAdapter(Actions controllerActions, GameListeners gameListeners, ThreeTrioColor playerColor) {
    if (controllerActions == null || gameListeners == null || playerColor == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.controllerActions = controllerActions;
    this.gameListeners = gameListeners;
    this.playerColor = playerColor;
  }

  @Override
  public void giveTurn() {
    gameListeners.runPlayerTurn(playerColor);
  }

  @Override
  public boolean getIsTurn() {
    return controllerActions.isPlayerTurn(playerColor);
  }

  @Override
  public ThreeTrioColor getColor() {
    return this.playerColor;
  }

  @Override
  public int getSelectedIdx() {
    return controllerActions.getSelectedIndex(playerColor);
  }

  @Override
  public void selectCell(ThreeTrioColor color, int handIdx) {
    // Convert ThreeTrioColor to PlayerColor
    PlayerColor pc = PlayerColor.valueOf(color.name());
    controllerActions.selectCard(pc, handIdx);
  }

  @Override
  public void playCell(int x, int y) {
    controllerActions.selectCell(x, y);
  }

  @Override
  public void endTurn() {
    controllerActions.endTurn();
  }

  @Override
  public void setView(TtGuiView view) {
    controllerActions.setView(view);
  }

  @Override
  public boolean hasView() {
    return controllerActions.hasView();
  }

  @Override
  public void refreshView() {
    gameListeners.refreshScreen(playerColor);
  }

  @Override
  public void setPanels(ThreeTrioColor somePlayer) {
    controllerActions.setPanels(somePlayer);
  }

  @Override
  public void showScorePopup() {
    gameListeners.runGameOver(playerColor);
  }
}
