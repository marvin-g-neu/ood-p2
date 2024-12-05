package cs3500.threetrios.provider.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cs3500.threetrios.provider.controller.gui.PlayerController;
import cs3500.threetrios.provider.controller.gui.TtGuiController;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrioModel;
import cs3500.threetrios.provider.model.ThreeTrioColor;

/**
 * View for the GUI implementation of the Three Trio Game.
 */
public class ThreeTrioGuiView extends JFrame implements TtGuiView {
  private final ReadOnlyThreeTrioModel model;
  private final HandPanel panelLeft;
  private final BoardPanel panelBoard;
  private final HandPanel panelRight;
  private final TtGuiController controller;
  private final ThreeTrioColor color;

  /**
   * Creates a GUI view for the ThreeTrioGame. Sets up the JFrame and the listeners required.
   * Makes it such that one hand can be seen on the left side of the board and another on the right.
   *
   * @param model      The read only model for the ThreeTrioGame being played
   * @param controller The GUI controller for the ThreeTrioGame.
   * @throws IllegalArgumentException if model,controller,or color is null
   */
  public ThreeTrioGuiView(ReadOnlyThreeTrioModel model,
                          TtGuiController controller, ThreeTrioColor color) {
    if (model == null || controller == null || color == null) {
      throw new IllegalArgumentException("model,controller,or color is null");
    }
    this.model = model;
    this.controller = controller;
    this.color = color;
    panelLeft = new HandPanel(model, this);
    panelBoard = new BoardPanel(model, this);
    panelRight = new HandPanel(model, this);
    this.setSize(1000, 600);
    this.setLayout(new BorderLayout());
    setUi();

    //Resizing window resizes all the panels
    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent evt) {
        setPanels();
      }
    });
  }

  /**
   * Sets the basic UI for the JFrame.
   */
  private void setUi() {
    setPanels();
    this.add(panelLeft, BorderLayout.WEST);
    this.add(panelBoard, BorderLayout.CENTER);
    this.add(panelRight, BorderLayout.EAST);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * Creates the Panels for the Board and Players on the Frame.
   */
  private void setPanels() {
    double width = this.getWidth();
    double height = this.getHeight();
    panelLeft.setPreferredSize(new Dimension((int) width / 8, (int) height));
    panelRight.setPreferredSize(new Dimension((int) width / 8, (int) height));
    panelBoard.setPreferredSize(new Dimension(6 * (int) width / 8, (int) height));
  }

  /**
   * Sets the title for the Frame based on the current game state.
   */
  private void makeTitle() {
    if (model.isGameOver()) {
      List<ThreeTrioColor> winner = model.getWinner();
      if (winner.size() > 1) {
        this.setTitle("Game Over. " + getWinnerString() + " are Tied.");
      } else {
        this.setTitle("Game Over. Winner: " + winner.get(0));
      }
    } else if (model.hasGameStarted()) {
      this.setTitle("Your Color: " + color + "\tCurrent Turn: " + controller.getCurrentTurn());
    } else {
      this.setTitle("Game has not started");
    }
  }

  /**
   * returns the string of winners.
   *
   * @return the string of winners.
   */
  private String getWinnerString() {
    List<ThreeTrioColor> winner = model.getWinner();
    String[] stringList = winner.stream().map(Enum::toString).toArray(String[]::new);
    return String.join(",", stringList);
  }

  /**
   * Sets the players of the left and right panels to colorLeft and colorRight.
   *
   * @param colorLeft  the left color.
   * @param colorRight the right color.
   * @throws IllegalArgumentException if colorLeft or colorRight are null.
   * @throws IllegalArgumentException if the color is not in the game.
   */
  @Override
  public void setPanelColors(ThreeTrioColor colorLeft, ThreeTrioColor colorRight) {
    if (colorLeft == null || colorRight == null) {
      throw new IllegalArgumentException("One of the colors is null");
    }
    if (!model.getAllPlayers().contains(colorLeft) || !model.getAllPlayers().contains(colorRight)) {
      throw new IllegalArgumentException("One of the colors is not playing");
    }
    panelLeft.changeDisplayColor(colorLeft);
    panelRight.changeDisplayColor(colorRight);
  }

  /**
   * Sets up the controller to handle click events in this view.
   *
   * @param listener the controller.
   */
  @Override
  public void addClickListener(PlayerController listener) {
    panelLeft.addClickListener(listener);
    panelRight.addClickListener(listener);
    panelBoard.addClickListener(listener);
  }

  /**
   * Refreshes the hand panels to reflect any changes in the game state.
   */
  @Override
  public void refreshPanels() {
    panelLeft.repaint();
    panelRight.repaint();
  }

  /**
   * Refreshes the board to reflect any changes in the game state.
   */
  @Override
  public void refreshBoard() {
    makeTitle();
    panelBoard.repaint();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Displays an error through a popup box using the string provided.
   *
   * @param message the message of the error.
   * @throws IllegalArgumentException if message is null
   */
  @Override
  public void throwError(String message) {
    if (message == null) {
      throw new IllegalArgumentException("message is null");
    }
    String errorMessage = "Color: " + color + ". " + message;
    JOptionPane.showMessageDialog(null, errorMessage, "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Sends a popup to the user with the given String.
   * The popup here is scrollable to ensure that all positions can be seen.
   */
  @Override
  public void showScoresPopup() {
    String titleString = "WINNER(s): " + getWinnerString() + "!!!";

    String scoreString = getScoresString(ViewConstants.getScoreboardCols());
    JTextArea textArea = new JTextArea(scoreString);
    textArea.setEditable(false);
    textArea.setFont(ViewConstants.getScoreboardFont());
    JScrollPane scrollPane = new JScrollPane(textArea);

    makePopup(scrollPane, titleString);
  }

  /**
   * creates a popup using another JFrame.
   *
   * @param scrollPane the scrollPane with all the scores in it
   * @param title      the title of the JFrame
   */
  private void makePopup(JScrollPane scrollPane, String title) {
    JFrame scorePopup = new JFrame();
    scorePopup.add(scrollPane);
    scorePopup.setTitle(title);

    //pack once so that the scrollPane knows its size
    scorePopup.pack();

    //sets the dimension of the scrollPane less than the JFrame size.
    scrollPane.setPreferredSize(getScorePopupDim(scrollPane));

    //pack again to resize the scrollPane
    scorePopup.pack();
    scorePopup.setLocationRelativeTo(null);
    scorePopup.setVisible(true);
  }

  /**
   * Gets the dimensions of the score panel. It makes sure that the scorePopup fits on the screen.
   *
   * @param scrollPane the scrollPane
   * @return the dimension of the score panel.
   */
  private Dimension getScorePopupDim(JScrollPane scrollPane) {
    int height = scrollPane.getHeight();
    int width = scrollPane.getWidth();
    if (height > getHeight()) {
      height = getHeight();
    }
    if (width > getWidth()) {
      width = getWidth();
    }
    return new Dimension(width, height);
  }

  /**
   * Gets the string of the score and formats such that there is numCols columns.
   * The String would look something like this:
   * "1. player1: 4    3. player3: 3 \n
   * 2. player2: 3    4. player4: 2".
   *
   * @param numCols the number of columns.
   * @return the string that is formatted into the above format.
   * @throws IllegalArgumentException if numCols <= 0
   */
  private String getScoresString(int numCols) {
    if (numCols <= 0) {
      throw new IllegalArgumentException("numCols can not be less than 1");
    }
    try {
      Map<ThreeTrioColor, Integer> scoresMap = model.getScores();
      List<String> stringList = scoreMapToStringList(scoresMap);
      stringList = getAllEvenPadding(stringList, 5);

      Appendable output = new StringBuilder();
      int numRows = stringList.size() / numCols;

      if (stringList.size() % numCols > 0) {
        numRows += 1;
      }
      for (int row = 0; row < numRows; row++) {
        for (int col = 0; col < numCols; col++) {
          //if the index is out of the stringList, then continue.
          if ((col * numRows + row) >= stringList.size()) {
            continue;
          }
          output.append(stringList.get(col * numRows + row));
        }
        output.append("\n");
      }

      return output.toString();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe.getMessage());
    }
  }

  /**
   * Turns the score map into a list of strings, where the strings are formatted like this:<br>
   * "&lt;placement(i.e. 1,2,3,...)&gt;. &lt;color&gt;:&lt;score&gt;"
   *
   * @param scoresMap the score map
   * @return the list of formatted strings.
   */
  private List<String> scoreMapToStringList(Map<ThreeTrioColor, Integer> scoresMap) {
    List<String> stringList = new ArrayList<>();
    List<ThreeTrioColor> colors = new ArrayList<>(scoresMap.keySet());
    Collections.reverse(colors);
    for (int idx = 0; idx < colors.size(); idx++) {
      ThreeTrioColor color = colors.get(idx);
      stringList.add((idx + 1) + ". " + color + ":" + scoresMap.get(color));
    }
    return stringList;
  }

  /**
   * pads all the strings so that they are the same length, extra padding can be added if wanted.
   *
   * @param list         the list to pad
   * @param extraPadding the amount of extra padding
   * @return the new list
   */
  private List<String> getAllEvenPadding(List<String> list, int extraPadding) {
    int stringLength = Collections.max(list,
            Comparator.comparingInt(String::length)).length() + extraPadding;
    List<String> newList = new ArrayList<>();
    for (String str : list) {
      newList.add(String.format("%-" + stringLength + "s", str));
    }
    return newList;
  }
}
