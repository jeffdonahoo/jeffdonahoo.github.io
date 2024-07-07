import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BadParse {

    public static String getNextEntry(InputStream in) {
        Scanner sin = new Scanner(in);

        try {
            String ssn = sin.next();
            String name = sin.next();
            int age = sin.nextInt();

            return name + "(" + ssn + ") is " + age + " years old.";
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public static void putNextEntry(String ssn, String name, int age, OutputStream out) {
        PrintWriter pout = new PrintWriter(new OutputStreamWriter(out));

        pout.print(ssn + " ");
        pout.print(name + " ");
        pout.print(age + " ");
    }

    public static void main(String[] args) throws IOException {

        // Part I
        String input = "1234567890 John 20\n0987654321 Beth 18\n2468101214 Jack 19\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        String entry;
        while ((entry = getNextEntry(in)) != null) {
            System.out.println(entry);
        }

        // Part II
        OutputStream out = new FileOutputStream("data");

        putNextEntry("1234567890", "John", 20, out);
        putNextEntry("0987654321", "Beth", 18, out);
        putNextEntry("2468101214", "Jack", 19, out);

        out.close();
    }
}
