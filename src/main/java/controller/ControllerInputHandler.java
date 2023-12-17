package controller;

import java.util.Scanner;

/**
 * Take and handle input from the user
 */
public class ControllerInputHandler {
  public Readable input;
  private final Scanner parser;

  /**
   * Create a UserInputHandler object
   *
   * @param input The source of input
   */
  public ControllerInputHandler(Readable input) {
    this.input = input;
    this.parser = new Scanner(input);
  }

  /**
   * Read the next token
   *
   * @return String
   */
  public String read() {
    return parser.next();
  }

  /**
   * Read the next line of the stream
   *
   * @return String
   */
  public String readLine() {
    return parser.nextLine();
  }
}
