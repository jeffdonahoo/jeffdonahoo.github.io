import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArrayListBasicTest {

  private ArrayList<Integer> list = new ArrayList<>();

  @Test
  public void testAdd() {
    assertTrue(list.add(1));
    assertEquals(1, list.size());
    assertFalse(list.isEmpty());
  }

  @Test
  public void testAddIndex() {
    assertTrue(list.add(1));
    assertTrue(list.add(3));
    list.add(1, 2);
    assertEquals(3, list.size());
    assertArrayEquals(new Integer[] { 1, 2, 3 }, list.toArray());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddBadIndex() {
    list.add(1);
    list.add(3);
    list.add(4, 2);
  }

  @Test
  public void testRemove() {
    list.add(3);
    list.add(2);
    list.add(1);
    assertSame(Integer.valueOf(2), list.remove(1));
    assertThat(list, not(hasItem(2)));
  }

  @Test
  public void testSetCopy() {
    list.add(3);
    list.add(2);
    list.add(1);
    Set<Integer> set = new TreeSet<>(list);
    assertThat(set, hasItems(1, 2, 3));
  }

  @Before
  public void setUpTest() {
    // Executes before EACH test
  }

  @After
  public void tearDownTest() {
    // Executes after EACH test
  }

  @BeforeClass
  public static void setUpAllTests() {
    // Executes once before ALL tests
  }

  @AfterClass
  public static void tearDownAllTests() {
    // Executes once after ALL tests
  }
}