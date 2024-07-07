import java.io.*;

/**
   An easy interface to prompt for input to various
   output stream to read numbers and strings from
   various input streams.
*/
public class ConsoleStream {

  /**
     Example main program
   */
  public static void main(String args[]) throws FileNotFoundException {
    System.out.println("Console...");
    // Get/Put person information from/to console
    getPersonInfo(System.in, System.out);

    // Create person information file
    PrintStream out = new PrintStream(new FileOutputStream("info"));
    out.println("Ed");
    out.println("23");
    out.close();

    // Get/put person information from/to file/console
    System.out.println("File...");
    getPersonInfo(new FileInputStream("info"), System.out);
  }

  /**
     Print a prompt to the output stream but don't print a newline

     @param prompt the prompt string to display
     @param out stream on which to display the prompt
   */

  public static void printPrompt(String prompt, PrintStream out) {
    out.print(prompt);
    out.flush();
  }

  /**
     Read a string from the input stream. The string is
     terminated by a newline

     @param in stream from which to get input
     @return the input string (without the newline)
   */

  public static String readLine(InputStream in) {
    int ch;
    String r = "";
    boolean done = false;
    while (!done) {
      try {
        ch = in.read();
        if (ch < 0 || (char) ch == '\n') {
          done = true;
        } else if ( (char) ch != '\r') {
          r = r + (char) ch;
        }
      } catch (IOException e) {
        done = true;
      }
    }
    return r;
  }

  /**
     Read a string from the input stream. The string is
     terminated by a newline.

     @param in stream from which to get input
     @param out stream on which to display the prompt
     @param prompt the prompt string to display
     @return the input string (without the newline)
   */
  public static String readLine(String prompt, InputStream in, PrintStream out) {
    printPrompt(prompt, out);
    return readLine(in);
  }

  /**
     Read an integer from the input stream. The input is
     terminated by a newline.

     @param prompt the prompt string to display
     @param in stream from which to get input
     @param out stream on which to display the prompt
     @return the input value as an int
   */
  public static int readInt(String prompt, InputStream in, PrintStream out) {
    while (true) {
      printPrompt(prompt, out);
      try {
        return Integer.valueOf(readLine(in).trim()).intValue();
      } catch (NumberFormatException e) {
        out.println("Not an integer. Please try again!");
      }
    }
  }

  /**
     Read a floating point number from the input stream.
     The input is terminated by a newline.

      @param prompt the prompt string to display
      @param in stream from which to get input
      @param out stream on which to display the prompt
      @return the input value as a double
   */
  public static double readDouble(String prompt, InputStream in, PrintStream out) {
    while (true) {
      printPrompt(prompt, out);
      try {
        return Double.parseDouble(readLine(in).trim());
      } catch (NumberFormatException e) {
        out.println("Not a floating point number. Please try again!");
      }
    }
  }

  /**
     ConsoleStream constructor that should never be called because all of the
     methods are static.  That's why I made it private.
   */
  private ConsoleStream() {
  }

  /**
     Demonstrate passing streams around and using ConsoleStream static methods
     for I/O.

      @param in stream from which to get input
      @param out stream on which to display the prompt
   */
  private static void getPersonInfo(InputStream in, PrintStream out) {
    String name = ConsoleStream.readLine("Name> ", in, out);
    int age = ConsoleStream.readInt("Age> ", in, out);
    out.println(name + " is " + age + " years old.");
  }
}
