public class Airplane {

  private static final String UNCHRISTENEDNAME = "Unnamed";
  public static int noPlanesCreated = 0;

  private StringBuilder name;
  private long miles  = 0;

  public Airplane() {
    name = UNCHRISTENEDNAME;
    noPlanesCreated++;
  }

  public Airplane(String name) {
    this.name = name;
    noPlanesCreated++;
  }

  public void makeFlight(String flightNo, long miles) {
    playRecording(flightNo);
    this.miles += miles;
  }

  public StringBuilder getName() {
    return name;
  }

  public long getMiles() {
    return miles;
  }

  public String toString() {
    return name + " - Total miles: " + miles;
  }

  private static void playRecording(String flightNo) {
    System.out.println("Welcome to flight " + flightNo);
    System.out.println("In case of an emergency, your seat cushion may be " +
                       "used to muffle your screams.");
    System.out.println();
  }

  public static void main(String[] args) {

    Airplane flyer = new Airplane("Spirit of St. Louis");
    Airplane bomber = new Airplane("Enola Gay");

    flyer.makeFlight("1234", 3000);
    flyer.makeFlight("5678", 1500);

    System.out.println("Flyer:\n" + flyer);
    System.out.println("Planes Created: " + Airplane.noPlanesCreated);
  }
}
