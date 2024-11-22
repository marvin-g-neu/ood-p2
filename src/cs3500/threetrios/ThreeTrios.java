package cs3500.threetrios;

import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.view.ThreeTriosGUIView;
public class ThreeTrios {
  public static void main(String[] args) {
    ThreeTriosModelInterface model = new ClassicalThreeTriosModel();
    model.startGame(
        new ThreeTriosBoard(
            new GridFileReader().readFile("docs/boards/boardWithNoUnreachableCardCells.config")),
        new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config"));
    new ThreeTriosGUIView(model);
  }
}
