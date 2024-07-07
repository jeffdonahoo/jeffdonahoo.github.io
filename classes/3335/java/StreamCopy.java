import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class StreamCopy {

    public static void go(InputStream src, OutputStream dst) throws IOException {
        byte[] buffer = new byte[1024];

        while (src.read(buffer) != -1) {
            dst.write(buffer);
        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Parameter(s): <input file> <output file>");
            System.exit(1);
        }

        go(new FileInputStream(args[0]), new FileOutputStream(args[1]));
    }
}
