import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class DelimFramerTest {

  private final static String ENC = "US-ASCII";
  private final static String DLM = "\n";
  private static byte DLMENC;

  static {
    try {
      DLMENC = DLM.getBytes(ENC)[0];
    } catch (UnsupportedEncodingException e) {
    }
  }

  @Test
  public void testFrameMsg() throws IOException {
    Framer f = new DelimFramer(new ByteArrayInputStream(new byte[0]));
    String msg;
    byte[] res;

    // frame message
    msg = "12345";
    res = frame(f, msg);
    assertArrayEquals((msg + DLM).getBytes(ENC), res);

    // frame empty message
    res = frame(f, "");
    assertArrayEquals(new byte[] { DLMENC }, res);

    // message contains delimiter in middle
    try {
      msg = "12" + DLM + "34";
      frame(f, msg);
      fail("Expected IOException");
    } catch (IOException e) {
    }

    // message contains delimiter at end
    msg = "12345" + DLM;
    try {
      res = frame(f, msg);
      fail("Expected IOException");
    } catch (IOException e) {
    }

    // message contains delimiter at beginning
    msg =  DLM + "12345";
    try {
      res = frame(f, msg);
      fail("Expected IOException");
    } catch (IOException e) {
    }
    
    // message with standard delimiters (but now ours)
    msg = "12\r3  4\t5";
    res = frame(f, msg);
    assertArrayEquals((msg + DLM).getBytes(ENC), res);

    msg = "12345\r";
    res = frame(f, msg);
    assertArrayEquals((msg + DLM).getBytes(ENC), res);
  }

  private byte[] frame(Framer f, String msg) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    f.frameMsg(msg.getBytes(ENC), bout);
    bout.close();
    return bout.toByteArray();
  }

  @Test
  public void testNextMsg() throws UnsupportedEncodingException, IOException {
    Framer f;
    byte[] res;

    // basic single message
    f = new DelimFramer(getInputStream("123" + DLM));
    res = f.nextMsg();
    assertArrayEquals("123".getBytes(ENC), res);
    res = f.nextMsg();
    assertNull(res);

    // multiple messages
    f = new DelimFramer(getInputStream("123" + DLM + "456" + DLM));
    res = f.nextMsg();
    assertArrayEquals("123".getBytes(ENC), res);
    res = f.nextMsg();
    assertArrayEquals("456".getBytes(ENC), res);
    res = f.nextMsg();
    assertNull(res);
    res = f.nextMsg();
    assertNull(res);

    // message followed by data with EoS
    f = new DelimFramer(getInputStream("123" + DLM + "456"));
    res = f.nextMsg();
    assertArrayEquals("123".getBytes(ENC), res);
    try {
      res = f.nextMsg();
      fail("Expected EOFException");
    } catch (EOFException e) {
    }
    res = f.nextMsg(); // Kinda weird
    assertNull(res);

    // empty string message
    f = new DelimFramer(getInputStream("" + DLM));
    res = f.nextMsg();
    assertArrayEquals("".getBytes(ENC), res);
    res = f.nextMsg();
    assertNull(res);
    res = f.nextMsg();
    assertNull(res);

    // two empty string messages
    f = new DelimFramer(getInputStream("" + DLM + "" + DLM));
    res = f.nextMsg();
    assertArrayEquals("".getBytes(ENC), res);
    res = f.nextMsg();
    assertArrayEquals("".getBytes(ENC), res);
    res = f.nextMsg();
    assertNull(res);
    
    // empty string at EoS
    f = new DelimFramer(getInputStream(""));
    res = f.nextMsg();
    assertNull(res);
    
    // empty string message followed by empty string at EoS
    f = new DelimFramer(getInputStream("" + DLM + ""));
    res = f.nextMsg();
    assertArrayEquals("".getBytes(ENC), res);
    res = f.nextMsg();
    assertNull(res);
    res = f.nextMsg();
    assertNull(res);
  }

  private InputStream getInputStream(String s) throws UnsupportedEncodingException {
    return new ByteArrayInputStream(s.getBytes(ENC));
  }
}
