package cs3500.threetrios.view;

import cs3500.threetrios.model.ReadOnlyThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;

import java.io.IOException;

/**
 * A Three Trios view implementation using text to display game state.
 */
public class ThreeTriosTextualView implements ThreeTriosView {
  private final ReadOnlyThreeTriosModelInterface model;
  private final Appendable textView;

  /**
   * Constructs a textual view for the Three Trios game using the given Appendable and Readable.
   *
   * @param model    the game model to visualize
   * @param textView the appendable object
   * @throws IllegalArgumentException if any argument is null
   */
  public ThreeTriosTextualView(ReadOnlyThreeTriosModelInterface model, Appendable textView) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.textView = textView;

  }

  @Override
  public String toString() {
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
          default: // should never happen
            throw new IllegalArgumentException("Invalid cell state");
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

  @Override
  public void render() throws IOException {
    textView.append(toString());
  }
}
