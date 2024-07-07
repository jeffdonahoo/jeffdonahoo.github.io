import java.util.*;
import java.text.*;

public class MissedBirthday {

  public static void main(String args[]) {
    // Prepare to parse input from console
    Scanner in = new Scanner(System.in);

    // Enter a date
    Calendar DOB = null;
    boolean dateCorrect = false;
    while (!dateCorrect) {
      System.out.print("Date of birth (MM/DD/YYYY)> ");
      String date = in.nextLine();
      try {
        DOB = new GregorianCalendar();
        DOB.setTime(DateFormat.getDateInstance(DateFormat.SHORT).parse(date));
        dateCorrect = true;
      } catch (ParseException e) {
        System.out.println("Date parse error");
      }
    }

    // Create a calendar with today's date
    Calendar today = new GregorianCalendar();

    // Find this year's birthday
    Calendar thisBDay = (Calendar) DOB.clone();  // Copy actual birthday
    thisBDay.set(Calendar.YEAR, today.get(Calendar.YEAR));  // Set to this year

    // Did I miss the birthday?
    boolean missedBDay = today.after(thisBDay);

    System.out.println("You have" + (missedBDay ? "" : " not") + 
      " missed your friend's birthday this year on " + formatDate(thisBDay));
  }

  public static String formatDate(Calendar date) {
    return DateFormat.getDateInstance(DateFormat.SHORT).format(date.getTime());  
  }
}
