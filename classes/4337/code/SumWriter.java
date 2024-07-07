import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Random;

public class SumWriter {

    public static void main(String[] args) throws Exception {
        Random randNoGen = new Random();
        FileLock lock;
        while (true) {
            RandomAccessFile file = new RandomAccessFile(SumConstants.FILENAME,
                    "rw");
            if (SumConstants.enableLock)
                lock = file.getChannel().lock();
            int left = SumConstants.SUMVALUE;
            while (left > 0) {
                int dec = randNoGen.nextInt(Math.min(10, left + 1));
                file.writeInt(dec);
                left -= dec;
            }
            file.setLength(file.getFilePointer()); // Truncate file
            if (SumConstants.enableLock)
                lock.release();
            file.close();
        }
    }
}
