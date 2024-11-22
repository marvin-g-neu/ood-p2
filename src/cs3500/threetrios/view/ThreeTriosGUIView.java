package cs3500.threetrios.view;

import cs3500.threetrios.model.*;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.PlayerColor;

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
  private HandPanelInterface redHand;
  private HandPanelInterface blueHand;
  private JPanel gridPanel;

  private JButton selection;

  private final PlayerColor player;

  /**
   * Creates a GUI view for a given model of Three Trios.
   *
   * @param model the model to be accessed by the view
   * @throws IllegalArgumentException if model is null
   * @throws IllegalArgumentException if model game has not been started
   */
  public ThreeTriosGUIView(ReadOnlyThreeTriosModelInterface model, PlayerColor player) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (model.getGameState() == GameState.NOT_STARTED) {
      throw new IllegalArgumentException("Game state is not started");
    }
    this.model = model;
    this.player = player;
    this.frame = new JFrame();
    frame.setLayout(new GridBagLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Three Trios");

    this.gridPanel = createGridPanel();
    this.selection = null;

    // GridBag setup
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;

    gbc.gridx = 0;
    gbc.weightx = 0.15;
    frame.add(redHand.getPanel(), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.7;
    frame.add(gridPanel, gbc);

    gbc.gridx = 2;
    gbc.weightx = 0.15;
    frame.add(blueHand.getPanel(), gbc);

    frame.setPreferredSize(new Dimension(800, 600));
    frame.pack();
  }

  @Override
  public void render() {
    redHand = createHandPanel(PlayerColor.RED);
    blueHand = createHandPanel(PlayerColor.BLUE);
    gridPanel = createGridPanel();

    frame.revalidate();
    frame.repaint();
  }

  private HandPanelInterface createHandPanel(PlayerColor handOfPlayer) {
    return new HandPanel(handOfPlayer, handOfPlayer == player, model);
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
