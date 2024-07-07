public enum Rating {
  G(0) {
    @Override
    public String advertise() {
      return "Stuffed animal";
    }
    
  }, PG(8) {
    @Override
    public String advertise() {
      return "Gummy bears";
    }
  }, PG13(13) {
    @Override
    public String advertise() {
      return "Glow-in-the-dark braces bands";
    }
  }, R(17) {
    @Override
    public String advertise() {
      return "1040 tax form";
    }
  };

  Rating(int age) {
    minAge = age;
  }

  public boolean oldEnough(int age) {
    return age >= minAge;
  }

  public static Rating maxMovie(int age) {
    // Assume first movie age is 0, age >=0, movie enums in age order
    // and I'm lazy (repeat G and don't terminate early).
    Rating maxMovie = G;
    for (Rating r : Rating.values()) {
      if (r.minAge <= age) {
        maxMovie = r;
      }
    }
    return maxMovie;
  }

  public abstract String advertise();
  
  private int minAge; // Minimum age to purchase

  public static void main(String args[]) {
    System.out.println(Rating.PG.oldEnough(12));

    for (Rating r : Rating.values()) {
      System.out.println(r.name() + ": "+ r.ordinal());
    }

    Rating war = Rating.valueOf("H");

    if (war.equals(Direction.LEFT)) {
      System.out.println("Boo");
    }
  }
}

enum Direction {
  LEFT, RIGHT
}

/**
 * 1.  Show Rating (G, PG, etc.) and print with values(), name(), ordinal(),
 *      valueOf() - Free methods
 * 2.  Add parameterized constructor and oldEnough()
 * 3.  Add enum Direction {LEFT, RIGHT} below
 *     Add Rating war = null; in main()
 *     Add if (war.equals(Direction.LEFT)) {... in main()
 *     Run and observe NullPointerException
 *     Fix war = R;
 *     Run and observe non-equality
 *     If used == instead of .equals() both NullPointerException
 *       and bad comparison are caught at compile time
 *       only applicable if Enum constant used (see JLS)
 * 4.  How do enums works?  Instance of Rating per enum with private constructor
 * 4.  Add maxMovie()
 * 5.  Add abstract advertise()
 */
