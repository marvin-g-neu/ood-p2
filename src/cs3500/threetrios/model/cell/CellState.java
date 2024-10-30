package cs3500.threetrios.model.cell;

/**
 * Represents the state of a cell in the Three Trios game grid.
 */
public enum CellState {
  RED("RED"),
  BLUE("BLUE"),
  HOLE("HOLE"),
  EMPTY("EMPTY");

  /**
   * The name of the cell state.
   */
  private final String name;

  /**
   * Constructs a new CellState with the given name.
   *
   * @param name the name of the cell state
   */
  CellState(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the cell state.
   *
   * @return the name of the cell state
   */
  public String getName() {
    return this.name;
  }
}
