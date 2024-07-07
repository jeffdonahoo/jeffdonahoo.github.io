import java.util.Scanner;
import java.io.PrintStream;

public class RomanceBook extends Book {

  public enum Rating {G, PG, PG13, R};
  private Rating rating;

  public RomanceBook(String title, String authors, Rating rating) {
    super(title, authors);
    this.rating = rating;
  }

  public boolean proofread() {
    System.out.println("Proofreading \"" + getTitle() + "\" is unnecessary");
    return true;
  }

  public Rating getRating() {
    return rating;
  }

  public String toString() {
    return getTitle() + " by " + getAuthors() + " is rated " + rating;
  }

  public static void main(String args[]) {
    Book b = new RomanceBook("You Only Live Once", "Carrington", Rating.PG);
    b.title = "C# Rocks";
    System.out.println(b);
  }

  public RomanceBook(Scanner in, PrintStream out) {
    super(in, out);
    out.print("Rating> ");
    rating = Rating.valueOf(Rating.class, in.nextLine());
  }
}
