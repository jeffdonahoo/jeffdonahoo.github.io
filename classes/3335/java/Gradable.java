import java.util.Scanner;
import java.io.PrintStream;

abstract public class Gradable extends Book {

  protected int gradeLevel;  // New feature

  public Gradable(String title, String authors, int gradeLevel) {
    super(title, authors);   // Must be first
    this.gradeLevel = gradeLevel;
  }

  public boolean proofread() {
    System.out.println("Verifying appropriate for grade level " + gradeLevel + ".");
    super.proofread();    // Can be anywhere
    return true;
  }

  public int getGradeLevel() {
    return gradeLevel;
  }

  public Gradable(Scanner in, PrintStream out) {
    super(in, out);
    out.print("Grade Level> ");
    gradeLevel = in.nextInt();
    in.nextLine();  // Consume end of line
  }
}
