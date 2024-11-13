package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModelInterface;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;

import javax.swing.*;
import java.awt.*;

/**
 * A view for the game ThreeTrios using a Java Swing GUI view.
 */
public class ThreeTriosGUIView implements ThreeTriosView {
  private ReadOnlyThreeTriosModelInterface model;
  private ThreeTriosController controller;

  private JFrame frame;
  private JPanel redPanel;
  private JPanel bluePanel;
  private JPanel gridPanel;

  private int handSize;

  private JButton selection;

  public static void main(String[] args) {

  }

  /**
   * Creates a GUI view for a given model of Three Trios.
   *
   * @param model the model to be accessed by the view
   * @throws IllegalArgumentException if model is null
   */
  public ThreeTriosGUIView(ReadOnlyThreeTriosModelInterface model) {
    this.model = model;
    this.frame = new JFrame();
    this.redPanel = new JPanel();
    this.bluePanel = new JPanel();
    this.gridPanel = new JPanel();

    frame.add(gridPanel, BorderLayout.CENTER);
    frame.add(redPanel, BorderLayout.WEST);
    frame.add(bluePanel, BorderLayout.EAST);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Three Trios");
    frame.pack();
    frame.setVisible(true);

    selection = null;
  }

  @Override
  public void render() {

  }

  private JPanel createHandPanel(PlayerColor player) {
    JPanel handPanel = new JPanel();
    handPanel.setLayout(new GridLayout(handSize, 1));

    for (int i = 0; i < handSize; i++) {
      JButton cardButton = new JButton(player + " " + (i + 1));
      if (player == PlayerColor.RED) {
        cardButton.setBackground(Color.RED);
      } else if (player == PlayerColor.BLUE) {
        cardButton.setBackground(Color.BLUE);
      }
      int finalI = i;
      cardButton.addActionListener(e -> controller.handleCardClick(player, finalI, cardButton));
      handPanel.add(cardButton);
    }

    return handPanel;
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
        cellButton.addActionListener(e -> controller.handleCellClick(finalRow, finalCol));
        grid.add(cellButton);
      }
    }

    return grid;
  }
}
