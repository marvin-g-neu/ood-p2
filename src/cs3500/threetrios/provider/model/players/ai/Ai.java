package cs3500.threetrios.provider.model.players.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.provider.model.ReadOnlyThreeTrioModel;
import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.players.Player;
import cs3500.threetrios.provider.model.players.ai.strategies.Strategy;

/**
 * An abstract class for AI, to make an AI just extend this class and
 * add all the rules into the chooseMove method.
 */
public abstract class Ai implements Player, Strategy {
  private final Player player;
  private final ReadOnlyThreeTrioModel model;

  /**
   * Creates a playable AI object.
   *
   * @param player the player.
   * @param model  the ReadOnlyThreeTrioModel.
   */
  public Ai(Player player, ReadOnlyThreeTrioModel model) {
    this.player = player;
    this.model = model;
  }


  /**
   * Plays move as the AI Player for the given color into the given model.
   */
  private void playMove() {
    AiParams moves = chooseMoveFromList(chooseMove(getAllPossibleMoves()));
    player.playCell(moves.getIdx(), (int) moves.getPos().getX(), (int) moves.getPos().getY());
  }

  /**
   * Gets all the possible moves for the AI to play.
   *
   * @return all the possible moves.
   */
  private List<AiParams> getAllPossibleMoves() {
    List<AiParams> allPossible = new ArrayList<>();
    Cell[][] board = model.getBoard();
    List<Cell> hand = model.getHand(getPlayerColor());
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        //if this square is not an empty slot, then continue to the next (x,y)
        if (board[y][x] != null) {
          continue;
        }
        for (int idx = 0; idx < hand.size(); idx++) {
          allPossible.add(new AiParams(new Point(x, y), idx));
        }
      }
    }
    return allPossible;
  }

  /**
   * This chooses the move to play from a list of move determined by the tiebreaker.
   *
   * @param moves the list of moves to choose from.
   * @return the moves that wins from the tiebreak method.
   * @throws IllegalArgumentException if moves is empty.
   */
  private AiParams chooseMoveFromList(List<AiParams> moves) {
    checkList(moves);
    if (moves.isEmpty()) {
      throw new IllegalArgumentException("moves is empty");
    }
    AiParams best = null;
    for (AiParams param : moves) {
      if (best == null) {
        best = param;

      } else {
        best = tieBreaker(best, param);
      }
    }
    return best;
  }

  /**
   * The tiebreaker to determine which move is better to play.
   *
   * @param param1 the first move.
   * @param param2 the second move.
   * @return the move determined as better from the two AIParams.
   */
  private AiParams tieBreaker(AiParams param1, AiParams param2) {
    if (param1.getPos().getY() < param2.getPos().getY()) {
      return param1;
    }
    if (param1.getPos().getY() > param2.getPos().getY()) {
      return param2;
    }
    //param1.y == param2.y

    if (param1.getPos().getX() < param2.getPos().getX()) {
      return param1;
    }
    if (param1.getPos().getX() > param2.getPos().getX()) {
      return param2;
    }
    //param1.x == param2.x


    if (param1.getIdx() < param2.getIdx()) {
      return param1;
    }
    if (param1.getIdx() > param2.getIdx()) {
      return param2;
    }

    //param1 == param2
    return param1;
  }

  /**
   * Ignores the variables inputted, the AI then decides to play the best move
   * based on the strategy it is employing.
   *
   * @param handIdx ignored
   * @param boardX  ignored
   * @param boardY  ignored
   */
  @Override
  public void playCell(int handIdx, int boardX, int boardY) {
    playMove();
  }


  /**
   * Returns a copy of the hand of the player.
   * Mutating these cells does not change the actual cells.
   *
   * @return the hand of the player.
   * @throws IllegalArgumentException if hand is null.
   */
  @Override
  public List<Cell> getHand() {
    if (player.getHand() == null) {
      throw new IllegalArgumentException("hand is null");
    }
    return player.getHand();
  }

  /**
   * Gets the board from the model.
   *
   * @return the board from the model.
   */
  protected Cell[][] getBoard() {
    checkBoard(model.getBoard());
    return model.getBoard();
  }

  /**
   * Check the board to make sure there is nothing illegal.
   *
   * @param board the board of the game.
   * @throws IllegalArgumentException if board is null.
   * @throws IllegalArgumentException if one of the arrays in board is null.
   * @throws IllegalArgumentException if the rows of the array are different sizes.
   */
  private void checkBoard(Cell[][] board) {
    if (board == null) {
      throw new IllegalArgumentException("board is null.");
    }

    for (int y = 0; y < board.length; y++) {

      if (board[y] == null) {
        throw new IllegalArgumentException("board[" + y + "]is null");
      }
      if (board[0].length != board[y].length) {
        throw new IllegalArgumentException("The rows of the array are different sizes.");
      }
    }
  }

  /**
   * Gives the color associated with the Player for setting their Cells to.
   *
   * @return The Color enum related to the player.
   */
  @Override
  public ThreeTrioColor getPlayerColor() {
    return player.getPlayerColor();
  }


  /**
   * Checks to see if the list provided is legal.
   *
   * @param moves the list of moves that the AI could play.
   * @throws IllegalArgumentException if moves is null.
   * @throws IllegalArgumentException if one of the elements in moves is null.
   * @throws IllegalArgumentException if one of the positions in the element is null.
   */
  protected void checkList(List<AiParams> moves) {
    if (moves == null) {
      throw new IllegalArgumentException("moves is null");
    }
    for (AiParams move : moves) {
      if (move == null) {
        throw new IllegalArgumentException("One of the moves are null");
      }
      if (move.getPos() == null) {
        throw new IllegalArgumentException("One of the positions in the moves is null");
      }
    }
  }
}
