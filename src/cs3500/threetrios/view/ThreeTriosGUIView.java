package cs3500.threetrios.view;

import cs3500.threetrios.model.*;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A view for the game ThreeTrios using a Java Swing GUI view.
 */
public class ThreeTriosGUIView implements ThreeTriosGUIViewInterface {
  private final ReadOnlyThreeTriosModelInterface model;

  private final JFrame frame;
  private JPanel redPanel;
  private JPanel bluePanel;
  private JPanel gridPanel;

  private JButton selection;

  /**
   * Creates a GUI view for a given model of Three Trios.
   *
   * @param model the model to be accessed by the view
   * @throws IllegalArgumentException if model is null
   * @throws IllegalArgumentException if model game has not been started
   */
  public ThreeTriosGUIView(ReadOnlyThreeTriosModelInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (model.getGameState() == GameState.NOT_STARTED) {
      throw new IllegalArgumentException("Game state is not started");
    }
    this.model = model;
    this.frame = new JFrame();
    frame.setLayout(new GridBagLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Three Trios");

    this.redPanel = createHandPanel(PlayerColor.RED);
    this.bluePanel = createHandPanel(PlayerColor.BLUE);
    this.gridPanel = createGridPanel();
    this.selection = null;

    // GridBag setup
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;

    gbc.gridx = 0;
    gbc.weightx = 0.15;
    frame.add(redPanel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.7;
    frame.add(gridPanel, gbc);

    gbc.gridx = 2;
    gbc.weightx = 0.15;
    frame.add(bluePanel, gbc);

    frame.setPreferredSize(new Dimension(800, 600));
    frame.pack();
  }

  @Override
  public void render() {
    redPanel = createHandPanel(PlayerColor.RED);
    bluePanel = createHandPanel(PlayerColor.BLUE);
    gridPanel = createGridPanel();

    frame.revalidate();
    frame.repaint();
  }

  private JPanel createHandPanel(PlayerColor player) {
    JPanel handPanel = new JPanel();
    int handSize = model.getCurrentPlayerHand().size();
    handPanel.setLayout(new GridLayout(handSize, 1));

    for (int i = 0; i < handSize; i++) {
      CustomCard card = model.getCurrentPlayerHand().get(i);
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
      cardButton.addActionListener(e -> handleCardClick(player, finalI));
      cardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      cardButton.setBorderPainted(false);
      handPanel.add(cardButton);
    }

    return handPanel;
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

  private JPanel createGridPanel() {
    Grid board = model.getGrid();
    JPanel grid = new JPanel();
    grid.setLayout(new GridLayout(board.getRows(), board.getCols()));
    grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    for (int row = 0; row < board.getRows(); row++) {
      for (int col = 0; col < board.getCols(); col++) {
        JButton cellButton = new JButton();
        if (board.getCell(row, col).isHole()) {
          cellButton.setBackground(Color.LIGHT_GRAY);
        } else {
          cellButton.setBackground(Color.YELLOW);
        }
        int finalRow = row;
        int finalCol = col;
        cellButton.addActionListener(e -> handleCellClick(finalRow, finalCol));
        grid.add(cellButton);
      }
    }

    return grid;
  }

  @Override
  public void handleCardClick(PlayerColor player, int handIndex) {
    if (player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    if (handIndex < 0 || handIndex >= model.getPlayerHand(player).size()) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }
    JButton clicked;
    // Color c;
    try {
      if (player == PlayerColor.RED) {
        clicked = (JButton) redPanel.getComponent(handIndex);
        // c = Color.RED;
      } else {
        clicked = (JButton) bluePanel.getComponent(handIndex);
        // c = Color.BLUE;
      }
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
  public void handleCellClick(int row, int col) {
    if (row < 0 || row >= model.getGrid().getRows()) {
      throw new IllegalArgumentException("Row index out of bounds");
    }
    if (col < 0 || col >= model.getGrid().getCols()) {
      throw new IllegalArgumentException("Column index out of bounds");
    }
    System.out.println("(" + row + ", " + col + ")");
  }

  public void makeVisible() {
    frame.setVisible(true);
  }

  public void displayMessage(String str) {
    JOptionPane.showMessageDialog(null, str);
  }
}
