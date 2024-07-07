import java.util.Scanner;
import java.io.PrintStream;

public class TechBook extends Gradable {

  int difficultyLevel;

  public TechBook(String title, String authors, int gradeLevel, int difficultyLevel) {
    super(title, authors, gradeLevel);
    this.difficultyLevel = difficultyLevel;
  }

  public boolean proofread() {
    super.proofread();
    System.out.println("Proofreading \"" + getTitle() + "\" for technical errors.");
    return true;
  }

  public int getDifficultyLevel() {
    return difficultyLevel;
  }

  public String toString() {
    return super.toString() + " is on grade level " + gradeLevel +
      " and has a difficulty of " + difficultyLevel;
  }

  public TechBook(Scanner in, PrintStream out) {
    super(in, out);
    out.print("Difficulty Level> ");
    difficultyLevel = in.nextInt();
    in.nextLine();  // Consume end of line
  }
}
