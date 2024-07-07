public class AirplaneNotes {

  private static final String UNCHRISTENEDNAME = "Unnamed"; // Class constant
                                                            // Can be initialized in static
                                                            // init section
  public static int noPlanesCreated = 0;                    // Class variable

  private StringBuffer name;   // Member variable (Should this be final? - No)
  private long miles  = 0;     // Initialized member variable

  // Constructors
  public AirplaneNotes() {
    name = UNCHRISTENEDNAME;  // See parameterized constructor for notes
    noPlanesCreated++;
    // We repeat the code in both constructors.  Bad, right?
    // In Java, we can call constructor x from constructor y by using this().
    // For example, we can put this(UNCHRISTENEDNAME) at the start of this
    // constructor.
  }

  public AirplaneNotes(String name) {
    this.name = name;   // Use this instead of thinking of different name for parameter!
                        // Would this work for String?  Yes!
                        //      Error - Assigning String to StringBuffer
                        // Change to this.name.append(name);
                        //      Error - No StringBuffer instance allocated (null pointer)
                        // Change to this.name = new StringBuffer(name);
    noPlanesCreated++;
  }

  // Member functions
  public void makeFlight(String flightNo, long miles) {
    playRecording(flightNo);
    this.miles += miles;
  }

  public StringBuffer getName() {
    return name;   // Returns a reference.  Bad!
  }

  public long getMiles() {
    return miles;  // Returns a value.  Ok!
  }

  public String toString() {  // Function called to convert Airplane to String
    return name + " - Total miles: " + miles;
  }

  // Class method
  private static void playRecording(String flightNo) {
    System.out.println("Welcome to flight " + flightNo);
    System.out.println("In case of an emergency, your seat cushion may be used to " +
                       "muffle your screams.");
    System.out.println();
  }

  public static void main(String args[]) {

    AirplaneNotes flyer = new AirplaneNotes("Spirit of St. Louis");
    AirplaneNotes bomber = new AirplaneNotes("Enola Gay");

    flyer.makeFlight("1234", 3000);
    flyer.makeFlight("5678", 1500);

    System.out.println("Flyer:\n" + flyer);
    System.out.println("Planes Created: " + Airplane.noPlanesCreated);
  }
}
