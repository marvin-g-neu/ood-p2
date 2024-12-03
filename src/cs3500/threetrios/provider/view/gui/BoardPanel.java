package cs3500.threetrios.provider.view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


import javax.swing.JPanel;

import cs3500.threetrios.provider.controller.gui.PlayerController;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrioModel;
import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.cells.Directions;

/**
 * Instance of a Board Panel, used to depict a board of the ThreeTrioGame onto a JFrame.
 */
public class BoardPanel extends JPanel {
  private final ReadOnlyThreeTrioModel model;
  private PlayerController controller;
  private final TtGuiView view;

  /**
   * Constructor for a Board Panel, takes in a model to be saved for dimension checking.
   *
   * @param model The ReadOnlyThreeTrioModel being used for the ThreeTrioGame.
   * @param view  the main view.
   * @throws IllegalArgumentException model or view are null
   */
  public BoardPanel(ReadOnlyThreeTrioModel model, TtGuiView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model or view are null");
    }
    this.model = model;
    this.view = view;
  }

  /**
   * Adds a listener to the controller to take in inputs from the user.
   *
   * @param given The TTGUIController being used for the instance of the ThreeTrioGame.
   * @throws IllegalArgumentException if give is null
   */
  public void addClickListener(PlayerController given) {
    if (given == null) {
      throw new IllegalArgumentException("given is null");
    }
    this.controller = given;
    this.addMouseListener(new TtClickListener());
  }

  /**
   * Gets the logical dimensions of the board using the board stored in the model.
   *
   * @return The new Dimensions created from the board found in the model.
   */
  private Dimension getLogicalSize() {
    return new Dimension(model.getBoard()[0].length * 100, model.getBoard().length * 100);
  }

  /**
   * Paints the board panel onto the JFrame.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(getLogicalToPhysical());
    for (int boardSetY = 0; boardSetY < model.getBoard().length; boardSetY++) {
      for (int boardSetX = 0; boardSetX < model.getBoard()[0].length; boardSetX++) {
        renderCell(g2d,boardSetX,boardSetY);
      }
    }
  }

  /**
   * Paints a single cell onto the board.
   * @param g2d the g2d to use.
   * @param x the x position
   * @param y the y position
   */
  private void renderCell(Graphics2D g2d,int x,int y) {
    Cell cell = model.getBoard()[y][x];
    drawRect(g2d, x, y, cell);
    if (cell != null && !cell.isaHole()) {
      renderTextOnCell(g2d, cell, x, y);
    }
  }

  /**
   * Draws a Rectangle using the top left corner as a starting point and shifting the width and
   * height of a depicted cell.
   *
   * @param g2d  The Graphics2D to which the transformations are played.
   * @param x    The x-value of the top left corner of the requested rectangle.
   * @param y    The y-value of the top left corner of the requested rectangle.
   * @param cell The cell that the g2d is currently drawing
   */
  private void drawRect(Graphics2D g2d, int x, int y,
                        Cell cell) {
    Point2D start = getModelToLogical().transform(new Point(x, y), null);
    Point2D size = getModelToLogical().transform(new Point(1, 1), null);
    Shape rect = new Rectangle2D.Double(start.getX(), start.getY(), size.getX(), size.getY());
    g2d.setColor(ViewConstants.colorToDisplay(cell));
    g2d.fill(rect);
    g2d.setColor(ViewConstants.getBoardLineColor());
    g2d.draw(rect);
  }

  /**
   * Writes the directional values onto a cell.
   *
   * @param g2d  The Graphics2D to which transformations will be made.
   * @param cell The Cell to be used as reference.
   * @param x    The x of the cell.
   * @param y    The y of the cell.
   */
  private void renderTextOnCell(Graphics2D g2d, Cell cell, int x, int y) {
    Dimension2D cellSize =
            new Dimension((int) getLogicalSize().getWidth() / model.getBoard()[0].length,
                    (int) (getLogicalSize().getHeight() / model.getBoard().length));
    g2d.setFont(ViewConstants.getFont());
    g2d.setColor(ViewConstants.getShowcaseColor(cell.getColor()));
    for (Directions dir : Directions.values()) {
      drawString(g2d, cell, dir, x, y, cellSize);
    }
  }

  /**
   * Draws the requested direction value of the cell onto itself.
   *
   * @param g2d      The Graphics2D to which transformations will be made.
   * @param cell     The cell to use for referencing values to set at the requested direction.
   * @param dir      The direction to which writing is requested to.
   * @param cellX    The x-position of the cell.
   * @param cellY    The y-position of the cell.
   * @param cellSize The size of the cell.
   */
  private void drawString(Graphics2D g2d, Cell cell, Directions dir,
                          int cellX, int cellY, Dimension2D cellSize) {
    Point2D textPos = ViewConstants.directionToPositionOfText(dir);
    double x = cellX + textPos.getX();
    double y = cellY + textPos.getY();
    g2d.drawString(cell.toDisplayName(dir),
            (int) (x * cellSize.getHeight()), (int) (y * cellSize.getHeight()));
  }

  /**
   * Turns the board given from the model into usable positioning in the logical dimension.
   *
   * @return the AffineTransform with the dimensions made from the board given by the model.
   */
  private AffineTransform getModelToLogical() {
    AffineTransform xform = new AffineTransform();
    Dimension logicalDims = getLogicalSize();
    xform.scale(logicalDims.getWidth() / model.getBoard()[0].length,
            logicalDims.getHeight() / model.getBoard().length);
    return xform;
  }

  /**
   * Turns the board given from the logical dimension to one in the physical dimension.
   *
   * @return the AffineTransform with the dimensions from the board given by the logical dimension.
   */
  private AffineTransform getLogicalToPhysical() {
    AffineTransform xform = new AffineTransform();
    Dimension logicalDims = getLogicalSize();
    xform.scale(this.getWidth() / logicalDims.getWidth(),
            this.getHeight() / logicalDims.getHeight());
    return xform;
  }

  /**
   * ClickListener for the ThreeTrioGame board to take in GUI inputs.
   */
  private class TtClickListener implements MouseListener {

    /**
     * Determines the point at which the user clicked and transforms it into a point usable for the
     * ThreeTrioGame components.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      try {
        AffineTransform physicalToLogical = getLogicalToPhysical();
        physicalToLogical.invert();

        AffineTransform logicalToModel = getModelToLogical();
        logicalToModel.invert();
        Point2D evtPt = e.getPoint();

        Point2D logicalPt = physicalToLogical.transform(evtPt, null);
        Point2D modelPt = logicalToModel.transform(logicalPt, null);

        int x = (int) modelPt.getX();
        int y = (int) modelPt.getY();

        System.err.println(x + "," + y);
        try {
          controller.playCell(x, y);
        } catch (IllegalStateException | IllegalArgumentException thrownException) {
          view.throwError(thrownException.getMessage());
        }
      } catch (NoninvertibleTransformException ex) {
        throw new RuntimeException(ex);
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {
      // not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // not used
    }
  }
}
