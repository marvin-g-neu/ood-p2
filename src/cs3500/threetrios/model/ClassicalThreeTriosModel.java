package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.card.PlayerColor;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of a Three Trios game model using classic rules.
 */
public class ClassicalThreeTriosModel extends BaseThreeTriosModel {
  private Random rand = new Random();

  /**
   * Creates a model for a classic game of Three Trios
   */
  public ClassicalThreeTriosModel() {
    this(false);
  }

  /**
   * Creates a model for a classic game of Three Trios where
   * the deck may be shuffled before the game
   *
   * @param shuffle whether the deck should be shuffled
   */
  public ClassicalThreeTriosModel(boolean shuffle) {
    this.gameState = GameState.NOT_STARTED;
    this.shuffle = shuffle;
    rules = new BasicThreeTriosGame(this);
  }

  @Override
  public boolean attackerWinsBattle(CustomCard attacker, CustomCard defender, Direction attackDirection) {
    if (attacker == null || defender == null || attackDirection == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    int attackerStrength = attacker.getStrength(attackDirection).getStrength();
    switch (attackDirection) {
      case NORTH:
        return attackerStrength > defender.getStrength(Direction.SOUTH).getStrength();
      case EAST:
        return attackerStrength > defender.getStrength(Direction.WEST).getStrength();
      case SOUTH:
        return attackerStrength > defender.getStrength(Direction.NORTH).getStrength();
      case WEST:
        return attackerStrength > defender.getStrength(Direction.EAST).getStrength();
      default: // should never happen
        throw new IllegalArgumentException("Unknown Direction");
    }
  }

  @Override
  public int getScore(PlayerColor player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    checkGameInProgress();

    int score = 0;
    for (Cell cell : grid.getCardCells()) {
      if (cell.getCellColor() == player) {
        score++;
      }
    }
    return score;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck, boolean shuffle) {
    if (gameState != GameState.NOT_STARTED) {
      throw new IllegalStateException("Game is already started");
    }
    if (grid == null || deck == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    for (int r = 0; r < grid.getRows(); r++) {
      for (int c = 0; c < grid.getCols(); c++) {
        if (grid.getCell(r, c) == null) {
          throw new IllegalArgumentException("Grid cells cannot be null");
        }
      }
    }
    for (CustomCard card : deck) {
      if (card == null) {
        throw new IllegalArgumentException("Deck cards cannot be null");
      }
    }
    grid = gameGrid;
    fullDeck = new ArrayList<>(deck);
    this.deck = new ArrayList<>(deck);
    this.shuffle = shuffle;
    currentPlayer = PlayerColor.RED;
    gameState = GameState.IN_PROGRESS;

    int cardCellCount = grid.getCardCells().size();
    if (cardCellCount % 2 == 0) {
      throw new IllegalArgumentException("Cards cannot be even");
    }
    if (cardCellCount >= this.deck.size()) {
      throw new IllegalArgumentException("There must be more cards than card cells");
    }

    if (shuffle) {
      Collections.shuffle(this.deck, rand);
    }
    for (int i = 0; i < (grid.getCardCells().size() + 1) / 2; i++) {
      redHand.add(this.deck.remove(0));
      blueHand.add(this.deck.remove(0));
    }
  }
}