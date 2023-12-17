package cs3500.pa04;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Represents a functional socket that is not connected to any server
 */
public class MocketGood extends Socket {
  InputStream in;
  PrintStream out;

  /**
   * Construct the good mocket
   *
   * @param in the InputStream to use
   * @param out the OutputStream to use
   */
  public MocketGood(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Get the InputStream used by the mocket
   *
   * @return InputStream
   */
  @Override
  public InputStream getInputStream() {
    return this.in;
  }

  /**
   * Get the OutputStream used by the mocket
   *
   * @return OutputStream
   */
  @Override
  public PrintStream getOutputStream() {
    return this.out;
  }

}
