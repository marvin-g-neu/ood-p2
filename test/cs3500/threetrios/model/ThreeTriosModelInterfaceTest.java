package cs3500.threetrios.model;

import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class ThreeTriosModelInterfaceTest {
  private Grid ttbNoUnreachableGrid;
  private Grid ttbOneByOneGrid;
  private CustomCard redCard;
  private CustomCard unassignedCard;
  private Cell[][] noUnreachableBoard;
  private List<CustomCard> allNecessaryCards;

  private ThreeTriosModelInterface basicModel;

  @Before
  public void setUp() {
    GridFileReader gridReader = new GridFileReader();
    DeckFileReader deckReader = new DeckFileReader();
    Cell[][] oneByOneBoard = gridReader.readFile(
        "docs/boards/oneByOneBoard.config");
    noUnreachableBoard = gridReader.readFile(
        "docs/boards/boardWithNoUnreachableCardCells.config");
    allNecessaryCards = deckReader.readFile(
        "docs/cards/AllNecessaryCards.config");
    ttbNoUnreachableGrid = new ThreeTriosBoard(noUnreachableBoard);
    ttbOneByOneGrid = new ThreeTriosBoard(oneByOneBoard);

    redCard = new ThreeTriosCard("Card", AttackValue.A,
        AttackValue.A, AttackValue.A, AttackValue.A, CardColor.RED);
    unassignedCard = new ThreeTriosCard("Card", AttackValue.A,
        AttackValue.A, AttackValue.A, AttackValue.A);

    basicModel = new ClassicalThreeTriosModel();
  }

  @Test
  public void getCellStateAtThrowsWhenExpected() {
    assertThrows(IllegalStateException.class, () -> basicModel.getCellStateAt(0, 0));
    basicModel.startGame(ttbNoUnreachableGrid, allNecessaryCards);
    assertThrows(IllegalArgumentException.class, () -> basicModel.getCellStateAt(4, 6));
    assertThrows(IllegalArgumentException.class, () -> basicModel.getCellStateAt(3, 7));
    assertThrows(IllegalArgumentException.class, () -> basicModel.getCellStateAt(-1, 1));
    assertThrows(IllegalArgumentException.class, () -> basicModel.getCellStateAt(1, -1));
  }

  @Test
  public void getCellStateAtGetsStateCorrectly() {
    basicModel.startGame(ttbNoUnreachableGrid, allNecessaryCards, false);
    assertEquals(CellState.EMPTY, basicModel.getCellStateAt(0, 0));
    assertEquals(CellState.HOLE, basicModel.getCellStateAt(3, 6));
    basicModel.playTurn(0, 0, 0);
    assertEquals(CellState.RED, basicModel.getCellStateAt(0, 0));
    basicModel.playTurn(0, 2, 0);
    assertEquals(CellState.BLUE, basicModel.getCellStateAt(0, 2));
    assertEquals(CellState.RED, basicModel.getCellStateAt(0, 0));
  }

  @Test
  public void endGameThrowsWhenGameNotInProgress() {
    assertThrows(IllegalStateException.class, () -> basicModel.endGame());
    basicModel.startGame(ttbOneByOneGrid, allNecessaryCards);
    basicModel.playTurn(0, 0, 0);
    assertEquals(GameState.RED_WIN, basicModel.getGameState());
    assertThrows(IllegalStateException.class, () -> basicModel.endGame());
  }

  @Test
  public void endGameEndsGameWhenExpected() {
    basicModel.startGame(ttbNoUnreachableGrid, allNecessaryCards);
    basicModel.endGame();
    assertEquals(GameState.EARLY_QUIT, basicModel.getGameState());

    basicModel = new ClassicalThreeTriosModel();
    basicModel.startGame(ttbNoUnreachableGrid, allNecessaryCards);
    basicModel.playTurn(0, 0, 0);
    assertEquals(GameState.IN_PROGRESS, basicModel.getGameState());
    basicModel.playTurn(0, 2, 0);
    assertEquals(GameState.IN_PROGRESS, basicModel.getGameState());
    basicModel.endGame();
    assertEquals(GameState.EARLY_QUIT, basicModel.getGameState());
  }

  @Test
  public void getCurrentPlayerThrowsWhenGameNotInProgress() {
    assertThrows(IllegalStateException.class, () -> basicModel.getCurrentPlayer());
  }

  @Test
  public void getCurrentPlayerReturnsCurrentPlayer() {
    basicModel.startGame(ttbNoUnreachableGrid, allNecessaryCards);
    assertEquals(PlayerColor.RED, basicModel.getCurrentPlayer());
    basicModel.playTurn(0, 0, 0);
    assertEquals(PlayerColor.BLUE, basicModel.getCurrentPlayer());
    basicModel.playTurn(0, 2, 0);
    assertEquals(PlayerColor.RED, basicModel.getCurrentPlayer());
  }

  @Test
  public void getCurrentPlayerHandThrowsWhenGameNotStarted() {
    assertThrows(IllegalStateException.class, () -> basicModel.getCurrentPlayerHand());
    basicModel.startGame(ttbOneByOneGrid, allNecessaryCards);
    basicModel.playTurn(0, 0, 0);
    assertThrows(IllegalStateException.class, () -> basicModel.getCurrentPlayerHand());
  }

  @Test
  public void getCurrentPlayerHandGetsCorrectHand() {
    basicModel.startGame(ttbNoUnreachableGrid, allNecessaryCards);
    List<CustomCard> originalRedHand = basicModel.getCurrentPlayerHand();
    assertEquals(10, originalRedHand.size());
    basicModel.playTurn(0, 0, 0);
    List<CustomCard> originalBlueHand = basicModel.getCurrentPlayerHand();
    assertEquals(10, originalBlueHand.size());
    for (int i = 0; i < originalRedHand.size(); i++) {
      assertNotEquals(originalRedHand.get(i), originalBlueHand.get(i));
    }
    basicModel.playTurn(0, 2, 0);
    List<CustomCard> newRedHand = basicModel.getCurrentPlayerHand();
    assertEquals(9, newRedHand.size());
    assertFalse(newRedHand.contains(originalRedHand.get(0)));
  }
}