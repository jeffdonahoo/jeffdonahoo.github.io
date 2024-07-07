import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookMain {

  public static void main(String[] args) {

    Book wind = new Book("The Wind Done Gone", "Randall");

    Book java = new TechBook("Core Java", "Horstmann and Cornell", 15, 3);
    java.proofread();

    Book graded = new Gradable("See Spot Run", "Cat", 3);

    Gradable graded = java;

    List<Book> library = new ArrayList<>();
    library.add(wind);
    library.add(new Point(2,3));
    Object o = java;
    library.add(o);
    library.add(java);
    library.add(new RomanceBook("You Only Love Once", "Carrington", RomanceBook.Rating.PG));
    library.add(Book.createBook(new Scanner(System.in), System.out));

    System.out.println("My library: ");
    for (int i = 0; i < library.size(); i++) {
      System.out.println(library.get(i));
      library.get(i).proofread();
    }
    System.out.println("Title: " + java.title);
    System.out.println("Grade Level: " + java.gradeLevel);
    System.out.println("Difficulty Level: " + java.difficultyLevel);

    System.out.println("Average Book Grade Level: " + avgGradeLevel(library));
  }

  public static double avgGradeLevel(List<Book> library) {
    double sum = 0;
    int ct = 0;
    for (Book b : library) {
      if (b instanceof Gradable) {
        sum += b.getGradeLevel();
        ct++;
      }
    }
    return sum / ct;
  }
}
