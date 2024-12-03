package cs3500.threetrios.provider.view.gui;

import java.awt.BasicStroke;
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
import java.util.List;


import javax.swing.JPanel;

import cs3500.threetrios.provider.controller.gui.PlayerController;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrioModel;
import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.cells.Directions;

/**
 * Instance of a Board Panel, used to depict a board of the ThreeTrioGame onto a JFrame.
 */
public class HandPanel extends JPanel {

  private PlayerController controller;
  private ThreeTrioColor playerColor;
  private final ReadOnlyThreeTrioModel model;

  private final TtGuiView view;

  /**
   * Constructor for a Hand Panel, takes in a model to be saved for dimension checking.
   *
   * @param model The ReadOnlyThreeTrioModel being used for the ThreeTrioGame.
   * @param view  the main view.
   * @throws IllegalArgumentException model or view are null.
   */
  public HandPanel(ReadOnlyThreeTrioModel model, TtGuiView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model or view are null");
    }
    this.model = model;
    this.view = view;
  }

  /**
   * Sets the Color for the playerColor to which this cell belongs to.
   *
   * @param color The Color of the player.
   * @throws IllegalArgumentException if color is null
   */
  public void changeDisplayColor(ThreeTrioColor color) {
    if (color == null) {
      throw new IllegalArgumentException("color is null");
    }
    playerColor = color;
  }

  /**
   * Adds a listener to the controller to take in inputs from the user.
   *
   * @param given The TTGUIController being used for the instance of the ThreeTrioGame.
   * @throws IllegalArgumentException if given is null
   */
  public void addClickListener(PlayerController given) {
    if (given == null) {
      throw new IllegalArgumentException("given is null");
    }
    this.controller = given;
    this.addMouseListener(new TtClickListener());
  }

  /**
   * The size of the logical transform is 10, 10 * the number of cells in the hand.
   *
   * @return the dimensions of the cells.
   */
  private Dimension getLogicalSize() {
    return new Dimension(100, model.getHand(playerColor).size() * 100);
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
    List<Cell> hand = model.getHand(playerColor);
    g2d.transform(getLogicalToPhysical());
    for (int idx = 0; idx < hand.size(); idx++) {
      paintCell(g2d, hand.get(idx), idx);
    }

    //draws a box around the selected cell in the hand if it is this player's turn.
    if (controller.getIsTurn() && controller.getColor() == playerColor
            && controller.getSelectedIdx() != -1) {
      highlightSquare(g2d, controller.getSelectedIdx());
    }
  }

  /**
   * Highlights a Rectangle using a black outline. Used to depict selection.
   *
   * @param g2d The Graphics2D to which transformations will be made.
   * @param idx The index in the hand from the model where the cell is located at.
   */
  private void highlightSquare(Graphics2D g2d, int idx) {
    g2d.setStroke(new BasicStroke(10));
    Point2D start = modelToLogical(new Point(0, idx));
    Point2D size = modelToLogical(new Point(1, 1));
    Shape rect = new Rectangle2D.Double(start.getX(), start.getY(), size.getX(), size.getY());
    g2d.setColor(ViewConstants.getShowcaseColor(playerColor));
    g2d.draw(rect);
    g2d.setStroke(new BasicStroke(1));
  }

  /**
   * Paints the Cell in the hand of the player using their color and cell values.
   *
   * @param g2d  The Graphics2D to which the transformations will be made.
   * @param cell The cell to be painted onto the GUI.
   * @param y    The y position of the cell.
   */
  private void paintCell(Graphics2D g2d, Cell cell, int y) {
    drawRect(g2d, y, cell);
    //gets the size of a cell in logical dimensions
    Dimension2D cellSize = new Dimension((int) getLogicalSize().getWidth(),
            (int) (getLogicalSize().getHeight() / model.getHand(playerColor).size()));

    //iterates through all the directions in the cell and prints it on the board
    g2d.setFont(ViewConstants.getFont());
    g2d.setColor(ViewConstants.getShowcaseColor(playerColor));
    for (Directions dir : Directions.values()) {
      drawString(g2d, cell, dir, 0, y, cellSize);
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
   * Draws a rectangle onto the given Graphics2D using the y-value as the starting point.
   * Changes the color of the background and the lines based on the cell color.
   *
   * @param g2d  The Graphics2D to which transformations will be made.
   * @param y    The y-value to be used as the starting position from where to draw the rectangle.
   * @param cell the cell.
   */
  private void drawRect(Graphics2D g2d, int y, Cell cell) {
    Point2D start = modelToLogical(new Point(0, y));
    Point2D size = modelToLogical(new Point(1, 1));
    Shape rect = new Rectangle2D.Double(start.getX(), start.getY(), size.getX(), size.getY());
    g2d.setColor(ViewConstants.colorToDisplay(cell));
    g2d.fill(rect);
    g2d.setColor(ViewConstants.getShowcaseColor(cell.getColor()));
    g2d.draw(rect);
  }

  /**
   * Converts a Point from the model into the logical dimension.
   *
   * @param point The point to be used for the transformation.
   * @return The transformed Point2D.
   */
  private Point2D modelToLogical(Point2D point) {
    return getModelToLogical().transform(point, null);
  }

  /**
   * Turns the hand given from the model into usable positioning in the logical dimension.
   *
   * @return the AffineTransform with the dimensions made from the player hand given by the model.
   */
  private AffineTransform getModelToLogical() {
    AffineTransform xform = new AffineTransform();
    Dimension logicalDims = getLogicalSize();
    xform.scale(logicalDims.getWidth(),
            logicalDims.getHeight() / model.getHand(playerColor).size());
    return xform;
  }

  /**
   * Turns the hand given from the logical dimension to the physical dimension.
   *
   * @return the AffineTransform with the logical coordinates.
   */
  private AffineTransform getLogicalToPhysical() {
    AffineTransform xform = new AffineTransform();
    Dimension logicalDims = getLogicalSize();
    xform.scale(this.getWidth() / logicalDims.getWidth(),
            this.getHeight() / logicalDims.getHeight());
    return xform;
  }

  /**
   * Click Listeners on the HandPanel from which the ThreeTrioGame takes inputs.
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
      //System.err.println(e.getX() + "," + e.getY());

      try {
        AffineTransform physicalToLogical = getLogicalToPhysical();
        physicalToLogical.invert();

        AffineTransform logicalToModel = getModelToLogical();
        logicalToModel.invert();

        Point2D evtPt = e.getPoint();
        Point2D logicalPt = physicalToLogical.transform(evtPt, null);
        Point2D modelPt = logicalToModel.transform(logicalPt, null);

        int handIdx = (int) modelPt.getY();
        System.err.println(handIdx);
        try {
          controller.selectCell(playerColor, handIdx);
        } catch (IllegalArgumentException | IllegalStateException exc) {
          view.throwError(exc.getMessage());
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
