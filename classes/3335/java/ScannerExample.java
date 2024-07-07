import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ScannerExample {

  public static void main(String[] args) throws FileNotFoundException {
    System.out.println("Console...");
    getPersonInfo(new Scanner(System.in), System.out);

    // Create person information file
    PrintStream out = new PrintStream(new FileOutputStream("info"));
    out.println("Ed");
    out.println("23");
    out.println("brown");
    out.close();

    // Get/put person information from/to file/console
    System.out.println("File...");
    getPersonInfo(new Scanner(new FileInputStream("info")), System.out);
  }

  private static void getPersonInfo(Scanner in, PrintStream out) {
    out.print("Name> "); // Read name
    String name = in.nextLine();
    out.print("Age> "); // Read age
    int age = in.nextInt();
    in.nextLine(); // Kill EOLN.  See what happens without this.
    out.print("Hair Color> "); // Read hair color
    String hairColor = in.nextLine();
    System.out.println(name + " is " + age + " years old with " + hairColor
	+ " hair.");
  }
}
