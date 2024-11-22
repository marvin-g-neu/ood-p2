package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;
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


public class HandPanel implements HandPanelInterface {
  private JPanel hand;
  private JButton selection;
  private PlayerColor handOfPlayer;
  private boolean viewOfPlayer;
  private List<CustomCard> handCards;

  public HandPanel(PlayerColor handOfPlayer, boolean viewOfPlayer, List<CustomCard> handCards) {
    this.handOfPlayer = handOfPlayer;
    this.viewOfPlayer = viewOfPlayer;
    this.handCards = handCards;


    JPanel handPanel = new JPanel();
    int handSize = handCards.size();
    handPanel.setLayout(new GridLayout(handSize, 1));

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

      if (handOfPlayer == PlayerColor.RED) {
        cardButton.setBackground(Color.RED);
      } else if (handOfPlayer == PlayerColor.BLUE) {
        cardButton.setBackground(Color.BLUE);
      }
      int finalI = i;
      cardButton.addActionListener(e -> handleCardClick(finalI));
      cardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      cardButton.setBorderPainted(false);
      handPanel.add(cardButton);
    }
  }

  private void setFonts(JPanel cardPanel, JLabel northLabel, JLabel southLabel, JLabel eastLabel, JLabel westLabel) {
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
  }
}
