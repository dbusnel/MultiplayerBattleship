package controller;

import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for handling input
 */
class ControllerInputHandlerTest {

  StringReader readStrings;
  ControllerInputHandler input;


  /**
   * initialize examples
   */
  @BeforeEach
  void initExamples() {
    this.readStrings = new StringReader("Test input.\nTwo lines");
    this.input = new ControllerInputHandler(readStrings);
  }

  /**
   * Test reading line from input String
   */
  @Test
  void readLine() {
    String test = input.readLine();
    assertEquals("Test input.", test);
    test = input.read();
    assertEquals("Two", test);
    test = input.read();
    assertEquals("lines", test);
  }
}