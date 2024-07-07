import java.util.*;

public class BookFactory {
  public static void main(String args[]) {
    Book newBook = Book.createBook(new Scanner(System.in), System.out);
    System.out.println(newBook);
  }
}
