package cs3500.threetrios.view;

import cs3500.threetrios.model.AttackValue;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelImplementation;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ThreeTriosTextView.
 */
public class ThreeTriosTextViewTest {

  private ThreeTriosModel<ThreeTriosCard> model;
  private StringBuilder output;
  private ThreeTriosTextView view;

  /**
   * Sets up the game model, deck, and view before each test.
   */
  @Before
  public void setUp() {
    List<ThreeTriosCard> bigDeck;
    bigDeck = Arrays.asList(
            new ThreeTriosCard("CorruptKing", AttackValue.seven, AttackValue.three,
                    AttackValue.nine, AttackValue.A),
            new ThreeTriosCard("AngryDragon", AttackValue.two, AttackValue.eight, AttackValue.nine,
                    AttackValue.nine),
            new ThreeTriosCard("WindBird", AttackValue.seven, AttackValue.two, AttackValue.five,
                    AttackValue.three),
            new ThreeTriosCard("HeroKnight", AttackValue.A, AttackValue.two, AttackValue.four,
                    AttackValue.four),
            new ThreeTriosCard("WorldDragon", AttackValue.eight, AttackValue.three,
                    AttackValue.five, AttackValue.seven),
            new ThreeTriosCard("SkyWhale", AttackValue.four, AttackValue.five, AttackValue.nine,
                    AttackValue.nine),
            new ThreeTriosCard("BigWig", AttackValue.five, AttackValue.two, AttackValue.seven,
                    AttackValue.eight),
            new ThreeTriosCard("TinyTim", AttackValue.four, AttackValue.four, AttackValue.two,
                    AttackValue.six),
            new ThreeTriosCard("JumpingJacks", AttackValue.five, AttackValue.two, AttackValue.seven,
                    AttackValue.A),
            new ThreeTriosCard("PenguinOverlord", AttackValue.five, AttackValue.nine, AttackValue.A,
                    AttackValue.two),
            new ThreeTriosCard("SmellySocks", AttackValue.seven, AttackValue.eight, AttackValue.two,
                    AttackValue.three),
            new ThreeTriosCard("TwinkleTwinkle", AttackValue.two, AttackValue.eight,
                    AttackValue.six, AttackValue.three),
            new ThreeTriosCard("SmallSteps", AttackValue.one, AttackValue.eight, AttackValue.six,
                    AttackValue.six),
            new ThreeTriosCard("GenghisKhan", AttackValue.A, AttackValue.five, AttackValue.six,
                    AttackValue.two),
            new ThreeTriosCard("SpicySausage", AttackValue.four, AttackValue.six, AttackValue.two,
                    AttackValue.three),
            new ThreeTriosCard("GamblingAddiction", AttackValue.seven, AttackValue.four,
                    AttackValue.nine, AttackValue.A)
    );

    // Define a 5x5 grid with card cells and holes
    List<List<CellType>> largeGridWithHoles;
    largeGridWithHoles = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<CellType> row = new ArrayList<>();
      for (int j = 0; j < 5; j++) {
        if ((i + j) % 2 == 0) {
          row.add(CellType.CARDCELL);
        } else {
          row.add(CellType.HOLE);
        }
      }
      largeGridWithHoles.add(row);
    }

    // Initialize the model, start the game, and the view
    model = new ThreeTriosModelImplementation();
    model.startGame(bigDeck, 5, 5, largeGridWithHoles);

    output = new StringBuilder();
    view = new ThreeTriosTextView(model, output);
  }

  @Test
  public void testRenderBoardWithLargeGrid() throws IOException {
    view.renderBoard();
    String expectedOutput = model.toString();
    assertEquals("Rendering large grid",
            expectedOutput, output.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    new ThreeTriosTextView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModelWithOutput() {
    new ThreeTriosTextView(null, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullOutput() {
    new ThreeTriosTextView(model, null);
  }

  @Test
  public void testValidConstructor() {
    view = new ThreeTriosTextView(model, output);
    try {
      view.renderMessage("Constructor is valid");
      assertEquals("Constructor is valid", output.toString());
    } catch (IOException e) {
      throw new AssertionError("IOException should not be thrown", e);
    }
  }

  @Test
  public void testRenderMessage() throws IOException {
    view = new ThreeTriosTextView(model, output);
    view.renderMessage("Three Trios!");
    assertEquals("Three Trios!", output.toString());
  }

  @Test
  public void testRenderBoardAfterMove() throws IOException {
    // Player Red plays the first card to cell (0, 0)
    model.playToCell(0, 0, 0);
    view.renderBoard();
    String expectedOutputAfterMove = model.toString();
    assertEquals("Renders board after a move",
            expectedOutputAfterMove, output.toString());
  }

  @Test
  public void testRenderBoardAfterMultipleMoves() throws IOException {
    model.playToCell(0, 0, 0);
    model.playToCell(2, 2, 0);
    view.renderBoard();
    String expectedOutputAfterMoves = model.toString();
    assertEquals("Renders board after multiple moves",
            expectedOutputAfterMoves, output.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderBoardBeforeGameStart() throws IOException {
    ThreeTriosModel<ThreeTriosCard> unstartedModel = new ThreeTriosModelImplementation();
    ThreeTriosTextView unstartedView = new ThreeTriosTextView(unstartedModel, output);
    unstartedView.renderBoard();
  }
}