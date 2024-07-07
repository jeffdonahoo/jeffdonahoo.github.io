import java.util.ArrayList;
import java.util.List;

public class IntSeries {
  private List<Integer> l = new ArrayList<>();

  public IntSeries ins(int s) {
    l.add(s);
    return this;
  }
}
