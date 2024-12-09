package cs3500.threetrios.view;

import cs3500.threetrios.controller.GameListeners;
import cs3500.threetrios.model.GameState;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModelInterface;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A view for the game ThreeTrios using a Java Swing GUI view.
 */
public class ThreeTriosGUIView implements ThreeTriosGUIViewInterface {
  private final ReadOnlyThreeTriosModelInterface model;

  private final JFrame frame;
  private final GridBagConstraints gbc;

  private HandPanelInterface redHand;
  private HandPanelInterface blueHand;
  private BoardPanelInterface boardPanel;
  private GameListeners controller;

  private final PlayerColor player;

  /**
   * Creates a GUI view for a given model of Three Trios for the given player.
   *
   * @param model  the model to be accessed by the view
   * @param player the owner of this view
   * @throws IllegalArgumentException if model is null
   * @throws IllegalArgumentException if model game has not been started
   */
  public ThreeTriosGUIView(ReadOnlyThreeTriosModelInterface model, PlayerColor player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    if (model.getGameState() == GameState.NOT_STARTED) {
      throw new IllegalArgumentException("Game state is not started");
    }
    this.model = model;
    this.player = player;

    frame = new JFrame();
    frame.setLayout(new GridBagLayout());
    frame.setTitle(player.toString());
    // GridBag setup
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;

    frame.setPreferredSize(new Dimension(800, 600));
    frame.pack();

    render();
  }

  @Override
  public void render() {
    redHand = createHandPanel(PlayerColor.RED);
    blueHand = createHandPanel(PlayerColor.BLUE);
    boardPanel = createGridPanel();

    Container cont = frame.getContentPane();

    cont.removeAll();
    cont.revalidate();

    gbc.gridx = 0;
    gbc.weightx = 0.15;
    cont.add((JPanel) redHand, gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.7;
    cont.add((JPanel) boardPanel, gbc);

    gbc.gridx = 2;
    gbc.weightx = 0.15;
    cont.add((JPanel) blueHand, gbc);
  }

  private HandPanelInterface createHandPanel(PlayerColor handOfPlayer) {
    return new HandPanel(handOfPlayer, handOfPlayer == player, model);
  }

  private BoardPanelInterface createGridPanel() {
    HandPanelInterface handOfPlayer;
    if (player == PlayerColor.RED) {
      handOfPlayer = redHand;
    } else {
      handOfPlayer = blueHand;
    }
    return new BoardPanel(model, handOfPlayer, controller);
  }

  @Override
  public void setVisible(boolean visible) {
    frame.setVisible(visible);
    System.out.println(visible + " " + player);
    System.out.println(frame.isVisible());
  }

  @Override
  public void displayMessage(String str) {
    JOptionPane.showMessageDialog(null, str);
  }

  @Override
  public void setController(GameListeners controller) {
    this.controller = controller;
    boardPanel.setController(controller);
  }
}
