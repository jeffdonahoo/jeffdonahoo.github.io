import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WidgetCloneMain {
  public static void main(String[] args) {
    Widget2 w1 = new Widget2("Thing1", 2.00);
    Widget2 w2 = w1.clone();
    w2.setName("New Thing");
    w2.setPrice(3);
    w2.getLastChecked().setTime(5000);
    w2.getBoundingBox().get(1).setLocation(2, 2);
    System.out.println(w1);
    System.out.println(w2);
  }
}

class Widget2 implements Cloneable {
  protected static int NEXTID = 0;

  protected int id;
  protected String name;
  protected double price;
  protected Date lastChecked;
  protected Date created;
  protected List<Point> boundingBox = new ArrayList<>();

  public Widget2(final String name, final double price) {
    id = NEXTID++;
    setName(name);
    setPrice(price);
    lastChecked = new Date();
    created = new Date();
    boundingBox.addAll(
        Arrays.asList(new Point[] { new Point(0, 0), new Point(1, 1) }));
  }

  @SuppressWarnings("unchecked")
  @Override
  public Widget2 clone() {
    try {
      Widget2 w = (Widget2) super.clone();
      w.id = NEXTID++;
      w.lastChecked = (Date) lastChecked.clone();
      w.created = new Date();
      // Option 1
      w.boundingBox = (List<Point>) ((ArrayList<Point>) boundingBox).clone();
      // Option 2
      // w.boundingBox = new ArrayList<Point>(this.boundingBox);
      // Option 3
      // w.boundingBox = new ArrayList<>(this.boundingBox.size());
      // for (Point p : this.boundingBox) {
      // w.boundingBox.add((Point) p.clone());
      // }
      return w;
    } catch (CloneNotSupportedException e) {
      // Cannot happen
      throw new AssertionError("Impossible exception thrown", e);
    }
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPrice(final double price) {
    this.price = price;
  }

  public Date getLastChecked() {
    return lastChecked;
  }

  public List<Point> getBoundingBox() {
    return boundingBox;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("");
    s.append(id + ": " + name + " for $" + price);
    s.append(
        " created on " + created + " and last certified on " + lastChecked);
    s.append(" bounded by");
    for (Point p : boundingBox) {
      s.append(" (" + p.getX() + ", " + p.getY() + ") ");
    }

    return s.toString();
  }
}
