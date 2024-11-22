package cs3500.threetrios.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Implementation of ThreeTriosGraphicalView.
 */
public class ThreeTriosGraphicalViewImplementation extends JFrame implements
        ThreeTriosGraphicalView {

  private final GridPanel gridPanel;
  private final HandPanel redHandPanel;
  private final HandPanel blueHandPanel;

  /**
   * Constructs a new ThreeTriosGraphicalView.
   *
   * @param model the model being displayed by the view
   */
  public ThreeTriosGraphicalViewImplementation(ReadonlyThreeTriosModel<?> model,
                                               PlayerColor playColor) {
    super("Player: " + playColor);

    // Set default close operation and layout
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Initialize panels
    gridPanel = new GridPanel(model);
    redHandPanel = new HandPanel(model, "RED");
    blueHandPanel = new HandPanel(model, "BLUE");

    // Add panels to the frame
    this.add(redHandPanel, BorderLayout.WEST);
    this.add(gridPanel, BorderLayout.CENTER);
    this.add(blueHandPanel, BorderLayout.EAST);

    // Set the actual size of the frame and center it
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setFeatures(Features features) {
    gridPanel.setFeatures(features);
    redHandPanel.setFeatures(features);
    blueHandPanel.setFeatures(features);
  }

  @Override
  public void resetSelection(PlayerColor player) {
    if (player.equals(PlayerColor.RED)) {
      redHandPanel.resetSelection();
    }
    else {
      blueHandPanel.resetSelection();
    }
  }

  @Override
  public void showMessage(String str) {
    JOptionPane.showMessageDialog(null, str);
  }
}
