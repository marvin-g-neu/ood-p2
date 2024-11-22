package cs3500.threetrios.view;

/**
 * An interface for managing the Panels representing the player hands.
 */
public interface HandPanelInterface {
  /**
   * Handle the effect of a card in hand being clicked.
   *
   * @param handIndex the index of the card in the player's hand
   * @throws IllegalArgumentException if handIndex is out of range for the player's hand
   */
  void handleCardClick(int handIndex);
}
