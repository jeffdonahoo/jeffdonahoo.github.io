import junit.framework.TestCase;
import java.util.ArrayList;

// This is a simple example of JUnit testing for java.util.ArrayList to demonstrate
// the various testing methods.  There are several methods for testing in JUnit:
//
// assertEquals
// assertTrue
// assertFalse
// assertNull
// assertNotNull
// assertSame
// assertNotSame
// fail

public class TestArrayList extends TestCase {
  private ArrayList arrayList = null;

  protected void setUp() throws Exception {
    super.setUp();
    /**@todo verify the constructors*/
    arrayList = new ArrayList();
    arrayList.add("mom");
    arrayList.add(null);
    arrayList.add("dad");
  }

  protected void tearDown() throws Exception {
    arrayList = null;
    super.tearDown();
  }

  public void testGet() {
    assertEquals(arrayList.get(0), "mom");
    assertNull(arrayList.get(1));
    assertSame(arrayList.get(0), arrayList.get(0));

    // Test for expected exception
    try {
      arrayList.get(1000);
      fail("Expected IndexOutOfBoundsException");
    }
    catch (Exception e) {}

    // Test for unexpected exception
    arrayList.get(1000);  // Exception will terminate test
  }

  public void testIsEmpty() {
    assertFalse(arrayList.isEmpty());
  }
}
