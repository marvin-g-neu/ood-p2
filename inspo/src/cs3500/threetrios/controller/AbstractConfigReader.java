package cs3500.threetrios.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.threetrios.controller.ConfigReader;

/**
 * Abstract implementation of ConfigReader.
 */
abstract class AbstractConfigReader implements ConfigReader {

  /**
   * Returns the lines of text from a file with the given file path. The lines are returned as a
   * List, where each line is an element in the list.
   *
   * @param filePath the file's path
   * @return the text from the file
   * @throws IllegalArgumentException if the file cannot be found or the file has no text in it
   */
  protected List<String> getConfigFileText(String filePath) {
    List<String> lines = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(new File(filePath));
      while (scanner.hasNext()) {
        lines.add(scanner.nextLine());
      }
      scanner.close();
    } catch (FileNotFoundException exception) {
      throw new IllegalArgumentException("File not found");
    }
    if (lines.isEmpty()) {
      throw new IllegalArgumentException("No text in file");
    }
    return lines;
  }
}
