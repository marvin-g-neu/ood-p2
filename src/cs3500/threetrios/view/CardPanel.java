package cs3500.threetrios.view;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Allows Hand and Board panels both to create cards when being painted and act as JPanels.
 */
public abstract class CardPanel extends JPanel {
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

  protected JButton createCard(CustomCard card) {
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

    if (card.getCurrentColor() == CardColor.RED) {
      cardButton.setBackground(Color.RED);
    } else if (card.getCurrentColor() == CardColor.BLUE) {
      cardButton.setBackground(Color.BLUE);
    }
    cardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    cardButton.setBorderPainted(false);

    return cardButton;
  }
}
