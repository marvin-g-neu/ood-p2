package cs3500.threetrios.controller;

/**
 * Uses a text file to help configure a game of Three Trios.
 */
public interface ConfigReader {
  /**
   * Uses a given file path to help set up a game of Three Trios.
   *
   * @param filePath the path of the config file
   * @throws IllegalArgumentException if the file is not found or the file is in the wrong format
   */
  void readConfigFile(String filePath);
}
