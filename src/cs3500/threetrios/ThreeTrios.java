package cs3500.threetrios;

import cs3500.threetrios.controller.players.ComputerPlayer;
import cs3500.threetrios.controller.players.HumanPlayer;
import cs3500.threetrios.controller.players.Player;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.strategy.MaxFlipsStrategy;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.view.ThreeTriosGUIView;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.cell.Cell;


import java.util.List;
import java.io.File;
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
    if (args.length != 2) {
        System.out.println("Usage: java ThreeTrios <player1type> <player2type>");
        System.exit(1);
    }

    // Configure players
    Player redPlayer = configurePlayer(args[0], PlayerColor.RED);
    Player bluePlayer = configurePlayer(args[1], PlayerColor.BLUE);

    // Create model
    ClassicalThreeTriosModel model = new ClassicalThreeTriosModel();
    
    // Create view
    ThreeTriosGUIView view = new ThreeTriosGUIView(model);
    
    // Create controllers for both players
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, view);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, view);
    
    // Register controllers with players
    redPlayer.callbackFeatures(redController);
    bluePlayer.callbackFeatures(blueController);
    
    // Start game with configuration files
    String gridPath = "ood-p2" + File.separator + "docs" + File.separator + "boards" + File.separator + "boardWithNoUnreachableCardCells.config";
    String deckPath = "ood-p2" + File.separator + "docs" + File.separator + "decks" + File.separator + "standardDeck.config";

    Cell[][] grid = new GridFileReader().readFile(gridPath);
    List<CustomCard> deck = new DeckFileReader().readFile(deckPath);
    
    model.startGame(new ThreeTriosBoard(grid), deck);
    view.makeVisible();
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
