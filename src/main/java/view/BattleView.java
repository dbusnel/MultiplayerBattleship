package view;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Represents Console visual aspect of BattleServo
 */
public class BattleView {

  public Appendable output;

  /**
   * By default, initialize output to console
   */
  public BattleView() {
    output = new PrintStream(System.out);
  }

  /**
   * Set the output mode to the desired Appendable
   */
  public BattleView(Appendable mode) {
    output = mode;
  }

  /**
   * Log a given String to the output
   *
   * @param s The string to append to output stream
   */
  public void log(String s) {
    try {
      this.output.append(s);
    } catch (IOException e) {
      throw new RuntimeException("Could not output to log.");
    }
  }
}
