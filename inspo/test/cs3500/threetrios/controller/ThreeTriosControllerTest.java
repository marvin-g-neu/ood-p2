package cs3500.threetrios.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelImplementation;
import cs3500.threetrios.strategy.MockModelOneValidMove;
import cs3500.threetrios.strategy.MockModelRecordActions;
import cs3500.threetrios.view.ThreeTriosGraphicalView;

/**
 * Tests for ThreeTriosController.
 */
public class ThreeTriosControllerTest {

  private ThreeTriosController controller;
  private ThreeTriosModel<?> model;
  private ThreeTriosGraphicalView view;
  private StringBuilder log;

  @Before
  public void setup() {
    log = new StringBuilder();
    view = new MockViewRecordActions(log);
  }

  // tests for the Features interface.

  @Test
  public void selectCardGameOver() {
    model = new MockModelGameWon();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.selectCard("RED", 0);
    Assert.assertEquals("Displaying message: The game is over.\n", log.toString());
  }

  @Test
  public void selectCardWrongColor() {
    model = new MockModelRedToPlay();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.selectCard("BLUE", 0);
    Assert.assertEquals("Displaying message: Player RED: You cannot select a card from your"
            + " opponent's hand.\n", log.toString());
  }

  @Test
  public void selectCardNotCurrentPlayer() {
    model = new MockModelRedToPlay();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.BLUE), view);
    controller.selectCard("BLUE", 0);
    Assert.assertEquals("Displaying message: Player BLUE: It is not your turn.\n", log.toString());
  }

  @Test
  public void clickCellGameOver() {
    model = new MockModelGameWon();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.clickCell(0, 0);
    Assert.assertEquals("Displaying message: The game is over.\n", log.toString());
  }

  @Test
  public void clickCellNoSelection() {
    model = new MockModelRedToPlay();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.clickCell(0, 0);
    Assert.assertEquals("Displaying message: Player RED: Please select a card from the hand"
            + " first.\n", log.toString());
  }

  @Test
  public void clickCellInvalidCell() {
    model = new MockModelOneValidMove(log); // 3x3 board, only valid if play to (2,2)
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.selectCard("RED", 0);
    controller.clickCell(0, 0);
    Assert.assertEquals("Displaying message: Player RED: Please play to an open cell.\n",
            log.toString());
  }

  @Test
  public void selectCardAndClickCell() {
    model = new MockModelRecordActions(log);
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.BLUE), view);
    Assert.assertTrue(controller.selectCard("BLUE", 0));
    controller.clickCell(0, 0);
    Assert.assertEquals("Checking moves for cell: (0, 0)\nPlayed card at index 0 to (0, 0)\n",
            log.toString());
    System.out.println(log);
  }

  // tests for the ModelFeatures interface

  @Test
  public void refreshViewTest() {
    model = new ThreeTriosModelImplementation();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.refreshView();
    Assert.assertEquals("Refreshed the view\n", log.toString());
  }

  @Test
  public void makeVisibleTest() {
    model = new ThreeTriosModelImplementation();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.makeVisible();
    Assert.assertEquals("Made the view visible\n", log.toString());
  }

  @Test
  public void notifyComputerPlayer() {
    model = new MockModelRedToPlay();
    controller = new ThreeTriosController(model, new MockComputerPlayerLog(log), view);
    controller.notifyCurrentPlayer();
    Assert.assertEquals("Got the best move for the model\n", log.toString());
  }

  @Test
  public void notifyNotPlayerTurn() {
    model = new MockModelRedToPlay();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.BLUE), view);
    controller.notifyCurrentPlayer();
    Assert.assertEquals("Reset selected card\nRefreshed the view\n", log.toString());
  }

  @Test
  public void notifyCurrentPlayerSuccess() {
    model = new MockModelRedToPlay();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.notifyCurrentPlayer();
    Assert.assertEquals("Reset selected card\nRefreshed the view\n"
            + "Displaying message: Player RED: It is your turn.\n", log.toString());
  }

  @Test
  public void notifyGameWon() {
    model = new MockModelGameWon();
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.notifyGameOver();
    Assert.assertEquals("Refreshed the view\nDisplaying message: Game over! Player RED wins"
            + " 5 to 3.\n", log.toString());
  }

  @Test
  public void notifyGameTied() {
    model = new MockModelRedToPlay(); // game is not won in this mock
    controller = new ThreeTriosController(model, new HumanPlayer(PlayerColor.RED), view);
    controller.notifyGameOver();
    Assert.assertEquals("Refreshed the view\nDisplaying message: Game over! "
            + "The game has ended in a tie.\n", log.toString());
  }
}
