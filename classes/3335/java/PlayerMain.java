import java.awt.Point;

class Player {
  public Point position;
  public String name;
  public int health;

  public Player(String name, int x, int y, int health) {
    position = new Point(x, y);
    this.name = name;
    this.health = health;
  }

  public String toString() {
    return name + " at " + position + " with health " + health;
  }
}

public class PlayerMain {
  public static void main(String args[]) {
    Player bob = new Player("Bob", 1, 1, 50);
    Player john = bob;
    john.name = "John";
    john.position.setLocation(2, 2);
    john.health = 25;
    System.out.println(bob);
  }
}
