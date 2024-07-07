import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;

public class DateTest {

  public static void main(String args[]) {
    // Getting the date
    Date targetDate = null;
    Scanner scanner = new Scanner(System.in);
    while (targetDate == null) {  // Loop until legal date is entered
      try {  // Attempt to get legal date
        System.out.print("Date (MM/DD/YYYY)> ");
        String dateEntry = scanner.nextLine();
        targetDate = DateFormat.getDateInstance(DateFormat.SHORT).parse(dateEntry);
      } catch (ParseException e) {  // Illegal date
        System.out.println("Date parse error");
      }
    }

    // Print the date
    if (targetDate.before(new Date())) {
      System.out.print("Too late!  Was due ");
    } else {
      System.out.print("Got time!  Due on ");
    }
    System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(targetDate));
  }
}
