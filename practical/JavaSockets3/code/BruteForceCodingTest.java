import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class BruteForceCodingTest {

  static final byte BoBS = (byte) 0xff;  // byte of bits (all set)
  
  @Test
  public void testByteArrayToDecimalString() {
    assertEquals("5 7 9 ", BruteForceCoding.byteArrayToDecimalString(new byte[] {5, 7, 9}));
    assertEquals("", BruteForceCoding.byteArrayToDecimalString(new byte[] {}));
    assertEquals("255 ", BruteForceCoding.byteArrayToDecimalString(new byte[] {(byte) 0xFF}));
  }

  @Test
  public void testEncodeIntBigEndian() {
    byte[] dst;
    int offset;
    
    // encode at the beginning
    dst = new byte[6];
    Arrays.fill(dst, (byte) 6);
    offset = BruteForceCoding.encodeIntBigEndian(dst, 1, 0, 4);
    assertEquals(4, offset);
    assertArrayEquals(new byte[] {0, 0, 0, 1, 6, 6}, dst);

    // encode in the middle
    Arrays.fill(dst, (byte) 6);
    offset = BruteForceCoding.encodeIntBigEndian(dst, 2, 1, 4);
    assertEquals(5, offset);
    assertArrayEquals(new byte[] {6, 0, 0, 0, 2, 6}, dst);
    
    // don't test if dst == null or offset+size > dst.length
    
    // large negative number
    Arrays.fill(dst, (byte) 0);
    offset = BruteForceCoding.encodeIntBigEndian(dst, -1, 0, 4);
    assertEquals(4, offset);
    assertArrayEquals(new byte[] {BoBS, BoBS, BoBS, BoBS, 0, 0}, dst);

    // number too large (truncation expected)
    Arrays.fill(dst, (byte) 0);
    offset = BruteForceCoding.encodeIntBigEndian(dst, 0x123456, 0, 2);
    assertEquals(2, offset);
    assertArrayEquals(new byte[] {(byte) 0x34, (byte) 0x56, 0, 0, 0, 0}, dst);
  }
  
  @Test
  public void testDecodeIntBigEndian() {
  //decodeIntBigEndian(byte[] val, int offset, int size)
    byte[] buf;
    
    buf = new byte[] {BoBS, BoBS, BoBS, BoBS, BoBS, BoBS};
    assertEquals((int) BruteForceCoding.decodeIntBigEndian(buf, 0, 4), -1);
    assertEquals((int) BruteForceCoding.decodeIntBigEndian(buf, 1, 4), -1);
    assertEquals(BruteForceCoding.decodeIntBigEndian(buf, 0, 6), 281474976710655L);
  }
}
