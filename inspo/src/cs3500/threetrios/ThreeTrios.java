package cs3500.threetrios;

import cs3500.threetrios.controller.ComputerPlayer;
import cs3500.threetrios.controller.HumanPlayer;
import cs3500.threetrios.controller.Player;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.AttackValue;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModelImplementation;
import cs3500.threetrios.strategy.FlipMostCards;
import cs3500.threetrios.strategy.GoForTheCorners;
import cs3500.threetrios.view.ThreeTriosGraphicalView;
import cs3500.threetrios.view.ThreeTriosGraphicalViewImplementation;

import java.util.Arrays;
import java.util.List;

/**
 * Starts a game of Three Trios.
 * When starting the game, player types are configured using command-line arguments. Two arguments
 * are entered. The first represents the Red player and the second represents the Blue player.
 * All arguments are case-insensitive.
 * For a human player, enter "Human". For a computer player that tries to flip the most cards when
 * it makes a move, enter "FlipMostCards". For a computer player that tries to play to the corners,
 * enter "GoForTheCorners."
 * If either of the two arguments are invalid, the program exits.
 */
public final class ThreeTrios {
  /**
   * Main method for ThreeTrios.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    if (args.length < 2) {
      // Run java -jar hw7.jar in terminal
      System.out.println("Usage: java -jar hw7.jar <player1> <player2>");
      System.exit(1);
    }
    // Create an example model
    ThreeTriosModelImplementation model = new ThreeTriosModelImplementation();

    // Sample deck and grid configuration
    List<ThreeTriosCard> deck = getSampleDeck();
    int cols = 3;
    int rows = 3;
    List<List<CellType>> cellTypes = getCellTypes();

    // Player command-line args
    Player player1 = playerConfig(args[0], PlayerColor.RED);
    Player player2 = playerConfig(args[1], PlayerColor.BLUE);

    ThreeTriosGraphicalView view1 = new ThreeTriosGraphicalViewImplementation(model,
            PlayerColor.RED);
    ThreeTriosGraphicalView view2 = new ThreeTriosGraphicalViewImplementation(model,
            PlayerColor.BLUE);

    ThreeTriosController controller1 = new ThreeTriosController(model, player1, view1);
    ThreeTriosController controller2 = new ThreeTriosController(model, player2, view2);

    model.addFeatures(controller1);
    model.addFeatures(controller2);

    // Start the game
    model.startGame(deck, cols, rows, cellTypes);
  }

  private static List<List<CellType>> getCellTypes() {
    List<List<CellType>> cellTypes = Arrays.asList(
            Arrays.asList(CellType.CARDCELL, CellType.CARDCELL, CellType.CARDCELL),
            Arrays.asList(CellType.CARDCELL, CellType.HOLE, CellType.HOLE),
            Arrays.asList(CellType.CARDCELL, CellType.CARDCELL, CellType.CARDCELL)
    );
    return cellTypes;
  }

  private static List<ThreeTriosCard> getSampleDeck() {
    List<ThreeTriosCard> deck = Arrays.asList(
            new ThreeTriosCard("Card1", AttackValue.one, AttackValue.two, AttackValue.three,
                    AttackValue.four),
            new ThreeTriosCard("Card2", AttackValue.five, AttackValue.six, AttackValue.seven,
                    AttackValue.eight),
            new ThreeTriosCard("Card3", AttackValue.nine, AttackValue.A, AttackValue.one,
                    AttackValue.two),
            new ThreeTriosCard("Card4", AttackValue.three, AttackValue.four, AttackValue.five,
                    AttackValue.six),
            new ThreeTriosCard("Card5", AttackValue.seven, AttackValue.eight, AttackValue.nine,
                    AttackValue.A),
            new ThreeTriosCard("Card6", AttackValue.one, AttackValue.two, AttackValue.three,
                    AttackValue.four),
            new ThreeTriosCard("Card7", AttackValue.five, AttackValue.six, AttackValue.seven,
                    AttackValue.eight),
            new ThreeTriosCard("Card8", AttackValue.eight, AttackValue.nine, AttackValue.two,
                    AttackValue.three)
    );
    return deck;
  }

  private static Player playerConfig(String arg, PlayerColor color) {
    if (arg.equalsIgnoreCase("human")) {
      return new HumanPlayer(color);
    } else if (arg.equalsIgnoreCase("FlipMostCards")) {
      return new ComputerPlayer(color, new FlipMostCards());
    } else if (arg.equalsIgnoreCase("GoForTheCorners")) {
      return new ComputerPlayer(color, new GoForTheCorners());
    } else {
      System.out.println("Invalid player type: " + arg);
      System.exit(1);
    }
    return null;
  }
}