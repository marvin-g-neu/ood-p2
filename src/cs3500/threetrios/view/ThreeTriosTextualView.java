package cs3500.threetrios.view;

import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;

/**
 * A Three Trios view implementation using text to display game state.
 */
public class ThreeTriosTextualView implements TextualView {
  private final ThreeTriosModelInterface model;

  /**
   * Constructs a textual view for the Three Trios game.
   *
   * @param model the game model to visualize
   * @throws IllegalArgumentException if model is null
   */
  public ThreeTriosTextualView(ThreeTriosModelInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public String render() {
    StringBuilder result = new StringBuilder();

    // Add current player
    result.append("Player: ").append(model.getCurrentPlayer()).append("\n");

    // Add board state
    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        switch (model.getCellStateAt(row, col)) {
          case EMPTY:
            result.append("_");
            break;
          case HOLE:
            result.append(" ");
            break;
          case RED:
            result.append("R");
            break;
          case BLUE:
            result.append("B");
            break;
          default: // Should do something
            break;
        }
      }
      result.append("\n");
    }

    // Add hand information
    result.append("Hand:\n");
    for (CustomCard card : model.getCurrentPlayerHand()) {
      result.append(card).append("\n");
    }

    return result.toString();
  }
}
