package cs3500.threetrios;

import cs3500.threetrios.controller.players.ComputerPlayer;
import cs3500.threetrios.controller.players.HumanPlayer;
import cs3500.threetrios.controller.players.Player;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.strategy.MaxFlipsStrategy;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.view.ThreeTriosGUIView;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.PlayerColor;


import java.util.List;

/**
 * Main class for the Three Trios game.
 * Accepts command line arguments to determine player types:
 * - "human" for human player
 * - "flipmost" for computer player using FlipMostCards strategy
 * - "corners" for computer player using GoForTheCorners strategy
 */
public final class ThreeTrios {
  /**
   * Main method that starts the Three Trios game.
   *
   * @param args Command line arguments specifying player types
   */
  public static void main(String[] args) {
    ClassicalThreeTriosModel model = new ClassicalThreeTriosModel();
    
    // setup board and deck
    ThreeTriosBoard board = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/boardWithNoUnreachableCardCells.config"));
    List<CustomCard> deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");

    // configure players
    Player player1 = configurePlayer(args[0], PlayerColor.RED);
    Player player2 = configurePlayer(args[1], PlayerColor.BLUE);

    // create views
    ThreeTriosGUIView view1 = new ThreeTriosGUIView(model, PlayerColor.RED);
    ThreeTriosGUIView view2 = new ThreeTriosGUIView(model, PlayerColor.BLUE);

    // create controllers
    ThreeTriosController controller1 = new ThreeTriosController(model, player1, view1);
    ThreeTriosController controller2 = new ThreeTriosController(model, player2, view2);

    // start game
    model.startGame(board, deck);
  }

  /**
   * Configures a player based on the command line argument.
   *
   * @param playerType The type of player to create
   * @param color The color assigned to the player
   * @return The configured Player object
   */
  private static Player configurePlayer(String playerType, PlayerColor color) {
    switch (playerType.toLowerCase()) {
      case "human":
        return new HumanPlayer(color);
      case "flipmost":
        return new ComputerPlayer(color, new MaxFlipsStrategy());
      case "corners":
        return new ComputerPlayer(color, new CornerStrategy());
      default:
        System.out.println("Invalid player type: " + playerType);
        System.exit(1);
        return null;
    }
  }
}
