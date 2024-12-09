package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;

/**
 * Implementation of a representation of a player hand by extending JPanel.
 */
public class HandPanel extends CardPanel implements HandPanelInterface {
  private JButton selection;
  private int selectionIndex;
  private final PlayerColor player;
  private final boolean viewOfPlayer;
  private final ReadOnlyThreeTriosModelInterface model;

  /**
   * Create a HandPanel with a given model and player whose hand this is for.
   *
   * @param player       the player whose hand this is
   * @param viewOfPlayer the view of the player
   * @param model        the model for the game
   */
  public HandPanel(PlayerColor player, boolean viewOfPlayer,
                   ReadOnlyThreeTriosModelInterface model) {
    this.player = player;
    this.viewOfPlayer = viewOfPlayer;
    this.model = model;
    List<CustomCard> handCards = model.getPlayerHand(player);

    int handSize = handCards.size();
    this.setLayout(new GridLayout(handSize, 1));

    for (int i = 0; i < handSize; i++) {
      CustomCard card = handCards.get(i);
      JButton cardButton = createCard(card);
      int finalI = i;
      cardButton.addActionListener(e -> handleCardClick(finalI));
      this.add(cardButton);
    }
  }

  @Override
  public void handleCardClick(int handIndex) {
    if (!viewOfPlayer) {
      return;
    }

    if (handIndex < 0 || handIndex >= model.getPlayerHand(player).size()) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }
    JButton clicked;
    // Color c;
    try {
      clicked = (JButton) this.getComponent(handIndex);
    } catch (ClassCastException e) {
      throw new IllegalStateException("Hand panel should only contain JButtons.");
    }

    if (clicked == selection) {
      deselect();
      System.out.println("Hand index " + handIndex + " of player: " + player);
      return;
    } else if (selection != null) {
      deselect();
    }
    setSelection(clicked, handIndex);
    System.out.println("Hand index " + handIndex + " of player: " + player);
  }

  private void setSelection(JButton selection, int selectionIndex) {
    this.selection = selection;
    this.selectionIndex = selectionIndex;
    selection.setBorderPainted(true);
  }

  @Override
  public JButton getSelected() {
    return selection;
  }

  @Override
  public int getSelectedIndex() {
    return selectionIndex;
  }

  @Override
  public void deselect() {
    selection.setBorderPainted(false);
    selection = null;
    selectionIndex = -1;
  }
}
