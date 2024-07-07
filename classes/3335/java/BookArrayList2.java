import java.util.*;
import java.awt.*;

public class BookArrayList2 {

  public static void main(String args[]) {

    Book wind = new Book("The Wind Done Gone", "Randall");

    BookList library = new BookList();
    library.add(wind);
    library.add(new Point(3,3));

    System.out.println("My library: ");
    for (int i = 0; i < library.size(); i++) {
      System.out.println(library.get(i));
      library.get(i).proofread();
    }
  }
}

class BookList {
  private ArrayList list = new ArrayList();

  public void add(Book b) {
    list.add(b);
  }

  public Book get(int index) {
    return (Book) list.get(index);
  }

  public int size() {
    return list.size();
  }
}
