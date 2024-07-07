public class PlaneMain {

  public static void main(String[] args) {

    Airplane flyer = new Airplane("Spirit of St. Louis");

    System.out.println("Flyer:\n" + flyer);

    StringBuilder name = flyer.getName();
    name.delete(0, name.length()).append("Spruce Goose");
    long miles = flyer.getMiles();
    miles = 1;

    System.out.println("Flyer:\n" + flyer);
  }
}
