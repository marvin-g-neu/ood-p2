package cs3500.threetrios.controller;

import cs3500.threetrios.controller.players.Player;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.view.ThreeTriosGUIView;

import java.util.Map;

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
  public void runGameOver() {
    refreshScreen();
    if (rules.isGameCompleted()) {
      Map<PlayerColor, Integer> scores = Map.of(
          PlayerColor.BLUE, model.getScore(PlayerColor.BLUE),
          PlayerColor.RED, model.getScore(PlayerColor.RED));

      PlayerColor winner = scores.get(PlayerColor.BLUE) > scores.get(PlayerColor.RED)
          ? PlayerColor.BLUE : PlayerColor.RED;
      PlayerColor loser = winner == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;

      String endGameMsg = "Match finished! %s team is victorious with a score of %d against %d.";
      view.displayMessage(String.format(endGameMsg, winner, model.getScore(winner),
          model.getScore(loser)));
    } else {
      String tieMsg = "Match concluded! Neither team emerged victorious - it's a draw.";
      view.displayMessage(tieMsg);
    }
  }

  @Override
  public void runPlayerTurn() {
    if (player.isHuman() && player.getColor().equals(model.getCurrentPlayer())) {
      this.cardIdx = -1;
      refreshScreen();
      String turnMsg = "Team %s: The board awaits your move.";
      view.displayMessage(String.format(turnMsg, this.player.getColor()));
    } else if (!player.isHuman() && player.getColor().equals(model.getCurrentPlayer())) {
      player.getMakePlay(model);
    }

    if (rules.isGameCompleted()) {
      runGameOver();
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
  public boolean selectCard(String playerColor, int cardIdx) {
    if (rules.isGameCompleted()) {
      String gameOverMsg = "This match has concluded.";
      view.displayMessage(gameOverMsg);
      return false;
    }

    PlayerColor color = PlayerColor.valueOf(playerColor);
    if (!color.equals(this.player.getColor())) {
      String wrongCardMsg = "Team %s: You cannot access the opposing team's cards.";
      view.displayMessage(String.format(wrongCardMsg, this.player.getColor()));
      return false;
    }

    if (!color.equals(model.getCurrentPlayer())) {
      String waitTurnMsg = "Team %s: Please wait for your turn.";
      view.displayMessage(String.format(waitTurnMsg, this.player.getColor()));
      return false;
    }

    this.cardIdx = cardIdx;
    view.handleCardClick(color, cardIdx);
    return true;
  }

  @Override
  public void selectCell(int row, int col) {
    if (rules.isGameCompleted()) {
      String gameOverMsg = "This match has concluded.";
      view.displayMessage(gameOverMsg);
      return;
    }

    if (cardIdx == -1) {
      String selectCardMsg = "Team %s: Choose a card from your hand before selecting a cell.";
      view.displayMessage(String.format(selectCardMsg, this.player.getColor()));
      return;
    }

    Cell cell = model.getGrid().getCell(row, col);
    CustomCard card = model.getPlayerHand(this.player.getColor()).get(cardIdx);
    if (!rules.isLegalMove(cell, card)) {
      String invalidMoveMsg = "Team %s: That move is not allowed. Choose an empty cell.";
      view.displayMessage(String.format(invalidMoveMsg, this.player.getColor()));
      return;
    }

    try {
      model.playTurn(row, col, cardIdx);
      cardIdx = -1;
      runPlayerTurn();
    } catch (IllegalArgumentException e) {
      String errorMsg = "Invalid action: %s";
      view.displayMessage(String.format(errorMsg, e.getMessage()));
    }
  }
}
