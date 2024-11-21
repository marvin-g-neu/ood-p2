package cs3500.threetrios.strategy;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.rules.RuleKeeper;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;

import java.util.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy 3: We choose cards that are less likely to be flipped in general. 
 * That means considering for each position, for each card, for each direction, 
 * figuring out how many of the opponent’s cards can flip them. The card and 
 * position combination with the smallest chance of being flipped is the play 
 * made.
 */
public class MinimizingFlipStrategy extends BasicStrategies {

  @Override
  public List<MakePlay> getBestMove(ThreeTriosModelInterface model, PlayerColor player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and/or player cannot be null");
    }

    Map<MakePlay, Integer> moveVulnerability = new HashMap<>();
    RuleKeeper rules = new BasicThreeTriosGame(model);

    // For each possible move we can make
    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        if (model.getGrid().getCell(row, col).isEmpty()) {
          for (int cardIdx = 0; cardIdx < model.getPlayerHand(player).size(); cardIdx++) {
            MakePlay currentMove = new MakePlay(cardIdx, row, col);
            CustomCard card = model.getPlayerHand(player).get(cardIdx);

            if (rules.isLegalMove(model.getGrid().getCell(row, col), card)) {
              // Calculate how vulnerable this move would be
              int vulnerability = calculateMoveVulnerability(model, currentMove, player);
              moveVulnerability.put(currentMove, vulnerability);
            }
          }
        }
      }
    }

    // Find moves with minimum vulnerability
    int minVulnerability = moveVulnerability.values().stream()
            .min(Integer::compareTo).orElse(Integer.MAX_VALUE);
    List<MakePlay> bestMoves = new ArrayList<>();

    for (Map.Entry<MakePlay, Integer> entry : moveVulnerability.entrySet()) {
      if (entry.getValue() == minVulnerability) {
        bestMoves.add(entry.getKey());
      }
    }

    return Collections.singletonList(breakTies(bestMoves));
  }

  /**
   * Calculates how vulnerable a move would be to being flipped by opponent's cards.
   * Returns a count of how many of the opponent's cards could potentially flip this position.
   */
  private int calculateMoveVulnerability(ThreeTriosModelInterface model, 
                                       MakePlay ourMove, 
                                       PlayerColor player) {
    ThreeTriosModelInterface simulatedModel = model.copy();
    simulatedModel.playTurn(ourMove.getCardInHand(), ourMove.getRow(), ourMove.getCol());
    
    PlayerColor opponent = (player == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
    int vulnerabilityCount = 0;

    // Check each opponent card to see if it could flip our card from any adjacent position
    int oppCardinHandIdx = 0;
    for (CustomCard opponentCard : simulatedModel.getPlayerHand(opponent)) {
      // Check all adjacent positions
      int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}, {-1,-1}, {1,1}};
      for (int[] dir : directions) {
        int adjRow = ourMove.getRow() + dir[0];
        int adjCol = ourMove.getCol() + dir[1];
        
        // Check if adjacent position is valid and empty
        if (isValidPosition(simulatedModel, adjRow, adjCol) 
            && simulatedModel.getGrid().getCell(adjRow, adjCol).isEmpty()) {
          // If opponent could play this card here and flip our card, increment vulnerability
          if (couldFlipOurCard(simulatedModel, opponentCard, oppCardinHandIdx, adjRow, adjCol, ourMove)) {
            vulnerabilityCount++;
          }
        }
      }
      oppCardinHandIdx++;
    }

    return vulnerabilityCount;
  }

  private boolean isValidPosition(ThreeTriosModelInterface model, int row, int col) {
    return row >= 0 && row < model.getGrid().getRows() && col >= 0 && col < model.getGrid().getCols();
  }

  private boolean couldFlipOurCard(ThreeTriosModelInterface model, 
                                 CustomCard opponentCard, 
                                 int oppCardinHandIdx, 
                                 int adjRow, 
                                 int adjCol, 
                                 MakePlay ourMove) {
    // Create a copy of the model to simulate the opponent's move
    ThreeTriosModelInterface simulatedModel = model.copy();
    RuleKeeper rules = new BasicThreeTriosGame(simulatedModel);
    
    // Check if the opponent's move would be legal
    if (!rules.isLegalMove(simulatedModel.getGrid().getCell(adjRow, adjCol), opponentCard)) {
      return false;
    }
    
    // Get the state of our card before opponent's move
    CustomCard ourCard = simulatedModel.getGrid().getCell(ourMove.getRow(), ourMove.getCol()).getCard();
    PlayerColor ourColor = ourCard.getCurrentColor() == CardColor.RED ? PlayerColor.RED : PlayerColor.BLUE;
    // Simulate opponent's move
    simulatedModel.playTurn(oppCardinHandIdx, adjRow, adjCol);
    
    // Check if our card was flipped by comparing its color before and after
    CustomCard cardAfterMove = simulatedModel.getGrid().getCell(ourMove.getRow(), 
                                                              ourMove.getCol()).getCard();
    
    CardColor colorAfter = cardAfterMove.getCurrentColor();
    CardColor ourColorCard = null;

    if (ourColor == PlayerColor.RED) {
      return ourColorCard == CardColor.RED;
    } else if (ourColor == PlayerColor.BLUE) {
      return ourColorCard == CardColor.BLUE;
    }

    return !colorAfter.equals(ourColorCard);
  }
}
