import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * WarrantyTest inputs an item purchase date from the console and determine
 * if the 1 year warranty has expired.
 */
public class WarrantyTest {

    public static void main(String args[]) {
	// Enter the purchase date
	Date purchaseDate = null;
	Scanner scanner = new Scanner(System.in);
	while (purchaseDate == null) { // Loop until legal date is entered
	    try { // Attempt to get legal date
		System.out.print("Purchase Date (MM/DD/YYYY)> ");
		String dateEntry = scanner.nextLine();
		purchaseDate = DateFormat.getDateInstance(DateFormat.SHORT)
			.parse(dateEntry);
	    } catch (ParseException e) { // Illegal date
		System.out.println("Date parse error");
	    }
	}

	// Add 1 year
	Calendar expirationDate = new GregorianCalendar();
	expirationDate.setTime(purchaseDate);
	expirationDate
		.set(Calendar.YEAR, expirationDate.get(Calendar.YEAR) + 1);

	// Determine if warranty has expired
	if (expirationDate.before(Calendar.getInstance())) {
	    System.out.print("Too late!  Expired ");
	} else {
	    System.out.print("Got time!  Expires ");
	}
	System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(
		expirationDate.getTime()));
    }
}