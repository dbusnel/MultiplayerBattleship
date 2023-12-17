package cs3500.pa04;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Represents a problematic socket for testing
 */
public class MocketBad extends Socket {
  InputStream in;
  PrintStream out;

  /**
   * Construct a bad mocket
   *
   * @param in the InputStream to use
   * @param out the OutputStream to use
   */
  public MocketBad(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Get the input stream. Always throws an exception
   *
   * @return The InputStream
   * @throws IOException Example Socket IOException
   */
  @Override
  public InputStream getInputStream() throws IOException {
    throw new IOException("This mocket hates you.");
  }

}
