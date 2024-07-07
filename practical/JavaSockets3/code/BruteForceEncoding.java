public class BruteForceEncoding {

  private static byte byteVal = 101; // one hundred and one
  private static short shortVal = 10001; // ten thousand and one
  private static int intVal = 100000001; // one hundred million and one
  private static long longVal = 1000000000001L;// one trillion and one

  private final static int BSIZE = Byte.SIZE / Byte.SIZE;
  private final static int SSIZE = Short.SIZE / Byte.SIZE;
  private final static int ISIZE = Integer.SIZE / Byte.SIZE;
  private final static int LSIZE = Long.SIZE / Byte.SIZE;

  private final static int BYTEMASK = 0xFF; // 8 bits

  public static String byteArrayToDecimalString(byte[] bArray) {
    StringBuilder rtn = new StringBuilder();
    for (byte b : bArray) {
      rtn.append(b & BYTEMASK).append(" ");
    }
    return rtn.toString();
  }

  // Warning:  Untested preconditions (e.g., 0 <= size <= 8)
  public static int encodeIntBigEndian(byte[] dst, long val, int offset, int size) {
    for (int i = 0; i < size; i++) {
      dst[offset++] = (byte) (val >> ((size - i - 1) * Byte.SIZE));
    }
    return offset;
  }

  public static void main(String[] args) {
    byte[] message = new byte[BSIZE + SSIZE + ISIZE + LSIZE];
    // encode the byte
    int offset = encodeIntBigEndian(message, byteVal, 0, BSIZE);
    offset = encodeIntBigEndian(message, shortVal, offset, SSIZE);
    offset = encodeIntBigEndian(message, intVal, offset, ISIZE);
    encodeIntBigEndian(message, longVal, offset, LSIZE);
    System.out.println("Encoded message: " + byteArrayToDecimalString(message));
  }
}
