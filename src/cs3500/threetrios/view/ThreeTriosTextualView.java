package cs3500.threetrios.view;

import cs3500.threetrios.model.ReadOnlyClassicalTTModel;

public class ThreeTriosTextualView implements TextualView {
    private final ReadOnlyClassicalTTModel model;

  /**
   * Constructs a textual view for the Three Trios game.
   * @param model the game model to visualize
   * @throws IllegalArgumentException if model is null
   */
  public ThreeTriosTextualView(ReadOnlyClassicalTTModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String render() {
    StringBuilder result = new StringBuilder();
    
    // Add current player
    result.append("Player: ").append(model.getCurrentPlayer()).append("\n");
    
    // Add board state
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        switch (model.getCellAt(row, col)) {
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
        }
      }
      result.append("\n");
    }
    
    // Add hand information
    result.append("Hand:\n");
    for (String card : model.getCurrentPlayerHand()) {
      result.append(card).append("\n");
    }
    
    return result.toString();
  }
}
