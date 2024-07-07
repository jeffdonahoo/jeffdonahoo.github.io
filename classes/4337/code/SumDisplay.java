import java.io.EOFException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class SumDisplay {

    public static void main(String[] args) throws Exception {
        FileLock lock;
        while (true) {
            RandomAccessFile file = new RandomAccessFile(SumConstants.FILENAME,
                    "r");
            if (SumConstants.enableLock)
                lock = file.getChannel().lock(0, Long.MAX_VALUE, true);
            int sum = 0;
            try {
                while (true) {
                    sum += file.readInt();
                }
            } catch (EOFException e) {
            }
            if (sum != SumConstants.SUMVALUE) {
                System.err.println("BAD SUM:  Expected "
                        + SumConstants.SUMVALUE + "; Received " + sum);
                System.exit(1);
            } else {
                System.out.println("Sum checks out");
            }
            if (SumConstants.enableLock)
                lock.release();
            file.close();
        }
    }
}
