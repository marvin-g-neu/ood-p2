package cs3500.threetrios.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

class GridPanel extends JPanel {

  private final ReadonlyThreeTriosModel<?> model;
  private Features features;

  public GridPanel(ReadonlyThreeTriosModel<?> model) {
    this.model = model;

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleGridClick(e);
      }
    });
  }

  public void setFeatures(Features features) {
    this.features = features;
  }

  private void handleGridClick(MouseEvent e) {
    int gridWidth = model.getGridWidth();
    int gridHeight = model.getGridHeight();

    int panelWidth = getWidth();
    int panelHeight = getHeight();

    int cellWidth = panelWidth / gridWidth;
    int cellHeight = panelHeight / gridHeight;

    int mouseX = e.getX();
    int mouseY = e.getY();

    int gridX = mouseX / cellWidth;
    int gridY = mouseY / cellHeight;

    if (gridX >= 0 && gridX < gridWidth && gridY >= 0 && gridY < gridHeight) {
      CellType cellType = model.getCellTypeAt(gridY, gridX);
      if (cellType != CellType.HOLE) {
        // Delegate to features
        if (features != null) {
          features.clickCell(gridY, gridX);
        } else {
          System.out.println("Clicked on cell at (" + gridY + ", " + gridX + ")");
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int gridWidth = model.getGridWidth();
    int gridHeight = model.getGridHeight();
    int cellWidth = getWidth() / gridWidth;
    int cellHeight = getHeight() / gridHeight;

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    for (int y = 0; y < gridHeight; y++) {
      for (int x = 0; x < gridWidth; x++) {
        int cellX = x * cellWidth;
        int cellY = y * cellHeight;
        CellType cellType = model.getCellTypeAt(y, x);

        if (cellType == CellType.CARDCELL) {
          drawCardCell(g2d, x, y, cellX, cellY, cellWidth, cellHeight);
        } else if (cellType == CellType.HOLE) {
          drawHole(g2d, cellX, cellY, cellWidth, cellHeight);
        }

        // Draw cell border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(cellX, cellY, cellWidth, cellHeight);
      }
    }
  }

  private void drawCardCell(
      Graphics2D g2d,
      int x,
      int y,
      int cellX,
      int cellY,
      int cellWidth,
      int cellHeight) {
    g2d.setColor(new Color(255, 215, 0));
    g2d.fillRect(cellX, cellY, cellWidth, cellHeight);

    try {
      Card card = model.getCardAt(y, x);
      if (card != null) {
        drawCardBackground(g2d, x, y, cellX, cellY, cellWidth, cellHeight);
        drawCardValues(g2d, card, cellX, cellY, cellWidth, cellHeight);
      }
    } catch (IllegalArgumentException ex) {
      // No card at this cell
    }
  }

  private void drawHole(
      Graphics2D g2d,
      int cellX,
      int cellY,
      int cellWidth,
      int cellHeight) {
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(cellX, cellY, cellWidth, cellHeight);
  }

  private void drawCardBackground(
      Graphics2D g2d,
      int x,
      int y,
      int cellX,
      int cellY,
      int cellWidth,
      int cellHeight) {
    PlayerColor owner = model.getCellPlayerAt(y, x);
    g2d.setColor(owner == PlayerColor.RED ? new Color(255, 182, 193) : new Color(15, 81, 208));
    g2d.fillRect(cellX + 5, cellY + 5, cellWidth - 10, cellHeight - 10);
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(1));
    g2d.drawRect(cellX + 5, cellY + 5, cellWidth - 10, cellHeight - 10);
  }

  private void drawCardValues(
      Graphics2D g2d,
      Card card,
      int cellX,
      int cellY,
      int cellWidth,
      int cellHeight) {
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 28));

    String northValue = (card.getNorth() == 10) ? "A" : String.valueOf(card.getNorth());
    String westValue = (card.getWest() == 10) ? "A" : String.valueOf(card.getWest());
    String eastValue = (card.getEast() == 10) ? "A" : String.valueOf(card.getEast());
    String southValue = (card.getSouth() == 10) ? "A" : String.valueOf(card.getSouth());
    
    // Values in the order North, West, East, South
    g2d.drawString(
        northValue,
        cellX + cellWidth / 2 - g2d.getFontMetrics().stringWidth(northValue) / 2,
        cellY + 35);
    g2d.drawString(
        westValue,
        cellX + 10,
        cellY + cellHeight / 2 + g2d.getFontMetrics().getHeight() / 4);
    g2d.drawString(
        eastValue,
        cellX + cellWidth - g2d.getFontMetrics().stringWidth(eastValue) - 10,
        cellY + cellHeight / 2 + g2d.getFontMetrics().getHeight() / 4);
    g2d.drawString(
        southValue,
        cellX + cellWidth / 2 - g2d.getFontMetrics().stringWidth(southValue) / 2,
        cellY + cellHeight - 10);
  }
}
