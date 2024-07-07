import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CloseException {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(new FileInputStream("test") {
            @Override
            public void close() throws IOException {
                System.out.println("Close()");
                super.close();
            }
        })) {
            in.next();
            in.next();
        } catch (NoSuchElementException | FileNotFoundException e) {
            System.err
                    .println("Unable to find or open file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Bad stuff:  " + e.getMessage());
        }
    }
}

/*
Start with
Scanner in = new Scanner(new FileInputStream("test"));
in.next();
in.next();
in.close();

1.  Catch FileNotFound and handle with error message
2.  Catch NoSuchElementException and handle same as FileNotFound (so use | catch); other Exceptions handled differently
3.  Always want file to close.  Add finally.  Run.  NullPointer.  Show try with resource (kill finally and close()).
4.  Did close() really get called?  Try to make anonymous subclass of Scanner (fails cuz final).  Make anonymous subclass of FileInputStream.
5.  Run.  close() not called!  Why?  Because "test" doesn't exist so open never happens.
6.  Add "test" and show close() called.
7.  You can have multiple files open in try ()
8.  Only classes implementing AutoClosable work in try.
*/