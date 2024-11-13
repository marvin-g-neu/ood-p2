package cs3500.threetrios.strategy;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.rules.RuleKeeper;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.model.card.CustomCard;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy 4:
 * Implements the minimax algorithm to choose the move that leaves 
 * the opponent with the least favorable options. It simulates possible 
 * moves up to a certain depth and selects the move that minimizes the
 * maximum gain of the opponent.
 */
public class MinimaxStrategy extends BasicStrategies {

  private final int depth;

  /**
   * Constructs a MinimaxStrategy with the specified search depth.
   *
   * @param depth the depth to which the minimax algorithm will search
   */
  public MinimaxStrategy(int depth) {
    if (depth < 1) {
      throw new IllegalArgumentException("Depth must be greater than 0");
    }
    if (depth > 5) {
      throw new IllegalArgumentException("Depth must be less than 5");
    }
    this.depth = depth;
  }

  /**
   * Constructs a MinimaxStrategy with a default search depth of 3.
   */
  public MinimaxStrategy() {
    this.depth = 3;
  }

  @Override
  public List<MakePlay> getBestMove(ThreeTriosModelInterface model, PlayerColor player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and/or player cannot be null");
    }

    PlayerColor opponent = (player == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;

    List<MakePlay> availableMoves = getAllAvailableMoves(model, player);
    if (availableMoves.isEmpty()) {
      // If no valid moves, return an empty list
      return new ArrayList<>();
    }

    MakePlay bestMove = null;
    int bestValue = Integer.MIN_VALUE;

    // For each possible move we can make we simulate the game state
    // if we make that move and find the minimax value of that game state
    for (MakePlay move : availableMoves) {
      ThreeTriosModelInterface modelCopy = model.copyModel();
      applyMove(modelCopy, move, player);

      int moveValue = minimax(modelCopy, depth - 1, false, player, opponent);
      if (moveValue > bestValue) {
        bestValue = moveValue;
        bestMove = move;
      }
    }

    // Return the move with the highest minimax value
    List<MakePlay> bestMoves = new ArrayList<>();
    if (bestMove != null) {
      bestMoves.add(bestMove);
    }

    // Break ties using the superclass method
    return Collections.singletonList(breakTies(bestMoves));
  }

  /**
   * The minimax recursive algorithm.
   *
   * @param model          the current game model
   * @param depth          the remaining depth to search
   * @param isMaximizing   whether the current layer is maximizing or minimizing
   * @param player         the original player
   * @param opponent       the opponent player
   * @return the evaluated score of the game state
   */
  private int minimax(ThreeTriosModelInterface model, int depth, boolean isMaximizing,
                       PlayerColor player, PlayerColor opponent) {
    if (depth == 0) {
      return evaluate(model, player, opponent);
    }

    // If we are maximizing we want to find the move that maximizes the score
    if (isMaximizing) {
      int maxEval = Integer.MIN_VALUE;
      List<MakePlay> availableMoves = getAllAvailableMoves(model, player);

      for (MakePlay move : availableMoves) {
        ThreeTriosModelInterface modelCopy = model.copyModel();
        applyMove(modelCopy, move, player);
        int eval = minimax(modelCopy, depth - 1, false, player, opponent);
        maxEval = Math.max(maxEval, eval);
      }
      return maxEval;
    } else {
      // If we are minimizing we want to find the move that minimizes the score
      int minEval = Integer.MAX_VALUE;
      List<MakePlay> availableMoves = getAllAvailableMoves(model, opponent);

      for (MakePlay move : availableMoves) {
        ThreeTriosModelInterface modelCopy = model.copyModel();
        applyMove(modelCopy, move, opponent);
        int eval = minimax(modelCopy, depth - 1, true, player, opponent);
        minEval = Math.min(minEval, eval);
      }
      return minEval;
    }
  }

  /**
   * Evaluates the game state from the perspective of the player.
   *
   * @param model    the current game model
   * @param player   the original player
   * @param opponent the opponent player
   * @return the difference in scores between the player and the opponent
   */
  private int evaluate(ThreeTriosModelInterface model, PlayerColor player, PlayerColor opponent) {
    return model.getScore(player) - model.getScore(opponent);
  }

  /**
   * Retrieves all available moves for the specified player.
   *
   * @param model  the current game model
   * @param player the player whose moves to retrieve
   * @return a list of all valid MakePlay moves
   */
  private List<MakePlay> getAllAvailableMoves(ThreeTriosModelInterface model, PlayerColor player) {
    List<MakePlay> availableMoves = new ArrayList<>();
    RuleKeeper rules = new BasicThreeTriosGame(model);

    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        if (model.getGrid().getCell(row, col).isEmpty()) {
          for (int cardIdx = 0; cardIdx < model.getPlayerHand(player).size(); cardIdx++) {
            MakePlay move = new MakePlay(cardIdx, row, col);
            CustomCard card = model.getPlayerHand(player).get(cardIdx);

            if (rules.isLegalMove(model.getGrid().getCell(row, col), card)) {
              availableMoves.add(move);
            }
          }
        }
      }
    }

    return availableMoves;
  }

  /**
   * Applies a move to the game model.
   *
   * @param model  the game model to apply the move to
   * @param move   the move to apply
   * @param player the player making the move
   */
  private void applyMove(ThreeTriosModelInterface model, MakePlay move, PlayerColor player) {
    model.playTurn(move.getCardInHand(), move.getRow(), move.getCol());
  }
}
