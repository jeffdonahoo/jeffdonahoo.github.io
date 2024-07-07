import java.util.Scanner;
import java.io.PrintStream;

public class Book {

  private String title;
  private String authors;

  public Book(String title, String authors) {
    this.title = title;
    this.authors = authors;
  }

  public boolean proofread() {
    System.out.println("Proofreading \"" + title + "\" for grammar and spelling errors.");
    return true;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthors() {
    return authors;
  }

  // public String toString() {
  //   return title + " by " + authors;
  // }

  public static void main(String args[]) {
    Book b = new Book("Core Java", "Horstmann");
    b.title = "C# Rocks";
    System.out.println(b);
  }

  public Book(Scanner in, PrintStream out) {
    out.print("Title> ");
    title = in.nextLine();
    out.print("Author(s)> ");
    authors = in.nextLine();
  }

  public static Book createBook(Scanner in, PrintStream out) {
    out.println("1-Book");
    out.println("2-Technical Book");
    out.println("3-Romance Book");
    out.print("Book Type> ");
    int choice = in.nextInt();
    in.nextLine();  // Consume end of line
    switch (choice) {
      case 1:
        return new Book(in, out);
      case 2:
        return new TechBook(in, out);
      case 3:
        return new RomanceBook(in, out);
      default:
        out.println("Bad choice");
    }
    return null;
  }
}
