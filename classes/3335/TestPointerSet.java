import junit.framework.*;

public class TestPointerSet extends TestCase {
  private PointerSet pointerSet = null;
  private PointerSet pointerSetCopy = null;
  private static char[] keys = {'c', 'a', 'r', 'z', 'f'};
  private static String[] values = {"CCCC", "AAA", "RRRRR", "ZZZ", "FFF"};

  public static void load(PointerSet p) {
    for (int i=0; i < keys.length; i++) {
      p.set(keys[i], values[i]);
    }
  }

  public static void unload(PointerSet p) {
    for (char c : p.getKeys()) {
      p.remove(c);
    }
  }

  protected void setUp() throws Exception {
    super.setUp();
    pointerSet = new PointerSet();
    pointerSetCopy = new PointerSet();
    load(pointerSetCopy);
  }

  protected void tearDown() throws Exception {
    pointerSet = null;
    super.tearDown();
  }

  public void testGet() {
    // Get from empty pointer set
    assertNull(pointerSet.get('a'));

    // Get existing item from pointer set with one element
    String s = "QQQQQ";
    pointerSet.set('q', s);
    assertEquals(pointerSet.get('q'), s);

    // Get non-existent item from pointer set with one element
    assertNull(pointerSet.get('a'));

    // Get non-existent node from pointer set with many elements
    load(pointerSet);
    assertNull(pointerSet.get('t'));

    // Get existing node from pointer set
    assertEquals(pointerSet.get(keys[3]), values[3]);

    // Get from empty pointer set
    unload(pointerSet);
    assertNull(pointerSet.get('a'));
  }

  public void testGetKeys() {
    // Get keys from empty  set
    assertEquals(pointerSet.getKeys().length, 0);

    // Load and get values
    load(pointerSet);
    assertTrue(TestHelper.ArraysEqual(pointerSet.getKeys(), keys));

    // Get values from emptied set
    unload(pointerSet);
    assertEquals(pointerSet.getKeys().length, 0);
  }


  public void testGetValues() {
    // Get values from empty  set
    assertEquals(pointerSet.getValues().length, 0);

    // Load and get values
    load (pointerSet);
    Object o[] = pointerSet.getValues();
    assertEquals(o.length, keys.length);

    // Get values from emptied set
    unload(pointerSet);
    assertEquals(pointerSet.getValues().length, 0);
  }

  public void testRemove() {
    // Attempt to remove from empty pointer set
    assertNull(pointerSet.remove('x'));

    // Attempt to remove non-existent mapping from populated pointer set
    load(pointerSet);
    assertNull(pointerSet.remove('x'));

    // Remove last key
    int size = keys.length;
    assertEquals(pointerSet.remove(keys[keys.length-1]), values[values.length-1]);
    size--;
    assertEquals(pointerSet.getValues().length, size);
    assertEquals(pointerSet.size(), size);
    assertNull(pointerSet.get(keys[keys.length-1]));

    // Remove first key
    assertEquals(pointerSet.remove(keys[0]), values[0]);
    size--;
    assertEquals(pointerSet.size(), size);
    assertEquals(pointerSet.getKeys().length, size);

    // Remove middle key
    assertEquals(pointerSet.remove(keys[2]), values[2]);
    size--;
    assertEquals(pointerSet.size(), size);
    assertEquals(pointerSet.getValues().length, size);
    assertEquals(pointerSet.get(keys[3]), values[3]);
  }

  public void testSet() {
    // Set with no prior association
    assertNull(pointerSet.set('x', "XXXXXX"));

    // Attempt to set value to null
    int size = pointerSet.size();
    assertNull(pointerSet.set('k', null));
    assertEquals(pointerSet.size(), size);

    load(pointerSet);

    // Set existing key
    assertEquals(pointerSet.set(keys[2], values[1]), values[2]);

    // Get new value associated with key
    assertEquals(pointerSet.get(keys[2]), values[1]);

    // Insert character mapping to empty string
    pointerSet.set('g', "");
    assertEquals(pointerSet.get('g'), "");

    // Create very large pointer set
    int i = 0;
    for (char c = Character.MIN_VALUE; c < Character.MIN_VALUE + 10000; c++, i++) {
      pointerSet.set(c, i);
    }

    i = 0;
    for (char c = Character.MIN_VALUE; c < Character.MIN_VALUE + 10000; c++, i++) {
      assertEquals(pointerSet.get(c), i);
    }
  }

  public void testSize() {
    // Test size() on empty pointer set
    assertEquals(pointerSet.size(), 0);

    // Test size() on loaded pointer set
    load(pointerSet);
    assertEquals(pointerSet.size(), keys.length);

    // Test size() after adding duplicate
    pointerSet.set(keys[1], values[2]);
    assertEquals(pointerSet.size(), keys.length);

    // Test size() after unload
    unload(pointerSet);
    assertEquals(pointerSet.size(), 0);
  }
}
