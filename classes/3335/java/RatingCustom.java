// enum Rating {G, PG};

public final class RatingCustom implements Comparable {

  private static final RatingCustom[] VALUES;
  private String name;
  private int ordinal;

  private RatingCustom(String name, int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
  }

  public static final RatingCustom[] values() {
    return VALUES;
  }

  public static RatingCustom valueOf(String name){}

  public static final RatingCustom G = new Rating("G", 0);
  public static final RatingCustom PG = new Rating("PG", 1);

  static {
    VALUES = new Rating[] {G, PG};
  }
}
