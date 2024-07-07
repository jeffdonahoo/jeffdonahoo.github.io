/**
   An easy interface to read numbers and strings from
   standard input

   @version 1.10 10 Mar 1997
   @author Cay Horstmann
*/
import java.io.*;

public class StreamConsole {
  /**
     Print a prompt on the specified output stream but don't print a newline

     @param prompt the prompt string to specified output stream
   */
  public static void printPrompt(String prompt, PrintStream out) {
    out.print(prompt + " ");
    out.flush();
  }

  /**
     Read a string from the console. The string is
     terminated by a newline

     @return the input string (without the newline)
   */
  public static String readLine(InputStream in) {
    int ch;
    String r = "";
    boolean done = false;
    while (!done) {
      try {
        ch = in.read();
        if ((ch < 0) || ((char)ch == '\n'))
          done = true;
        else if ((char)ch != '\r') // weird--it used to do \r\n translation
          r = r + (char) ch;
      } catch(java.io.IOException e) {
        done = true;
      }
    }

    return r;
  }

  /**
     Read a string from the console. The string is
     terminated by a newline

     @param prompt the prompt string to display
     @return the input string (without the newline)
   */
  public static String readLine(String prompt, InputStream in, PrintStream out) {
    printPrompt(prompt, out);
    return readLine(in);
  }

  /**
     Read an integer from the console. The input is
     terminated by a newline

     @param prompt the prompt string to display
     @return the input value as an int
     @exception NumberFormatException if bad input
    */
  public static int readInt(String prompt, InputStream in, PrintStream out) {
    while(true) {
      printPrompt(prompt, out);
      try {
        return Integer.valueOf(readLine(in).trim()).intValue();
      } catch(NumberFormatException e) {
        System.out.println("Not an integer. Please try again!");
      }
    }
  }

  /**
     Read a floating point number from the console.
     The input is terminated by a newline

      @param prompt the prompt string to display
      @return the input value as a double
      @exception NumberFormatException if bad input
    */

  public static double readDouble(String prompt, InputStream in, PrintStream out) {
    while(true) {
      printPrompt(prompt, out);
      try {
        return Double.parseDouble(readLine(in).trim());
      } catch(NumberFormatException e) {
        System.out.println("Not a floating point number. Please try again!");
      }
    }
  }
}
