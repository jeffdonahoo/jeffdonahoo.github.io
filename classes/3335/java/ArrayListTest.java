import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a simple (incomplete) example of JUnit testing for
 * java.util.ArrayList to demonstrate the various testing methods. There are
 * several methods for testing in JUnit:
 * 
 * assertArrayEquals
 * assertEquals
 * assertTrue
 * assertFalse
 * assertNull
 * assertNotNull
 * assertSame
 * assertNotSame
 * fail
 */
public class ArrayListTest {
  private ArrayList<String> arrayList = null;

  @Before
  public void setUp() throws Exception {
    arrayList = new ArrayList<>();
    arrayList.add("mom");
    arrayList.add(null);
    arrayList.add("dad");
  }

  @After
  public void tearDown() throws Exception {
    arrayList = null;
  }

  @Test
  public void testGet() {
    assertEquals(arrayList.get(0), "mom");
    assertNull(arrayList.get(1));
    assertSame(arrayList.get(0), arrayList.get(0));

    // Test for expected exception
    try {
      arrayList.get(1000);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
    }

    // Test for unexpected exception
    arrayList.get(1000); // Exception will terminate test
  }

  public void testIsEmpty() {
    assertFalse(arrayList.isEmpty());
  }
}
