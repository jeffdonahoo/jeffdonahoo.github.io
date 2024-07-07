import java.awt.*;

class Player implements Cloneable {
  public Point position;
  public String name;

  public Player(String name, int x, int y) {
    position = new Point(x, y);
    this.name = name;
  }

  public String toString() {
    return name + " at " + position;
  }

  public Object clone() {
    try {
      Player cloned = (Player) super.clone();
      cloned.position = (Point) position.clone();
      return cloned;
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }
}

public class PlayerMain2 {
  public static void main(String args[]) {
    Player bob = new Player("Bob", 1, 1);
    Player john = (Player) (bob.clone());
    john.name = "John";
    john.position.setLocation(2, 2);
    System.out.println(bob);
  }
}
