package cs3500.threetrios.controller;

import java.io.IOException;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.view.ThreeTriosGraphicalView;

/**
 * Mock of ThreeTriosGraphicalView that records actions.
 */
public class MockViewRecordActions implements ThreeTriosGraphicalView {

  Appendable log;

  /**
   * Creates a new mock.
   *
   * @param log Appendable object
   */
  public MockViewRecordActions(Appendable log) {
    this.log = log;
  }

  @Override
  public void makeVisible() {
    try {
      log.append("Made the view visible\n");
    } catch (IOException e) {
      // append nothing
    }
  }

  @Override
  public void refresh() {
    try {
      log.append("Refreshed the view\n");
    } catch (IOException e) {
      // append nothing
    }
  }

  @Override
  public void setFeatures(Features features) {
    // Stub
  }

  @Override
  public void resetSelection(PlayerColor player) {
    try {
      log.append("Reset selected card\n");
    } catch (IOException e) {
      // append nothing
    }
  }

  @Override
  public void showMessage(String str) {
    try {
      log.append("Displaying message: " + str + "\n");
    } catch (IOException e) {
      // append nothing
    }
  }
}
