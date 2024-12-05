package cs3500.threetrios.view.adapted;

import cs3500.threetrios.controller.GameListeners;
import cs3500.threetrios.provider.view.gui.TtGuiView;
import cs3500.threetrios.view.ThreeTriosGUIViewInterface;

import java.io.IOException;

/**
 * An adaptor to utilize a TTGuiView for someone using a ThreeTriosGUIViewInterface.
 */
public class ThreeTriosGUIViewAdaptor implements ThreeTriosGUIViewInterface {
  private TtGuiView providerView;

  public ThreeTriosGUIViewAdaptor(TtGuiView view) {
    providerView = view;
  }

  @Override
  public void setVisible(boolean visible) {
    providerView.refreshBoard();
    providerView.refreshPanels();
    providerView.makeVisible();
  }

  @Override
  public void displayMessage(String message) {
    // Not set individually
  }

  @Override
  public void setController(GameListeners controller) {
    // No method to set outside of construction
  }

  @Override
  public void render() throws IOException {
    providerView.refreshPanels();
    providerView.refreshBoard();
  }
}
