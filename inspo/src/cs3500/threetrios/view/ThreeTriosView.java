package cs3500.threetrios.view;

import java.io.IOException;

/**
 * Interface for the Three Trios game view.
 */
public interface ThreeTriosView {

  /**
   * Renders the game board.
   *
   * @throws IOException if rendering fails
   */
  void renderBoard() throws IOException;

  /**
   * Renders a given message to the destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission to the output fails
   */
  void renderMessage(String message) throws IOException;
}
