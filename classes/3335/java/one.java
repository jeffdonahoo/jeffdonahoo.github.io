import java.awt.*;

public class one {

  public static void modifyPoint(Point pt) {
    pt.setLocation(2,2);
    pt = new Point(3,3);
    pt.setLocation(4,4);
  }

  public static void main(String[] args) {
    Point pt = new Point(1,1);
    modifyPoint(pt);
    System.out.println(pt);
  }
}