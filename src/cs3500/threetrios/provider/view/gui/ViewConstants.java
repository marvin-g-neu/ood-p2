package cs3500.threetrios.provider.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.cells.Directions;

/**
 * The Constants for the view. This stores everything from the ThreeTrioColors to colors to the font
 * of the text.
 */
public class ViewConstants {
  //the Color of the empty cell.
  private static final Color emptyCellColor = Color.YELLOW;
  //the Color or a hole cell.
  private static final Color holeCellColor = Color.GRAY;
  //the Color of light text to display on darker backgrounds.
  private static final Color lightTextColor = Color.WHITE;
  //the Color of dark text to display on lighter backgrounds.
  private static final Color darkTextColor = Color.BLACK;
  //the color of the lines on the board.
  private static final Color boardLineColor = Color.BLACK;
  // The Display values of all the ThreeTrioColor, uses these values as the background of each card.
  private static final Map<ThreeTrioColor, Color> colorDisplay =
          Map.<ThreeTrioColor, Color>ofEntries(
                  //the 2 base colors.
                  Map.entry(ThreeTrioColor.BLUE, new Color(30, 144, 255)),
                  Map.entry(ThreeTrioColor.RED, Color.RED),

                  //the other colors possible for the game.
                  Map.entry(ThreeTrioColor.APRICOT, new Color(251, 206, 177)),
                  Map.entry(ThreeTrioColor.CAESAR, new Color(83, 63, 61)),
                  Map.entry(ThreeTrioColor.DAWN, new Color(166, 162, 154)),
                  Map.entry(ThreeTrioColor.EEYORE, new Color(172, 189, 209)),
                  Map.entry(ThreeTrioColor.FAIRYLIGHT, new Color(223, 192, 225)),
                  Map.entry(ThreeTrioColor.GREEN, new Color(0, 200, 0)),
                  Map.entry(ThreeTrioColor.HELIOTROPE, new Color(223, 115, 255)),
                  Map.entry(ThreeTrioColor.ICE_BLUE, new Color(129, 209, 236)),
                  Map.entry(ThreeTrioColor.JADE, new Color(0, 168, 107)),
                  Map.entry(ThreeTrioColor.KETCHUP, new Color(181, 0, 0)),
                  Map.entry(ThreeTrioColor.LAPIS_LAZULI, new Color(38, 97, 156)),
                  Map.entry(ThreeTrioColor.MUSHY_PEAS, new Color(157, 180, 68)),
                  Map.entry(ThreeTrioColor.NEVA, new Color(204, 109, 109)),
                  Map.entry(ThreeTrioColor.OGRE, new Color(201, 210, 29)),
                  Map.entry(ThreeTrioColor.PURPLE, new Color(128, 0, 128)),
                  Map.entry(ThreeTrioColor.QUINACRIDONE_ROSE_VIOLET, new Color(142, 48, 75)),
                  Map.entry(ThreeTrioColor.SAFETY_ORANGE, new Color(255, 120, 0)),
                  Map.entry(ThreeTrioColor.TERRACOTTA, new Color(142, 80, 55)),
                  Map.entry(ThreeTrioColor.UBE, new Color(136, 120, 195)),
                  Map.entry(ThreeTrioColor.VAULT, new Color(86, 87, 64)),
                  Map.entry(ThreeTrioColor.WASABI, new Color(120, 138, 37)),
                  Map.entry(ThreeTrioColor.XYLOPHONE, new Color(255, 182, 193)),
                  Map.entry(ThreeTrioColor.YUCCA, new Color(104, 120, 107)),
                  Map.entry(ThreeTrioColor.ZUMTHOR, new Color(237, 246, 255)),
                  Map.entry(ThreeTrioColor.ATOMIC_TANGERINE, new Color(255, 153, 102)),
                  Map.entry(ThreeTrioColor.BAMBOO, new Color(105, 184, 41)),
                  Map.entry(ThreeTrioColor.CADILLAC, new Color(176, 76, 106)),
                  Map.entry(ThreeTrioColor.DARIO, new Color(0, 255, 156)),
                  Map.entry(ThreeTrioColor.EGGPLANT, new Color(97, 64, 81)),
                  Map.entry(ThreeTrioColor.FISH_N_CHIPS, new Color(220, 171, 75)),
                  Map.entry(ThreeTrioColor.GOSSAMER, new Color(6, 155, 129)),
                  Map.entry(ThreeTrioColor.HALF_SEA_FROG, new Color(239, 238, 231)),
                  Map.entry(ThreeTrioColor.IKO_IKO, new Color(179, 171, 110)),
                  Map.entry(ThreeTrioColor.JUPITER_PINK, new Color(170, 81, 125)),
                  Map.entry(ThreeTrioColor.KWILA, new Color(109, 51, 42)),
                  Map.entry(ThreeTrioColor.LAWN_GREEN, new Color(124, 252, 0)),
                  Map.entry(ThreeTrioColor.MEDIUM_SLATE_BLUE, new Color(123, 104, 238)),
                  Map.entry(ThreeTrioColor.NEON_CYAN, new Color(0, 255, 255)),
                  Map.entry(ThreeTrioColor.ONAHAU, new Color(205, 244, 255)),
                  Map.entry(ThreeTrioColor.PAPAYA, new Color(247, 161, 0)),
                  Map.entry(ThreeTrioColor.QUINACRIDONE_RED_VIOLET, new Color(116, 0, 85)),
                  Map.entry(ThreeTrioColor.ROSE_QUARTZ, new Color(170, 152, 169)),
                  Map.entry(ThreeTrioColor.SONIC_BOOM, new Color(37, 32, 66)),
                  Map.entry(ThreeTrioColor.TWITTER_BLUE, new Color(0, 172, 237)),
                  Map.entry(ThreeTrioColor.UNDERCURRENT, new Color(54, 92, 108)),
                  Map.entry(ThreeTrioColor.VIRIDIAN, new Color(64, 130, 109)),
                  Map.entry(ThreeTrioColor.WATERLOO_AND_CITY_LINE, new Color(147, 206, 186)),
                  Map.entry(ThreeTrioColor.XANADU, new Color(115, 134, 120)),
                  Map.entry(ThreeTrioColor.YABBADABBADOO, new Color(0, 139, 151)),
                  Map.entry(ThreeTrioColor.ZODIAC, new Color(65, 58, 93))
          );
  //the default font of the view.
  private static final Font font = new Font("Arial", Font.PLAIN, 30);
  private static final Font scoreboardFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);

  private static final int scoreboardCols = 2;
  /**
   * A Map which stores Directions to a relevant transformation of the center of the cell.
   */
  private static final Map<Directions, Point2D> positionOfText = Map.of(
          Directions.NORTH, new Point2D.Double(.40, .35),
          Directions.EAST, new Point2D.Double(.65, .60),
          Directions.WEST, new Point2D.Double(.15, .60),
          Directions.SOUTH, new Point2D.Double(.40, .85)
  );

  /**
   * Returns the background color of a given cell.
   *
   * @param cell the cell given to turn into a display. Where null represents playable space.
   * @return The background color of the cell to be displayed.
   */
  public static Color colorToDisplay(Cell cell) {
    if (cell == null) {
      //if the cell is empty.
      return emptyCellColor;
    } else if (cell.isaHole()) {
      //if the cell is a hole.
      return holeCellColor;
    } else {
      //if the cell is a player cell.
      return colorDisplay.get(cell.getColor());
    }
  }

  /**
   * Returns the line color of the board.
   *
   * @return the line color of the board.
   */
  public static Color getBoardLineColor() {
    return boardLineColor;
  }

  /**
   * Takes in a given direction and applies the relevant transformation to a new Point.
   *
   * @param dir the direction in which the Point is set.
   * @return A Point with the relevant transformation applied.
   * @throws IllegalArgumentException if dir is null.
   */
  public static Point2D directionToPositionOfText(Directions dir) {
    if (dir == null) {
      throw new IllegalArgumentException("dir is null");
    }
    return positionOfText.get(dir);
  }

  /**
   * Returns the font to use on the text on the board.
   */
  public static Font getFont() {
    return font;
  }

  /**
   * Returns the font to use on the scoreboard.
   */
  public static Font getScoreboardFont() {
    return scoreboardFont;
  }

  /**
   * Returns a color with the most contrasts from the display color of a ThreeTrioColor.
   *
   * @param color the threeTrioColor
   * @return the color that contrasts the ThreeTrioColor the most(white or black)
   * @throws IllegalArgumentException if color is null
   */
  public static Color getShowcaseColor(ThreeTrioColor color) {
    if (color == null) {
      throw new IllegalArgumentException("color is null");
    }

    if (getBrightness(colorDisplay.get(color)) <= 128) {
      return lightTextColor;
    } else {
      return darkTextColor;
    }
  }

  /**
   * Returns the brightness value of a color.
   *
   * @param color the color
   * @return the brightness value
   */
  private static int getBrightness(Color color) {
    return Collections.max(List.of(color.getRed(), color.getBlue(), color.getGreen()));
  }

  /**
   * Returns the amount of columns the scoreboard should have.
   *
   * @return scoreboardCols
   */
  public static int getScoreboardCols() {
    return scoreboardCols;
  }
}
