import java.io.*;

public class BookMainStream {

  public static void main(String args[]) {

    Book b;
    while ((b = createBook(System.in, System.out)) != null) {
      System.out.println(b);
      System.out.println();
    }
  }

  public static Book createBook(InputStream in, PrintStream out) {
    out.println("1-Book");
    out.println("2-Technical Book");
    out.println("3-Romance Book");
    out.println("Other-Quit");
    int choice = ConsoleStream.readInt("New Book> ", in, out);
    switch (choice) {
      case 1:
        return new Book(in, out);
      case 2:
        return new TechBook(in, out);
      case 3:
        return new RomanceBook(in, out);
    }
    return null;
  }
}
