package cs3500.threetrios.view;

import javax.swing.JButton;
import javax.swing.JPanel;

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

  /**
   * Get the JPanel for this hand, meant to be used when constructing the view.
   *
   * @return the JPanel representing this hand
   */
  JPanel getPanel();

  /**
   * Get the selected JButton, or null if there is none selected.
   *
   * @return the selected JButton or null
   */
  JButton getSelected();

  /**
   * Gets the index of the selected JButton, or -1 if there is none selected.
   *
   * @return the index of selected or -1
   */
  int getSelectedIndex();

  /**
   * Set the current selected button to null and reset the outline.
   */
  void deselect();
}
