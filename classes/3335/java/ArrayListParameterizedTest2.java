import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ArrayListParameterizedTest2 {
  
  private static int BIGTESTSIZE = 500;
  
  private ArrayList<Integer> testList = new ArrayList<>();
  @Parameter(0)
  public Integer[] expList;
  @Parameter(1)
  public int expCount;
  
  @Parameters(name="Testing list {0} at # {index}")
  public static Collection<Object[]> data() {
    Integer[] big = IntStream.range(0, BIGTESTSIZE).boxed().toArray(Integer[]::new);
    return Arrays.asList(new Object[][] {
      {new Integer[] {}, 0},
      {new Integer[] {1}, 1},
      {new Integer[] {3, 2}, 2},
      {big, BIGTESTSIZE}
    });
  }

  @Test
  public void testAdd() {
    for (Integer i : expList) {
      testList.add(i);
    }
    assertEquals(expCount, testList.size());
    assertArrayEquals(expList, testList.toArray());
  }

  @Test
  public void testRemove() {
    if (expList.length == 0) {
      return;
    }

    for (Integer i : expList) {
      testList.add(i);
    }

    int i = 0;
    assertEquals(expList[i++], testList.remove(0));
    assertEquals(expCount - 1, testList.size());
    for (Integer listItem : testList) {
      assertEquals(expList[i++], listItem);
    }
  }
}