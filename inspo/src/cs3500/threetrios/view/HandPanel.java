package cs3500.threetrios.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;

class HandPanel extends JPanel {

  private final ReadonlyThreeTriosModel model;
  private final String playerColor;
  private int selectedCardIndex = -1;
  private Features features;

  public HandPanel(ReadonlyThreeTriosModel<?> model, String playerColor) {
    this.model = model;
    this.playerColor = playerColor;

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleHandClick(e);
      }
    });
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(150, getHeight());
  }

  public void setFeatures(Features features) {
    this.features = features;
  }

  private void handleHandClick(MouseEvent e) {
    List<ThreeTriosCard> hand = model.getHand(PlayerColor.valueOf(playerColor));
    int numCards = hand.size();

    int panelHeight = getHeight();
    int cardHeight = panelHeight / Math.max(numCards, 1);

    int mouseY = e.getY();
    int cardIndex = mouseY / cardHeight;

    if (cardIndex >= 0 && cardIndex < numCards) {
      if (selectedCardIndex == cardIndex) {
        // Deselect the card if it's already selected
        resetSelection();
      } else {
        // Select the new card
        selectedCardIndex = cardIndex;
      }

      if (features != null) {
        boolean successfulSelect = features.selectCard(playerColor, selectedCardIndex);
        if (!successfulSelect) {
          resetSelection(); // card should not be highlighted if selection fails
        }
      } else {
        System.out.println("Clicked on card index " + cardIndex + " of player " + playerColor);
      }
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    List<ThreeTriosCard> hand = model.getHand(PlayerColor.valueOf(playerColor));
    int numCards = hand.size();
    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int cardWidth = panelWidth;
    int cardHeight = panelHeight / Math.max(numCards, 1);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Color backgroundColor = playerColor.equals("RED") ? new Color(255, 182, 193) : new Color(15,
        81, 208);

    for (int i = 0; i < numCards; i++) {
      int cardX = 0;
      int cardY = i * cardHeight;
      // Draw card background
      g2d.setColor(backgroundColor);
      g2d.fillRect(cardX + 5, cardY + 5, cardWidth - 10, cardHeight - 10);
      // Draw border around the card
      if (i == selectedCardIndex) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
      } else {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
      }
      g2d.drawRect(cardX + 5, cardY + 5, cardWidth - 10, cardHeight - 10);

      Card card = hand.get(i);
      g2d.setColor(Color.BLACK);
      g2d.setFont(new Font("Arial", Font.BOLD, 24));

      String northValue = (card.getNorth() == 10) ? "A" : String.valueOf(card.getNorth());
      String westValue = (card.getWest() == 10) ? "A" : String.valueOf(card.getWest());
      String eastValue = (card.getEast() == 10) ? "A" : String.valueOf(card.getEast());
      String southValue = (card.getSouth() == 10) ? "A" : String.valueOf(card.getSouth());
      // Values in the order North, West, East, South
      g2d.drawString(northValue, cardX + cardWidth
          / 2 - g2d.getFontMetrics().stringWidth(northValue) / 2, cardY + 30);
      g2d.drawString(westValue, cardX + 20, cardY + cardHeight
          / 2 + g2d.getFontMetrics().getHeight() / 4);
      g2d.drawString(eastValue, cardX + cardWidth - g2d.getFontMetrics().stringWidth(eastValue)
          - 20, cardY + cardHeight / 2 + g2d.getFontMetrics().getHeight() / 4);
      g2d.drawString(southValue, cardX + cardWidth / 2
          - g2d.getFontMetrics().stringWidth(southValue) / 2, cardY + cardHeight - 20);
    }
  }

  public void resetSelection() {
    selectedCardIndex = -1;
  }
}