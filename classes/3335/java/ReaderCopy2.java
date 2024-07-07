import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class ReaderCopy2 {
    public static void main(String[] args) throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("ReaderCopy.java"), "ASCII");
        var buf = new char[10];
        int charsRead;
        while ((charsRead = in.read(buf)) != -1) {
            System.out.print(Arrays.copyOf(buf, charsRead));
        }
    }
}
