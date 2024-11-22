
package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.view.ThreeTriosGUIView;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.controller.players.Player;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;

public class ThreeTriosController implements Actions, GameListeners {
  private final ClassicalThreeTriosModel model;
  private final Player player;
  private final ThreeTriosGUIView view;
  private final BasicThreeTriosGame rules;
  private int cardIdx;

  public ThreeTriosController(ClassicalThreeTriosModel model, Player player,
                             ThreeTriosGUIView view) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.model = model;
    this.player = player;
    this.view = view;
    this.rules = new BasicThreeTriosGame(model);
    this.cardIdx = -1;
    player.callbackFeatures(this);
  }

  @Override
  public boolean selectCard(String playerColor, int cardIdx) {
    if (rules.isGameCompleted()) {
      view.showMessage("The game is over.");
      return false;
    }
    
    PlayerColor color = PlayerColor.valueOf(playerColor);
    if (!color.equals(this.player.getColor())) {
      view.showMessage("Player " + this.player.getColor() 
          + ": You cannot select a card from your opponent's hand.");
      return false;
    }
    
    if (!color.equals(model.getCurrentPlayer())) {
      view.showMessage("Player " + this.player.getColor() 
          + ": It is not your turn.");
      return false;
    }
    
    this.cardIdx = cardIdx;
    return true;
  }

  @Override
  public void selectCell(int row, int col) {
    if (rules.isGameCompleted()) {
      view.showMessage("The game is over.");
      return;
    }
    
    if (cardIdx == -1) {
      view.showMessage("Player " + this.player.getColor() 
          + ": Please select a card from the hand first.");
      return;
    }
    
    ThreeTriosCell cell = model.getGrid().getCell(row, col);
    CustomCard card = model.getPlayerHand(this.player.getColor()).get(cardIdx);
    if (!rules.isLegalMove(cell, card)) {
      view.showMessage("Player " + this.player.getColor() 
          + ": Please play to an open cell.");
      return;
    }
    
    try {
      model.playTurn(row, col, cardIdx);
      cardIdx = -1;
      runPlayerTurn();
    } catch (IllegalArgumentException e) {
      view.showMessage("Invalid move: " + e.getMessage());
    }
  }

  @Override
  public void refreshScreen() {
    view.render();
  }

  @Override
  public void makeScreenVisible() {
    view.makeVisible();
  }

  @Override
  public void runPlayerTurn() {
    if (player.isHuman() && player.getColor().equals(model.getCurrentPlayer())) {
      view.resetSelection(this.player.getColor());
      refreshScreen();
      view.showMessage("Player " + this.player.getColor() 
          + ": It is your turn.");
    } else if (!player.isHuman() && player.getColor().equals(model.getCurrentPlayer())) {
      player.getMakePlay(model);
    }
    
    if (rules.isGameCompleted()) {
      runGameOver();
    }
  }

  @Override
  public void runGameOver() {
    refreshScreen();
    if (rules.isGameCompleted()) {
      PlayerColor winner = model.getWinner();
      PlayerColor loser = winner.equals(PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
      view.showMessage(String.format("Game over! Player %s wins %d to %d.", 
          winner, model.getPlayerScore(winner), model.getPlayerScore(loser)));
    } else {
      view.showMessage("Game over! The game has ended in a tie.");
    }
  }
}