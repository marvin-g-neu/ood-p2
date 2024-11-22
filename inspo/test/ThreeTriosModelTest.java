import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.AttackValue;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelImplementation;

/**
 * Tests for public methods of the ThreeTriosModel interface.
 */
public class ThreeTriosModelTest {

  private ThreeTriosModel<ThreeTriosCard> model;

  private List<ThreeTriosCard> bigDeck; // 16 cards, works on all 3 grids.
  private List<ThreeTriosCard> smallDeck; // 5 cards, doesn't work on allCardCellGrid

  // 3x5 grid with all card cells
  private List<List<CellType>> allCardCellGrid;
  // CCX
  // XCX
  // XCC
  private List<List<CellType>> allCardCellsTouch;
  // CXX
  // CXC
  // XCC
  private List<List<CellType>> twoCardCellGroups;

  @Before
  public void setup() {
    model = new ThreeTriosModelImplementation();
    smallDeckSetup();
    bigDeckSetup();
    allCardCellGridSetup();
    allCardCellsTouchGridSetup();
    twoCardCellGroupsGridSetup();
  }

  private void smallDeckSetup() {
    smallDeck = new ArrayList<>();
    smallDeck.add(new ThreeTriosCard("CorruptKing", AttackValue.seven, AttackValue.three,
            AttackValue.nine, AttackValue.A));
    smallDeck.add(new ThreeTriosCard("AngryDragon", AttackValue.two, AttackValue.eight,
            AttackValue.nine, AttackValue.nine));
    smallDeck.add(new ThreeTriosCard("WindBird", AttackValue.seven, AttackValue.two,
            AttackValue.five, AttackValue.three));

    smallDeck.add(new ThreeTriosCard("HeroKnight", AttackValue.A, AttackValue.two,
            AttackValue.four, AttackValue.four));
    smallDeck.add(new ThreeTriosCard("WorldDragon", AttackValue.eight, AttackValue.three,
            AttackValue.five, AttackValue.seven));
    smallDeck.add(new ThreeTriosCard("SkyWhale", AttackValue.four, AttackValue.five,
            AttackValue.nine, AttackValue.nine));
  }

  private void bigDeckSetup() {
    bigDeck = new ArrayList<>();
    bigDeck.add(new ThreeTriosCard("CorruptKing", AttackValue.seven, AttackValue.three,
            AttackValue.nine, AttackValue.A));
    bigDeck.add(new ThreeTriosCard("AngryDragon", AttackValue.two, AttackValue.eight,
            AttackValue.nine, AttackValue.nine));
    bigDeck.add(new ThreeTriosCard("WindBird", AttackValue.seven, AttackValue.two,
            AttackValue.five, AttackValue.three));
    bigDeck.add(new ThreeTriosCard("HeroKnight", AttackValue.A, AttackValue.two,
            AttackValue.four, AttackValue.four));
    bigDeck.add(new ThreeTriosCard("WorldDragon", AttackValue.eight, AttackValue.three,
            AttackValue.five, AttackValue.seven));
    bigDeck.add(new ThreeTriosCard("SkyWhale", AttackValue.four, AttackValue.five,
            AttackValue.nine, AttackValue.nine));
    bigDeck.add(new ThreeTriosCard("BigWig", AttackValue.five, AttackValue.two,
            AttackValue.seven, AttackValue.eight));
    bigDeck.add(new ThreeTriosCard("TinyTim", AttackValue.four, AttackValue.four,
            AttackValue.two, AttackValue.six));

    bigDeck.add(new ThreeTriosCard("JumpingJacks", AttackValue.five, AttackValue.two,
            AttackValue.seven, AttackValue.A));
    bigDeck.add(new ThreeTriosCard("PenguinOverlord", AttackValue.five, AttackValue.nine,
            AttackValue.A, AttackValue.two));
    bigDeck.add(new ThreeTriosCard("SmellySocks", AttackValue.seven, AttackValue.eight,
            AttackValue.two, AttackValue.three));
    bigDeck.add(new ThreeTriosCard("TwinkleTwinkle", AttackValue.two, AttackValue.eight,
            AttackValue.six, AttackValue.three));
    bigDeck.add(new ThreeTriosCard("SmallSteps", AttackValue.one, AttackValue.eight,
            AttackValue.six, AttackValue.six));
    bigDeck.add(new ThreeTriosCard("GenghisKhan", AttackValue.A, AttackValue.five,
            AttackValue.six, AttackValue.two));
    bigDeck.add(new ThreeTriosCard("SpicySausage", AttackValue.four, AttackValue.six,
            AttackValue.two, AttackValue.three));
    bigDeck.add(new ThreeTriosCard("GamblingAddiction", AttackValue.seven, AttackValue.four,
            AttackValue.nine, AttackValue.A));
  }

  private void twoCardCellGroupsGridSetup() {
    twoCardCellGroups = new ArrayList<>();
    twoCardCellGroups.add(new ArrayList<>());
    twoCardCellGroups.add(new ArrayList<>());
    twoCardCellGroups.add(new ArrayList<>());
    twoCardCellGroups.get(0).add(CellType.CARDCELL);
    twoCardCellGroups.get(0).add(CellType.HOLE);
    twoCardCellGroups.get(0).add(CellType.HOLE);
    twoCardCellGroups.get(1).add(CellType.CARDCELL);
    twoCardCellGroups.get(1).add(CellType.HOLE);
    twoCardCellGroups.get(1).add(CellType.CARDCELL);
    twoCardCellGroups.get(2).add(CellType.HOLE);
    twoCardCellGroups.get(2).add(CellType.CARDCELL);
    twoCardCellGroups.get(2).add(CellType.CARDCELL);
  }

  private void allCardCellsTouchGridSetup() {
    allCardCellsTouch = new ArrayList<>();
    allCardCellsTouch.add(new ArrayList<>());
    allCardCellsTouch.add(new ArrayList<>());
    allCardCellsTouch.add(new ArrayList<>());
    allCardCellsTouch.get(0).add(CellType.CARDCELL);
    allCardCellsTouch.get(0).add(CellType.CARDCELL);
    allCardCellsTouch.get(0).add(CellType.HOLE);
    allCardCellsTouch.get(1).add(CellType.HOLE);
    allCardCellsTouch.get(1).add(CellType.CARDCELL);
    allCardCellsTouch.get(1).add(CellType.HOLE);
    allCardCellsTouch.get(2).add(CellType.HOLE);
    allCardCellsTouch.get(2).add(CellType.CARDCELL);
    allCardCellsTouch.get(2).add(CellType.CARDCELL);
  }

  private void allCardCellGridSetup() {
    allCardCellGrid = new ArrayList<>();
    allCardCellGrid.add(new ArrayList<>());
    allCardCellGrid.add(new ArrayList<>());
    allCardCellGrid.add(new ArrayList<>());
    allCardCellGrid.get(0).add(CellType.CARDCELL);
    allCardCellGrid.get(0).add(CellType.CARDCELL);
    allCardCellGrid.get(0).add(CellType.CARDCELL);
    allCardCellGrid.get(0).add(CellType.CARDCELL);
    allCardCellGrid.get(0).add(CellType.CARDCELL);
    allCardCellGrid.get(1).add(CellType.CARDCELL);
    allCardCellGrid.get(1).add(CellType.CARDCELL);
    allCardCellGrid.get(1).add(CellType.CARDCELL);
    allCardCellGrid.get(1).add(CellType.CARDCELL);
    allCardCellGrid.get(1).add(CellType.CARDCELL);
    allCardCellGrid.get(2).add(CellType.CARDCELL);
    allCardCellGrid.get(2).add(CellType.CARDCELL);
    allCardCellGrid.get(2).add(CellType.CARDCELL);
    allCardCellGrid.get(2).add(CellType.CARDCELL);
    allCardCellGrid.get(2).add(CellType.CARDCELL);
  }

  @Test
  public void modelConstructorTest() {
    Assert.assertEquals("Game has not started", model.getStatus(),
            ThreeTriosModel.GameStatus.NotStarted);
  }

  // testing startGame (no config file)

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullDeckTest() {
    model.startGame(null, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullGridTest() {
    model.startGame(bigDeck, 5, 3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidGridWidthTest() {
    model.startGame(bigDeck, -1, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidGridHeightTest() {
    model.startGame(bigDeck, 5, -1, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckContainsNullTest() {
    bigDeck.add(null);
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckWithDuplicatesTest() {
    bigDeck.add(new ThreeTriosCard("CorruptKing", AttackValue.seven, AttackValue.three,
            AttackValue.nine, AttackValue.A));
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckTooManyCellTypeRows() {
    allCardCellGrid.add(new ArrayList<>()); // creating extra row of correct length
    allCardCellGrid.get(3).add(CellType.CARDCELL);
    allCardCellGrid.get(3).add(CellType.CARDCELL);
    allCardCellGrid.get(3).add(CellType.CARDCELL);
    allCardCellGrid.get(3).add(CellType.CARDCELL);
    allCardCellGrid.get(3).add(CellType.CARDCELL);
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullCellTypeRow() {
    allCardCellGrid.set(0, null); // replace row with null
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameCellTypeRowLengthMismatch() {
    allCardCellGrid.get(1).remove(0); // size 4 mismatch with grid width 5
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameCellTypeRowContainsNull() {
    allCardCellGrid.get(0).set(0, null);
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameEvenCardCellCount() {
    allCardCellGrid.get(0).set(0, CellType.HOLE); // now 14 card cells
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckTooSmall() {
    model.startGame(smallDeck, 5, 3, allCardCellGrid);
  }

  @Test
  public void startGameSuccessfully() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertEquals("Correct grid width", 5, model.getGridWidth());
    Assert.assertEquals("Correct grid height", 3, model.getGridHeight());
    Assert.assertEquals("Correct grid + Red player hand setup", "Player: RED\n"
            + "_____\n_____\n_____\nHand:\nCorruptKing 7 3 9 A\nAngryDragon 2 8 9 9\n"
            + "WindBird 7 2 5 3\nHeroKnight A 2 4 4\nWorldDragon 8 3 5 7\nSkyWhale 4 5 9 9\n"
            + "BigWig 5 2 7 8\nTinyTim 4 4 2 6", model.toString());
    Assert.assertEquals("Correct Blue player hand setup", "[JumpingJacks 5 2 7 A,"
            + " PenguinOverlord 5 9 A 2, SmellySocks 7 8 2 3, TwinkleTwinkle 2 8 6 3,"
            + " SmallSteps 1 8 6 6, GenghisKhan A 5 6 2, SpicySausage 4 6 2 3,"
            + " GamblingAddiction 7 4 9 A]", model.getHand(PlayerColor.BLUE).toString());
    Assert.assertEquals("Game status updated", ThreeTriosModel.GameStatus.Playing,
            model.getStatus());
  }

  // testing startGame (with config files)
  // only testing null file paths and successful game start - other exceptions already tested
  // (tested in previous startGame tests and deck / grid config reader tests)

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullFilePaths() {
    model.startGame(null, null);
  }

  @Test
  public void startGameFilePathsSuccessfully() {
    // should work the same as previous test startGameSuccessfully
    model.startGame("gridConfigFiles" + File.separator + "5x3AllCardCells.txt",
            "deckConfigFiles" + File.separator + "bigDeck.txt");
    Assert.assertEquals("Correct grid width", 5, model.getGridWidth());
    Assert.assertEquals("Correct grid height", 3, model.getGridHeight());
    Assert.assertEquals("Correct grid + Red player hand setup", "Player: RED\n"
            + "_____\n_____\n_____\nHand:\nCorruptKing 7 3 9 A\nAngryDragon 2 8 9 9\n"
            + "WindBird 7 2 5 3\nHeroKnight A 2 4 4\nWorldDragon 8 3 5 7\nSkyWhale 4 5 9 9\n"
            + "BigWig 5 2 7 8\nTinyTim 4 4 2 6", model.toString());
    Assert.assertEquals("Correct Blue player hand setup", "[JumpingJacks 5 2 7 A,"
            + " PenguinOverlord 5 9 A 2, SmellySocks 7 8 2 3, TwinkleTwinkle 2 8 6 3,"
            + " SmallSteps 1 8 6 6, GenghisKhan A 5 6 2, SpicySausage 4 6 2 3,"
            + " GamblingAddiction 7 4 9 A]", model.getHand(PlayerColor.BLUE).toString());
    Assert.assertEquals("Game status updated", ThreeTriosModel.GameStatus.Playing,
            model.getStatus());
  }

  // testing playToCell

  @Test(expected = IllegalStateException.class)
  public void playToCellGameNotInProgress() {
    model.playToCell(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playToCellInvalidCard() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(0, 0, -1); // only 8 cards in hand
  }

  @Test(expected = IllegalArgumentException.class)
  public void playToCellOutOfBounds() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(-1, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playToCellGivenHole() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    model.playToCell(0, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playToCellAtOccupiedCell() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(0, 0, 0);
    model.playToCell(0, 0, 1);
  }


  @Test
  public void playToCellSuccessfully() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(0, 0, 0);
    Assert.assertEquals("Hand now has 7 cards", 7, model.getHand(PlayerColor.RED).size());
    Assert.assertEquals("Cell card updated", "CorruptKing 7 3 9 A",
            model.getCardAt(0, 0).toString());
    Assert.assertEquals("Cell owner updated", PlayerColor.RED, model.getCellPlayerAt(0, 0));
    Assert.assertEquals("Blue's turn next", PlayerColor.BLUE, model.getCurrentPlayer());
  }

  @Test
  public void battlePhaseBehaviorNoComboStep() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(1, 2, 0); // 7 3 9 A
    model.playToCell(1, 3, 0); // 5 2 7 A. A > 9, should flip
    Assert.assertEquals("Flip when greater value", PlayerColor.BLUE, model.getCellPlayerAt(1, 2));
    model.playToCell(0, 3, 1); // 7 2 5 3. 2 < 5, should not flip
    Assert.assertEquals("No flip when smaller value", PlayerColor.BLUE,
            model.getCellPlayerAt(1, 3));
    model.playToCell(2, 2, 0); // 5 9 A 2, 5 > 3 but adjacent is also blue, no flip
    Assert.assertEquals("No flip when same color", PlayerColor.BLUE,
            model.getCellPlayerAt(1, 2));
    model.playToCell(1, 4, 2); // 8 3 5 7, 7 = 7, should not flip
    Assert.assertEquals("No flip when equal values", PlayerColor.BLUE, model.getCellPlayerAt(1, 3));
    model.playToCell(2, 1, 0);

    model.playToCell(0, 1, 1); // A 2 4 4
    model.playToCell(2, 3, 0);
    model.playToCell(1, 0, 0); // 2 8 9 9
    model.playToCell(0, 0, 0); // 1 8 6 6. Card on right: 6 > 4. Card below: 8 > 2. Double flip
    Assert.assertEquals("(0,1) flips", PlayerColor.BLUE, model.getCellPlayerAt(0, 1));
    Assert.assertEquals("(1,0) also flips", PlayerColor.BLUE, model.getCellPlayerAt(1, 0));
  }

  @Test
  public void battlePhaseBehaviorComboStep() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(1, 2, 0); // 7 3 9 A
    model.playToCell(1, 3, 0); // 5 2 7 A. A > 9, Red's card flips
    model.playToCell(2, 2, 1); // 7 2 5 3. 7 > 3, (1,2) should flip. Combo does not flip (1,3).
    Assert.assertEquals("Card at (1,2) flips", PlayerColor.RED, model.getCellPlayerAt(1, 2));
    Assert.assertEquals("Card at (1,3) does not flip", PlayerColor.BLUE,
            model.getCellPlayerAt(1, 3));
    model.playToCell(2, 3, 3); // 1 8 6 6, 6 > 5, (2,2) should flip. Combo flips (1,2) then stops.
    Assert.assertEquals("Card at (2,2) flips", PlayerColor.BLUE, model.getCellPlayerAt(2, 2));
    Assert.assertEquals("Combo flips card at (1,2)", PlayerColor.BLUE, model.getCellPlayerAt(1, 2));
  }

  // testing numOfCardsInDeck

  @Test(expected = IllegalStateException.class)
  public void numOfCardsInDeckGameNotStarted() {
    model.numOfCardsInDeck();
  }

  @Test
  public void numOfCardsInDeckSuccessful() {
    model.startGame(bigDeck, 3, 3, allCardCellsTouch); // 16 card deck, 5 card cell game
    Assert.assertEquals("Correct deck size", 10, model.numOfCardsInDeck());
  }

  // testing getGridWidth

  @Test(expected = IllegalStateException.class)
  public void getGridWidthGameNotStarted() {
    model.getGridWidth();
  }

  @Test
  public void getGridWidthSuccessfully() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertEquals("Correct grid width", 5, model.getGridWidth());
  }

  // testing getGridHeight

  @Test(expected = IllegalStateException.class)
  public void getGridHeightGameNotStarted() {
    model.getGridHeight();
  }

  @Test
  public void getGridHeightSuccessfully() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertEquals("Correct grid height", 3, model.getGridHeight());
  }

  // testing isGameOver

  @Test(expected = IllegalStateException.class)
  public void isGameOverGameNotStarted() {
    model.isGameOver();
  }

  @Test
  public void isGameOverTest() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    model.playToCell(0, 0, 0);
    model.playToCell(0, 1, 0);
    model.playToCell(1, 1, 0);
    model.playToCell(2, 1, 0); // one more open card cell
    Assert.assertFalse("Game is not over", model.isGameOver());
    Assert.assertEquals("Game is still in progress", ThreeTriosModel.GameStatus.Playing,
            model.getStatus());
    model.playToCell(2, 2, 0); // no more open cells
    Assert.assertTrue("Game is over", model.isGameOver());
    Assert.assertEquals("Game status updated on final move", ThreeTriosModel.GameStatus.GameOver,
            model.getStatus());
  }

  // testing isGameWon

  @Test(expected = IllegalStateException.class)
  public void isGameWonGameNotStarted() {
    model.isGameWon();
  }

  @Test(expected = IllegalStateException.class)
  public void isGameWonGameInProgress() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.isGameWon();
  }

  @Test
  public void isGameWonNoWinner() {
    model.startGame(smallDeck, 3, 3, twoCardCellGroups);
    // no flips occur, 3 Red and 2 Blue on grid, 1 Blue in hand.
    model.playToCell(1, 2, 0);
    model.playToCell(0, 0, 0);
    model.playToCell(2, 2, 0);
    model.playToCell(1, 0, 0);
    model.playToCell(2, 1, 0);
    Assert.assertFalse(model.isGameWon());
  }

  @Test
  public void isGameWonHasWinner() {
    model.startGame(smallDeck, 3, 3, twoCardCellGroups);
    model.playToCell(1, 2, 0); // 7 3 9 A
    model.playToCell(2, 2, 0); // A 2 4 4, A > 3, (1,2) flips
    // no more flips. 3 Blue and 2 Red on grid, 1 Blue in hand, Blue wins.
    model.playToCell(0, 0, 0);
    model.playToCell(2, 1, 0);
    model.playToCell(1, 0, 0);
    Assert.assertTrue(model.isGameWon());
  }

  // testing getWinner

  @Test(expected = IllegalStateException.class)
  public void getWinnerGameNotStarted() {
    model.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void getWinnerGameInProgress() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void getWinnerGameTied() {
    model.startGame(smallDeck, 3, 3, twoCardCellGroups);
    // no flips occur, 3 Red and 2 Blue on grid, 1 Blue in hand.
    model.playToCell(1, 2, 0);
    model.playToCell(0, 0, 0);
    model.playToCell(2, 2, 0);
    model.playToCell(1, 0, 0);
    model.playToCell(2, 1, 0);
    model.getWinner();
  }

  @Test
  public void getWinnerSuccessfully() {
    model.startGame(smallDeck, 3, 3, twoCardCellGroups);
    model.playToCell(1, 2, 0); // 7 3 9 A
    model.playToCell(2, 2, 0); // A 2 4 4, A > 3, (1,2) flips
    // no more flips. 3 Blue and 2 Red on grid, 1 Blue in hand, Blue wins.
    model.playToCell(0, 0, 0);
    model.playToCell(2, 1, 0);
    model.playToCell(1, 0, 0);
    Assert.assertEquals("Player Blue is the winner", PlayerColor.BLUE, model.getWinner());
  }

  // testing getHand

  @Test(expected = IllegalStateException.class)
  public void getHandGameNotStarted() {
    model.getHand(PlayerColor.RED);
  }

  @Test
  public void getHandSuccessFully() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertEquals("Red's hand has 8 cards", 8, model.getHand(PlayerColor.RED).size());
    Assert.assertEquals("Blue's hand has 8 cards", 8, model.getHand(PlayerColor.BLUE).size());
    Assert.assertEquals("Red's hand has the correct cards", "[CorruptKing 7 3 9 A,"
                    + " AngryDragon 2 8 9 9, WindBird 7 2 5 3, HeroKnight A 2 4 4,"
                    + " WorldDragon 8 3 5 7, SkyWhale 4 5 9 9, BigWig 5 2 7 8, TinyTim 4 4 2 6]",
            model.getHand(PlayerColor.RED).toString());
    Assert.assertEquals("Blue's hand has the correct cards", "[JumpingJacks 5 2 7 A,"
            + " PenguinOverlord 5 9 A 2, SmellySocks 7 8 2 3, TwinkleTwinkle 2 8 6 3,"
            + " SmallSteps 1 8 6 6, GenghisKhan A 5 6 2, SpicySausage 4 6 2 3,"
            + " GamblingAddiction 7 4 9 A]", model.getHand(PlayerColor.BLUE).toString());
  }

  @Test
  public void getHandModifyResultList() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    List<ThreeTriosCard> redHand = model.getHand(PlayerColor.RED);
    redHand.remove(0);
    Assert.assertEquals("Actual hand size unchanged", 8, model.getHand(PlayerColor.RED).size());
  }

  @Test
  public void getHandModifyCards() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    List<ThreeTriosCard> redHand = model.getHand(PlayerColor.RED);
    redHand.set(0, null);
    Assert.assertEquals("Actual card in hand unchanged", "CorruptKing 7 3 9 A",
            model.getHand(PlayerColor.RED).get(0).toString());
  }

  // testing getCardAt

  @Test(expected = IllegalStateException.class)
  public void getCardAtGameNotStarted() {
    model.getCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtNonCardCell() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    model.getCardAt(0, 2);
  }

  @Test
  public void getCardAtOutOfBoundsCell() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertThrows(IllegalArgumentException.class, () -> model.getCardAt(-1, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.getCardAt(3, 5));
  }

  @Test
  public void getCardAtSuccessful() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(0, 0, 0);
    Assert.assertEquals("Correct card", "CorruptKing 7 3 9 A", model.getCardAt(0, 0).toString());
  }

  // testing getCellTypeAt

  @Test(expected = IllegalStateException.class)
  public void getCellTypeAtGameNotStarted() {
    model.getCellTypeAt(0, 0);
  }

  @Test
  public void getCellTypeAtOutOfBoundsCell() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertThrows(IllegalArgumentException.class, () -> model.getCellTypeAt(-1, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.getCellTypeAt(3, 5));
  }

  @Test
  public void getCellTypeAtSuccessful() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    Assert.assertEquals("Card cell", CellType.CARDCELL, model.getCellTypeAt(0, 0));
    Assert.assertEquals("Hole", CellType.HOLE, model.getCellTypeAt(0, 2));
  }

  // testing getCellPlayerAt

  @Test(expected = IllegalStateException.class)
  public void getCellPlayerAtGameNotStarted() {
    model.getCellPlayerAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCellPlayerAtNonCardCell() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    model.getCellPlayerAt(0, 2);
  }

  @Test
  public void getCellPlayerAtOutOfBoundsCell() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertThrows(IllegalArgumentException.class, () -> model.getCellPlayerAt(-1, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.getCellPlayerAt(3, 5));
  }

  @Test
  public void getCellPlayerAtSuccessful() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertNull("No card yet", model.getCellPlayerAt(0, 0));
    model.playToCell(0, 0, 0); // Player Red plays
    Assert.assertEquals("Red owns card at (0, 0)", PlayerColor.RED, model.getCellPlayerAt(0, 0));
    model.playToCell(1, 1, 0); // Player Blue plays
    Assert.assertEquals("Blue owns card at (1, 1)", PlayerColor.BLUE, model.getCellPlayerAt(1, 1));
  }

  // testing getStatus

  @Test
  public void getStatusTest() {
    Assert.assertEquals("Game not started", ThreeTriosModel.GameStatus.NotStarted,
            model.getStatus());
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    Assert.assertEquals("Game in progress", ThreeTriosModel.GameStatus.Playing, model.getStatus());
    model.playToCell(0, 0, 0);
    model.playToCell(0, 1, 0);
    model.playToCell(1, 1, 0);
    model.playToCell(2, 1, 0); // one more open card cell
    Assert.assertEquals("Game still in progress", ThreeTriosModel.GameStatus.Playing,
            model.getStatus());
    model.playToCell(2, 2, 0); // all card cells filled
    Assert.assertEquals("Game is over", ThreeTriosModel.GameStatus.GameOver, model.getStatus());
  }

  // testing getCurrentPlayer

  @Test(expected = IllegalStateException.class)
  public void getCurrentPlayerGameHasNotStarted() {
    model.getCurrentPlayer();
  }

  @Test
  public void getCurrentPlayerSuccessfully() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    Assert.assertEquals("Red is playing", PlayerColor.RED, model.getCurrentPlayer());
    model.playToCell(0, 0, 0);
    Assert.assertEquals("Blue is playing", PlayerColor.BLUE, model.getCurrentPlayer());
    model.playToCell(0, 1, 0);
    model.playToCell(1, 1, 0);
    model.playToCell(2, 1, 0);
    model.playToCell(2, 2, 0);
    Assert.assertThrows("Game is over", IllegalStateException.class,
        () -> model.getCurrentPlayer());
  }

  // testing gridCopy

  @Test(expected = IllegalStateException.class)
  public void gridCopyGameNotStarted() {
    model.gridCopy();
  }

  @Test
  public void gridCopyMutation() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Cell[][] gridCopy = model.gridCopy();
    gridCopy[0][0].setCard(new ThreeTriosCard("blurb", AttackValue.A, AttackValue.A,
            AttackValue.A, AttackValue.A));
    gridCopy[0][0].setOwner(PlayerColor.RED);
    Assert.assertNull("no effect on card in cell", model.getCardAt(0, 0));
    Assert.assertNull("no effect on cell owner", model.getCellPlayerAt(0, 0));
  }

  // testing legalPlayToCell

  @Test(expected = IllegalStateException.class)
  public void legalPlayToCellGameNotStarted() {
    model.legalPlayToCell(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void legalPlayToCellOutOfBounds() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.legalPlayToCell(-1, -1);
  }

  @Test
  public void legalPlayToCellNotCardCell() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    Assert.assertFalse("Cell is a hole", model.legalPlayToCell(0, 2));
  }

  @Test
  public void legalPlayToCellOccupiedCell() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    model.playToCell(0, 0, 0);
    Assert.assertFalse("Cell is occupied", model.legalPlayToCell(0, 0));
  }

  @Test
  public void legalPlayToCellOpenCell() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertTrue("Cell is an unoccupied card cell", model.legalPlayToCell(0, 0));
  }

  // testing numCardsFlippedWhenPlayed

  @Test(expected = IllegalStateException.class)
  public void numCardsFlippedGameNotPlaying() {
    model.numCardsFlippedWhenPlayed(0, 0, 0);
  }

  @Test
  public void numCardsFlippedInvalidCell() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    model.playToCell(0, 0, 0);
    Assert.assertThrows("Given cell is out of bounds", IllegalArgumentException.class,
        () -> model.numCardsFlippedWhenPlayed(-1, -1, 0));
    Assert.assertThrows("Given cell is a hole", IllegalArgumentException.class,
        () -> model.numCardsFlippedWhenPlayed(0, 2, 0));
    Assert.assertThrows("Given cell is occupied", IllegalArgumentException.class,
        () -> model.numCardsFlippedWhenPlayed(0, 0, 0));
    Assert.assertThrows("Invalid card in hand index", IllegalArgumentException.class,
        () -> model.numCardsFlippedWhenPlayed(0, 1, -1));
  }

  @Test
  public void numCardsFlippedWhenPlayedTest() {
    model.startGame(bigDeck, 5, 3, allCardCellGrid);
    Assert.assertEquals("No adjacent cards", 0, model.numCardsFlippedWhenPlayed(1, 2, 0));
    model.playToCell(1, 2, 0); // 7 3 9 A
    Assert.assertEquals("Adjacent card will flip", 1, model.numCardsFlippedWhenPlayed(1, 3, 0));
    model.playToCell(1, 3, 0); // 5 2 7 A. A > 9
    Assert.assertEquals("Adjacent card will flip, no combo", 1,
            model.numCardsFlippedWhenPlayed(2, 2, 1));
    model.playToCell(2, 2, 1); // 7 2 5 3. 7 > 3. Combo does not flip (1,3).
    Assert.assertEquals("Adjacent card will flip, combo flips one more", 2,
            model.numCardsFlippedWhenPlayed(2, 3, 3));
    model.playToCell(2, 3, 3); // 1 8 6 6, 6 > 5. Combo flips (1,2) then stops.
  }

  // testing getPlayerScore

  @Test(expected = IllegalStateException.class)
  public void getPlayerScoreGameNotStarted() {
    model.getPlayerScore(PlayerColor.RED);
  }

  @Test
  public void getPlayerScoreTest() {
    model.startGame(smallDeck, 3, 3, twoCardCellGroups);
    Assert.assertEquals("Red has 3 cards in hand, 0 on grid", 3,
            model.getPlayerScore(PlayerColor.RED));
    Assert.assertEquals("Blue has 3 cards in hand, 0 on grid", 3,
            model.getPlayerScore(PlayerColor.BLUE));
    model.playToCell(1, 2, 0); // 7 3 9 A
    Assert.assertEquals("Red has 2 cards in hand, 1 on grid", 3,
            model.getPlayerScore(PlayerColor.RED));
    model.playToCell(2, 2, 0); // A 2 4 4, A > 3, (2,1) flips
    Assert.assertEquals("Red has 2 cards in hand, 0 on grid", 2,
            model.getPlayerScore(PlayerColor.RED));
    Assert.assertEquals("Blue has 2 cards in hand, 2 on grid", 2,
            model.getPlayerScore(PlayerColor.RED));
  }

  // testing toString

  @Test
  public void modelToStringGameNotStarted() {
    Assert.assertThrows("Game not started", IllegalStateException.class, () -> model.toString());
  }

  @Test
  public void modelToStringTest() {
    model.startGame(smallDeck, 3, 3, allCardCellsTouch);
    Assert.assertEquals("Grid and Player Red hand displayed",
            "Player: RED\n__ \n _ \n __\nHand:\nCorruptKing 7 3 9 A\nAngryDragon 2 8 9 9\n"
                    + "WindBird 7 2 5 3", model.toString());
    model.playToCell(0, 0, 0);
    Assert.assertEquals("Grid updated and Player Blue hand displayed",
            "Player: BLUE\nR_ \n _ \n __\nHand:\nHeroKnight A 2 4 4\nWorldDragon 8 3 5 7\n"
                    + "SkyWhale 4 5 9 9", model.toString());
    model.playToCell(2, 2, 0);
    Assert.assertEquals("Grid updated and Player Red hand updated",
            "Player: RED\nR_ \n _ \n _B\nHand:\nAngryDragon 2 8 9 9\nWindBird 7 2 5 3",
            model.toString());
  }

  // playing a full game of Three Trios

  @Test
  public void playFullGame() {
    // CXX
    // CXC
    // XCC
    model.startGame(smallDeck, 3, 3, twoCardCellGroups);
    // CXX
    // CXR
    // XCC
    model.playToCell(1, 2, 0); // 7 3 9 A
    // CXX
    // CXB
    // XCB
    model.playToCell(2, 2, 0); // A 2 4 4, A > 3, (2,1) flips
    // RXX
    // CXB
    // XCB
    model.playToCell(0, 0, 0); // 2 8 9 9
    // RXX
    // CXB
    // XBB
    model.playToCell(2, 1, 0); // 8 3 5 7
    // RXX
    // RXB
    // XBB
    model.playToCell(1, 0, 0); // 7 2 5 3
    // Red has 2 cards, Blue has 4 cards (3 on grid, 1 in hand).
    Assert.assertEquals("Player Blue is the winner", PlayerColor.BLUE, model.getWinner());
  }
}
