import java.util.*;

class PairList {

  Vector x = new Vector();
  Vector y = new Vector();

  public void newPair(int x, int y) {
    this.x.add(new Integer(x));
    this.y.add(new Integer(y));
  }

  public String toString() {
    String rtn = "";
    for (int i = 0; i < x.size(); i++) {
      rtn += "(" + ((Integer) x.get(i)).intValue() + ", " +
        ((Integer) y.get(i)).intValue() + ")  ";
    }
    return rtn;
  }

  public static void main(String[] args) {
    PairList set = new PairList();

    for (int i = 0; i < args.length; i+=2) {
      set.newPair(Integer.parseInt(args[i]), Integer.parseInt(args[i+1]));
    }

    System.out.println(set);
  }
}