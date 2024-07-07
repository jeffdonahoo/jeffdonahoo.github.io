import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

public class LengthFramerTest {

  @Test
  public void testFrameMsg() throws IOException {
    Framer f = new LengthFramer(new ByteArrayInputStream(new byte[0]));
    byte[] msg;
    byte[] res;

    // frame message
    msg = "12345".getBytes();
    res = frame(f, msg);
    assertSuccessfullyFramed(msg, res);

    // frame empty string
    msg = "".getBytes();
    res = frame(f, msg);
    assertSuccessfullyFramed(msg, res);

    // frame giant message
    msg = new byte[65534];
    Arrays.fill(msg, (byte) 6);
    res = frame(f, msg);
    assertSuccessfullyFramed(msg, res);

    // frame giant message
    msg = new byte[65536];
    Arrays.fill(msg, (byte) 6);
    try {
      res = frame(f, msg);
      fail("Expected IOException");
    } catch (IOException e) {
    }
  }

  private byte[] frame(Framer f, byte[] msg) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    f.frameMsg(msg, bout);
    bout.close();
    return bout.toByteArray();
  }

  private static int decodeIntBigEndian(byte[] val) {
    int rtn = 0;
    for (int i = 0; i < val.length; i++) {
      rtn |= (val[i] & 0xff) << ((val.length - i - 1) * 8);
    }
    return rtn;
  }

  private void assertSuccessfullyFramed(byte[] msg, byte[] framed) {
    // Verify contents
    assertArrayEquals(msg, Arrays.copyOfRange(framed, 2, msg.length + 2));
    // Verify prefix length
    assertEquals(msg.length, decodeIntBigEndian(Arrays.copyOfRange(framed, 0, 2)));
  }

  @Test
  public void testNextMsg() throws IOException {
    Framer f;
    byte[] res;
    byte[][] msgList;

    // fetch single message
    msgList = new byte[][] { "123".getBytes() };
    f = new LengthFramer(getInputStream(msgList));
    res = f.nextMsg();
    assertArrayEquals(msgList[0], res);
    res = f.nextMsg();
    assertNull(res);

    // fetch two messages
    msgList = new byte[][] { "123".getBytes(), "456".getBytes() };
    f = new LengthFramer(getInputStream(msgList));
    res = f.nextMsg();
    assertArrayEquals(msgList[0], res);
    res = f.nextMsg();
    assertArrayEquals(msgList[1], res);
    res = f.nextMsg();
    assertNull(res);
    res = f.nextMsg();
    assertNull(res);

    // fetch empty string
    msgList = new byte[][] { "".getBytes() };
    f = new LengthFramer(getInputStream(msgList));
    res = f.nextMsg();
    assertArrayEquals(msgList[0], res);
    res = f.nextMsg();
    assertNull(res);
    res = f.nextMsg();
    assertNull(res);

    // fetch message then empty string
    msgList = new byte[][] { "123".getBytes(), "".getBytes() };
    f = new LengthFramer(getInputStream(msgList));
    res = f.nextMsg();
    assertArrayEquals(msgList[0], res);
    res = f.nextMsg();
    assertArrayEquals(msgList[1], res);
    res = f.nextMsg();
    assertNull(res);
    res = f.nextMsg();
    assertNull(res);

    // fetch giant message
    msgList = new byte[1][];
    msgList[0] = new byte[65532];
    Arrays.fill(msgList[0], (byte) 6);
    f = new LengthFramer(getInputStream(msgList));
    res = f.nextMsg();
    assertArrayEquals(msgList[0], res);
    
    // fetch from empty stream
    f = new LengthFramer(new ByteArrayInputStream( new byte[] { } ));
    res = f.nextMsg();
    assertNull(res);
    
    // fetch from stream with improper prefix
    f = new LengthFramer(new ByteArrayInputStream( new byte[] { 5 } ));
    res = f.nextMsg();
    assertNull(res);  // I don't like this
    
    // fetch from stream with incorrect prefix
    f = new LengthFramer(new ByteArrayInputStream( new byte[] { 0, 5, 1, 2 } ));
    try {
      f.nextMsg();
      fail("Expected IOExpection");
    } catch (IOException e) {}
  }

  private InputStream getInputStream(byte[][] msgList) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    byte[] prefix = new byte[2];
    for (byte[] msg : msgList) {
      encodeIntBigEndian(prefix, msg.length, 0, 2);
      bout.write(prefix);
      bout.write(msg);
    }
    return new ByteArrayInputStream(bout.toByteArray());
  }

  public static void encodeIntBigEndian(byte[] dst, int val, int offset, int size) {
    for (int i = 0; i < size; i++) {
      dst[offset++] = (byte) (val >> ((size - i - 1) * 8));
    }
  }
}
