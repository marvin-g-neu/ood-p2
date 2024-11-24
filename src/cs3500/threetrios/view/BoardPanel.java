package cs3500.threetrios.view;

import cs3500.threetrios.controller.GameListeners;
import cs3500.threetrios.model.ReadOnlyThreeTriosModelInterface;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.grid.Grid;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Implementation of a representation of the game board using a JPanel.
 */
public class BoardPanel extends CardPanel implements BoardPanelInterface {
  private final JPanel grid;
  private final ReadOnlyThreeTriosModelInterface model;
  private GameListeners controller;

  private final HandPanelInterface hand;

  private final JButton[][] gridRep;

  /**
   * Create a BoardPanel with a given model and hand of the player whose view this is part of.
   *
   * @param model      the model being used for the game
   * @param hand       the panel representation of the hand of the player whose panel this is
   * @param controller the controller for the player whose panel this is
   */
  public BoardPanel(ReadOnlyThreeTriosModelInterface model,
                    HandPanelInterface hand, GameListeners controller) {
    this.model = model;
    this.hand = hand;
    this.controller = controller;
    grid = new JPanel();

    Grid board = model.getGrid();
    grid.setLayout(new GridLayout(board.getRows(), board.getCols()));
    grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    gridRep = new JButton[board.getRows()][board.getCols()];
    for (int row = 0; row < board.getRows(); row++) {
      for (int col = 0; col < board.getCols(); col++) {
        JButton cellButton = new JButton();
        Cell cell = board.getCell(row, col);
        if (cell.isHole()) {
          cellButton.setBackground(Color.LIGHT_GRAY);
        } else if (cell.isEmpty()) {
          cellButton.setBackground(Color.YELLOW);
        } else {
          System.out.println("Yes");
          cellButton = createCard(cell.getCard());
        }
        int finalRow = row;
        int finalCol = col;
        cellButton.addActionListener(e -> handleCellClick(finalRow, finalCol));
        grid.add(cellButton);
        gridRep[finalRow][finalCol] = cellButton;
      }
    }
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
    JButton selected = hand.getSelected();
    JButton cellButton = gridRep[row][col];
    if (cellButton.getBackground() == Color.YELLOW && selected != null) {
      controller.playMove(row, col, hand.getSelectedIndex());
      hand.deselect();
    }
  }

  @Override
  public JPanel getPanel() {
    return grid;
  }

  @Override
  public void setController(GameListeners controller) {
    this.controller = controller;
  }
}
