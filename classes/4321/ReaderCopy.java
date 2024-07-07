import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class ReaderCopy {
    public static void main(String[] args) throws IOException {
        Reader in = new FileReader("ReaderCopy.java");
        var buf = new char[10];
        int charsRead;
        while ((charsRead = in.read(buf)) != -1) {
            System.out.print(Arrays.copyOf(buf, charsRead));
        }
    }
}
