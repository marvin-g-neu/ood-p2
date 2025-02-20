package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.model.rules.RuleKeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of a Three Trios game model using classic rules.
 */
public class ClassicalThreeTriosModel extends BaseThreeTriosModel {
  private final Random rand;

  private ClassicalThreeTriosModel(RuleKeeper rules, Grid grid, List<CustomCard> deck,
                                   PlayerColor currentPlayer, GameState gameState,
                                   List<CustomCard> redHand, List<CustomCard> blueHand,
                                   boolean shuffle, Random rand) {
    super(rules, grid, deck, currentPlayer, gameState, redHand, blueHand, shuffle);
    this.rand = rand;
  }

  /**
   * Creates a model for a classic game of Three Trios.
   */
  public ClassicalThreeTriosModel() {
    // gameState is only set to NOT_STARTED in the constructor, so it can't go back to NOT_STARTED
    super();
    this.rand = new Random();
  }

  /**
   * Creates a model for a classic game of Three Trios where
   * the deck may be shuffled before the game.
   *
   * @param seed whether the deck should be shuffled
   */
  public ClassicalThreeTriosModel(long seed) {
    // gameState is only set to NOT_STARTED in the constructor, so it can't go back to NOT_STARTED
    super();
    this.rand = new Random(seed);
  }

  @Override
  public int getScore(PlayerColor player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    checkGameInProgress();

    int score = 0;
    for (Cell cell : grid.getCardCells()) {
      if (!cell.isEmpty() && cell.getCellColor() == player) {
        score++;
      }
    }
    return score;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck, boolean shuffle) {
    // gameState is only set to IN_PROGRESS in startGame which checks that it is NOT_STARTED first
    if (getGameState() != GameState.NOT_STARTED) {
      throw new IllegalStateException("Game is already started");
    }
    if (gameGrid == null || deck == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    for (int r = 0; r < gameGrid.getRows(); r++) {
      for (int c = 0; c < gameGrid.getCols(); c++) {
        if (gameGrid.getCell(r, c) == null) {
          throw new IllegalArgumentException("Grid cells cannot be null");
        }
      }
    }
    for (CustomCard card : deck) {
      if (card == null) {
        throw new IllegalArgumentException("Deck cards cannot be null");
      }
      if (card.getCurrentColor() != CardColor.UNASSIGNED) {
        card.setNewColor(CardColor.UNASSIGNED);
      }
    }
    grid = gameGrid;
    this.deck = deck;
    this.shuffle = shuffle;
    currentPlayer = PlayerColor.RED;
    setGameState(GameState.IN_PROGRESS);
    rules = new BasicThreeTriosGame(this);

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

    redHand = new ArrayList<>();
    blueHand = new ArrayList<>();
    for (int i = 0; i < Math.ceil(((cardCellCount + 1) / 2.0)); i++) {
      redHand.add(this.deck.remove(0));
      redHand.get(redHand.size() - 1).setNewColor(CardColor.RED);
      blueHand.add(this.deck.remove(0));
      blueHand.get(blueHand.size() - 1).setNewColor(CardColor.BLUE);
    }
  }

  @Override
  public ThreeTriosModelInterface copy() {
    return new ClassicalThreeTriosModel(rules, grid.copy(), cardListCopy(deck), currentPlayer,
        gameState, cardListCopy(redHand), cardListCopy(blueHand), shuffle, rand);
  }
}
