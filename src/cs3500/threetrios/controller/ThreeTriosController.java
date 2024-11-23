package cs3500.threetrios.controller;

import cs3500.threetrios.controller.players.Player;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.model.rules.RuleKeeper;
import cs3500.threetrios.view.ThreeTriosGUIViewInterface;

import java.io.IOException;
import java.util.Map;

/**
 * Controls the Three Trios game.
 */
public class ThreeTriosController implements Actions, GameListeners {
  private final ThreeTriosModelInterface model;
  private final Player player;
  private final ThreeTriosGUIViewInterface view;
  private final RuleKeeper rules;
  private int cardIdx;
  private Player currentPlayer;

  /**
   * Constructs the controller.
   *
   * @param model  game model
   * @param player game player
   * @param view   game view
   */
  public ThreeTriosController(ThreeTriosModelInterface model, Player player,
                              ThreeTriosGUIViewInterface view) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("model, player or view cannot be null.");
    }
    this.model = model;
    this.player = player;
    this.view = view;
    this.rules = new BasicThreeTriosGame(model);
    this.cardIdx = -1;
    player.callbackFeatures(this);
    this.currentPlayer = player;
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

      String endGameMsg = "The game is over! %s team wins with %d over %d.";
      view.displayMessage(String.format(endGameMsg, winner, model.getScore(winner),
          model.getScore(loser)));
    } else {
      String tieMsg = "The game is over! It's a draw.";
      view.displayMessage(tieMsg);
    }
  }

  @Override
  public void runPlayerTurn() {
    if (player.isHuman() && player.getColor().equals(model.getCurrentPlayer())) {
      this.cardIdx = -1;
      refreshScreen();
      String turnMsg = "Player %s: Make your move.";
      view.displayMessage(String.format(turnMsg, this.player.getColor()));
    } else if (!player.isHuman() && player.getColor().equals(model.getCurrentPlayer())) {
      player.getMakePlay(model);
    }

    if (rules.isGameCompleted()) {
      runGameOver();
    }
    refreshScreen();
    switchPlayer();
  }

  @Override
  public void refreshScreen() {
    try {
      view.render();
    } catch (IOException e) { // IOException should not be a problem for GUI views
      throw new IllegalStateException("Unexpected error occurred while refreshing screen.");
    }
  }

  @Override
  public void setScreenVisible(boolean visible) {
    view.setVisible(visible);
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
      String wrongCardMsg = "Player %s: Cannot access opponent's cards.";
      view.displayMessage(String.format(wrongCardMsg, this.player.getColor()));
      return false;
    }

    if (!color.equals(model.getCurrentPlayer())) {
      String waitTurnMsg = "Player %s: Please wait for your turn.";
      view.displayMessage(String.format(waitTurnMsg, this.player.getColor()));
      return false;
    }

    this.cardIdx = cardIdx;
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
      String selectCardMsg = "Player %s: Select a card first.";
      view.displayMessage(String.format(selectCardMsg, this.player.getColor()));
      return;
    }

    Cell cell = model.getGrid().getCell(row, col);
    CustomCard card = model.getPlayerHand(this.player.getColor()).get(cardIdx);
    if (!rules.isLegalMove(cell, card)) {
      String invalidMoveMsg = "Player %s: Move not allowed.";
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

  private void switchPlayer() {
    if (model.getCurrentPlayer() == PlayerColor.RED) {
      currentPlayer = player;
    } else {
      currentPlayer = player;
    }
    runPlayerTurn();
  }
}
