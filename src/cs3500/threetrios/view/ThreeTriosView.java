package cs3500.threetrios.view;

import java.io.IOException;
/**
 * An interface for any View for the game Three Trios.
 */
public interface ThreeTriosView {
  /**
   * Render the view for a game of Three Trios.
   *
   * @throws IOException if cannot take input or send output
   */
  void render() throws IOException;
}
