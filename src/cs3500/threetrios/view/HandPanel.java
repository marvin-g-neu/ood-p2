package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.util.List;

/**
 * Implementation of a representation of a player hand using a JPanel.
 */
public class HandPanel implements HandPanelInterface {
  private final JPanel hand;
  private JButton selection;
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

    hand = new JPanel();
    int handSize = handCards.size();
    hand.setLayout(new GridLayout(handSize, 1));

    for (int i = 0; i < handSize; i++) {
      CustomCard card = handCards.get(i);
      JPanel cardPanel = new JPanel(new BorderLayout());
      cardPanel.setOpaque(false);

      JLabel northLabel = new JLabel(card.getAttackValue(Direction.NORTH).toString(),
          SwingConstants.CENTER);
      JLabel southLabel = new JLabel(card.getAttackValue(Direction.SOUTH).toString(),
          SwingConstants.CENTER);
      JLabel eastLabel = new JLabel(card.getAttackValue(Direction.EAST).toString());
      JLabel westLabel = new JLabel(card.getAttackValue(Direction.WEST).toString());

      cardPanel.add(northLabel, BorderLayout.NORTH);
      cardPanel.add(southLabel, BorderLayout.SOUTH);
      cardPanel.add(eastLabel, BorderLayout.EAST);
      cardPanel.add(westLabel, BorderLayout.WEST);
      setFonts(cardPanel, northLabel, southLabel, eastLabel, westLabel);
      cardPanel.addComponentListener(new ComponentAdapter() {
        // resize card text every time the window is resized
        @Override
        public void componentResized(ComponentEvent e) {
          setFonts(cardPanel, northLabel, southLabel, eastLabel, westLabel);
        }
      });

      JButton cardButton = new JButton();
      cardButton.setLayout(new BorderLayout());
      cardButton.add(cardPanel, BorderLayout.CENTER);

      if (player == PlayerColor.RED) {
        cardButton.setBackground(Color.RED);
      } else if (player == PlayerColor.BLUE) {
        cardButton.setBackground(Color.BLUE);
      }
      int finalI = i;
      cardButton.addActionListener(e -> handleCardClick(finalI));
      cardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      cardButton.setBorderPainted(false);
      hand.add(cardButton);
    }
  }

  private void setFonts(JPanel cardPanel, JLabel northLabel, JLabel southLabel,
                        JLabel eastLabel, JLabel westLabel) {
    int width = cardPanel.getWidth();
    int height = cardPanel.getHeight();
    int fontSize = Math.min(width, height) / 3;
    Font font = new Font("Arial", Font.PLAIN, fontSize);

    northLabel.setFont(font);
    southLabel.setFont(font);
    eastLabel.setFont(font);
    westLabel.setFont(font);
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
      clicked = (JButton) hand.getComponent(handIndex);
    } catch (ClassCastException e) {
      throw new IllegalStateException("Hand panel should only contain JButtons.");
    }

    if (clicked == selection) {
      clicked.setBorderPainted(false);
      selection = null;
      System.out.println("Hand index " + handIndex + " of player: " + player);
      return;
    } else if (selection != null) {
      selection.setBorderPainted(false);
    }
    selection = clicked;
    selection.setBorderPainted(true);
    System.out.println("Hand index " + handIndex + " of player: " + player);
  }

  @Override
  public JPanel getPanel() {
    return hand;
  }

  @Override
  public JButton getSelected() {
    return selection;
  }

  @Override
  public void deselect() {
    selection = null;
  }
}
