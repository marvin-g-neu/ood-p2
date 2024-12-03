package cs3500.threetrios.provider.controller.gui;

import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.view.gui.TtGuiView;

/**
 * The base for a player controller where they can get notified
 * when it is their turn and play a move.
 */
public interface PlayerController {

  /**
   * Informs the Player that the game has shifted to their turn.
   */
  void giveTurn();

  /**
   * Returns whether it is the player's turn or not.
   *
   * @return true if player's true false otherwise.
   */
  boolean getIsTurn();

  /**
   * Returns the color of the player associated with the controller.
   *
   * @return the color of this player.
   */
  ThreeTrioColor getColor();

  /**
   * Returns the index selected in the hand.
   *
   * @return the index selected in the hand.
   */
  int getSelectedIdx();

  /**
   * Handles an action when a cell is being selected.
   *
   * @param color   the color of the hand
   * @param handIdx the index of the cell.
   */
  void selectCell(ThreeTrioColor color, int handIdx);

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   * @param x Clicked position on the x-axis.
   * @param y Clicked position on the y-axis.
   */
  void playCell(int x, int y);

  /**
   * Ends the current player's turn.
   */
  void endTurn();

  /**
   * Sets the view.
   *
   * @param view the view
   */
  void setView(TtGuiView view);

  /**
   * Checks if the view exists.
   *
   * @return if the view exists
   */
  boolean hasView();

  /**
   * Refreshes the current view, if the view is null throws exception.
   *
   * @throws IllegalArgumentException if the view is null.
   */
  void refreshView();

  /**
   * Sets the view so that the Player this controller is associated with has their color
   * displayed on the left and the player that is inputted is displayed on the right.
   * If the player inputted is the same as the player associated with the controller then the
   * one on the right will showcase the next player.
   *
   * @param somePlayer A player who is currently in the ThreeTrioGame.
   */

  void setPanels(ThreeTrioColor somePlayer);

  /**
   * tells the view to shows the winning popup.
   */
  void showScorePopup();
}
