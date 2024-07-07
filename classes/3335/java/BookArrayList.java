import java.util.*;
import java.awt.*;

public class BookArrayList {

  public static void main(String args[]) {

    Book wind = new Book("The Wind Done Gone", "Randall");

    ArrayList library = new ArrayList();
    library.add(wind);
    library.add(new Point(3,3));
    library.add(new TechBook("Core Java", "Horstmann and Cornell", 8, 3));

    System.out.println("My library: ");
    for (Iterator i = library.iterator(); i.hasNext(); ) {
      Book book = (Book) i.next();
      System.out.println(book);
      book.proofread();
    }

    System.out.println("Average Book Grade Level: " + avgGradeLevel(library));
  }

  public static double avgGradeLevel(ArrayList l) {
    double sum = 0;
    int ct = 0;
    for (Iterator i = l.iterator(); i.hasNext(); ) {
      Object o = i.next();
      if (o instanceof Gradable) {
        sum += ((Gradable) o).getGradeLevel();
        ct++;
      }
    }
    return sum / ct;
  }
}