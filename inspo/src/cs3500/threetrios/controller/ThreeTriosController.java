package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTriosGraphicalView;

/**
 * Implementation of the controller for Three Trios.
 */
public class ThreeTriosController implements Features, ModelFeatures {

  private final ThreeTriosModel<?> model;
  private final Player player;
  private final ThreeTriosGraphicalView view;
  private int selectedCardIdx;

  /**
   * Creates a new controller.
   *
   * @param model  the model being controlled
   * @param player the player corresponding to this controller instance
   * @param view   the view being controlled
   */
  public ThreeTriosController(ThreeTriosModel<?> model, Player player,
                              ThreeTriosGraphicalView view) {
    this.model = model;
    this.player = player;
    this.view = view;
    selectedCardIdx = -1;
    view.setFeatures(this);
    player.setFeatures(this);
  }


  @Override
  public boolean selectCard(String player, int cardIdx) {
    if (model.isGameOver()) {
      view.showMessage("The game is over.");
      return false;
    }
    if (PlayerColor.valueOf(player).equals(this.player.getPlayerColor())) {
      if (!PlayerColor.valueOf(player).equals(model.getCurrentPlayer())) {
        view.showMessage("Player " + this.player.getPlayerColor().toString()
                + ": It is not your turn.");
        return false;
      } else {
        selectedCardIdx = cardIdx;
        return true;
      }
    }
    view.showMessage("Player " + this.player.getPlayerColor().toString()
            + ": You cannot select a card from your opponent's hand.");
    return false;

  }

  @Override
  public void clickCell(int row, int col) {
    if (model.isGameOver()) {
      view.showMessage("The game is over.");
      return;
    }
    if (selectedCardIdx == -1) {
      view.showMessage("Player " + this.player.getPlayerColor().toString()
              + ": Please select a card from the hand first.");
      return;
    }
    if (!model.legalPlayToCell(row, col)) {
      view.showMessage("Player " + this.player.getPlayerColor().toString()
              + ": Please play to an open cell.");
      return;
    }
    model.playToCell(row, col, selectedCardIdx);
    selectedCardIdx = -1;
  }

  @Override
  public void refreshView() {
    view.refresh();
  }

  @Override
  public void makeVisible() {
    view.makeVisible();
  }

  @Override
  public void notifyCurrentPlayer() {
    if (player.isComputer()) {
      if (player.getPlayerColor().equals(model.getCurrentPlayer())) {
        player.getMove(model);
      }
    } else {
      view.resetSelection(this.player.getPlayerColor());
      refreshView();
      if (this.player.getPlayerColor().equals(model.getCurrentPlayer())) {
        view.showMessage("Player " + this.player.getPlayerColor().toString()
                + ": It is your turn.");
      }
    }
  }

  @Override
  public void notifyGameOver() {
    refreshView();
    if (model.isGameWon()) {
      PlayerColor winner = model.getWinner();
      PlayerColor loser;
      if (winner.equals(PlayerColor.RED)) {
        loser = PlayerColor.BLUE;
      } else {
        loser = PlayerColor.RED;
      }
      view.showMessage("Game over! Player " + winner + " wins " + model.getPlayerScore(winner)
              + " to " + model.getPlayerScore(loser) + ".");
    } else {
      view.showMessage("Game over! The game has ended in a tie.");
    }
  }
}
