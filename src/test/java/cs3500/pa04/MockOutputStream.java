package cs3500.pa04;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Implementation of OutputStream abstract class for testing
 */
public class MockOutputStream extends PrintStream {
  ArrayList<JsonNode> objects = new ArrayList<JsonNode>();
  /**
   * Creates a new print stream, without automatic line flushing, with the
   * specified OutputStream. Characters written to the stream are converted
   * to bytes using the platform's default character encoding.
   *
   * @param out The output stream to which values and objects will be
   *            printed
   * @see PrintWriter#PrintWriter(OutputStream)
   */
  public MockOutputStream(OutputStream out) {
    super(out);
  }


  /**
   * Add the printed item to a record of previously printed items
   *
   * @param o  The {@code Object} to be printed.
   */
  @Override
  public void println(Object o) {
    System.out.println("Success!!");
    try {
      objects.add((JsonNode) o);
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Should be printing JSON objects only.");
    }
  }
}
