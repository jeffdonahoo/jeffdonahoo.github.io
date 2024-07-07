import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class BruteForceEncodingTest {

  @Test
  public void testByteArrayToDecimalString() {

  }

  @Test
  public void testEncodeIntBigEndian() {
    byte[] dst;
    int offset;
    final byte BoBS = (byte) 0xff; // byte of bits (set)

    dst = new byte[6];
    Arrays.fill(dst, (byte) 0);
    offset = BruteForceEncoding.encodeIntBigEndian(dst, 0, 0, 4);
    assertEquals(4, offset);
    assertArrayEquals(new byte[] { 0, 0, 0, 0, 0, 0 }, dst);

    Arrays.fill(dst, (byte) 0);
    offset = BruteForceEncoding.encodeIntBigEndian(dst, -1, 0, 4);
    assertEquals(4, offset);
    assertArrayEquals(new byte[] { BoBS, BoBS, BoBS, BoBS, 0, 0 }, dst);

    Arrays.fill(dst, (byte) 0);
    offset = BruteForceEncoding.encodeIntBigEndian(dst, 0x123456, 0, 2);
    assertEquals(2, offset);
    assertArrayEquals(new byte[] { (byte) 0x34, (byte) 0x56, 0, 0, 0, 0 }, dst);
  }
}
