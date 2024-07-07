import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WidgetMain {
  public static void main(String[] args) {
    Widget w1 = new Widget("Thing1", 2.00);
    Widget w2 = w1;
    w2.setName("New Thing");
    w2.setPrice(3);
    w2.getLastChecked().setTime(5000);
    w2.getBoundingBox().add(new Point(3, 4));
    System.out.println(w1);
    System.out.println(w2);
  }
}

class Widget {
  protected static int NEXTID = 0;
  protected int id;
  protected String name;
  protected double price;
  protected Date lastChecked;
  protected final Date created;
  protected List<Point> boundingBox = new ArrayList<>();
  
  public Widget(final String name, final double price) {
    this.id = NEXTID++;
    setName(name);
    setPrice(price);
    this.lastChecked = new Date();
    this.created = new Date();
    this.boundingBox.addAll(Arrays.asList(new Point[] {new Point(0,0), new Point (1,1)}));
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
    s.append(" created on " + created + " and last certified on " + lastChecked);
    s.append(" bounded by ");
    for (Point p : boundingBox) {
      s.append(" (" + p.getX() + ", " + p.getY() + ") ");
    }
    
    return s.toString();
  }
}
