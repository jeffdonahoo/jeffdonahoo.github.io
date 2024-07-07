public class BadElementException extends Exception {
  private int index;

  public BadElementException(String message, int index) {
    super(message);
    this.index = index;
  }

  public int getIndex() {
    return index;
  }
  
  /****************************************
   * Main stuff
   */
  public static int string2Integer(String s) throws Exception {
      int value = 0;  // Integer value of string
    
      for (int i = 0; i < s.length(); i++)
        if (Character.isDigit(s.charAt(i)))  // Is the character a digit?
          value = (value * 10) + Character.digit(s.charAt(i), 10);
        else
          throw new Exception("Bad Number");
    
      return value;
    }
  
  public static int average(String[] seq) throws BadElementException {
      int sum = 0;

      for (int i = 0; i < seq.length; i++){
        try {
      sum += string2Integer(seq[i]);
        } catch (Exception e) {
      throw new BadElementException("Element is not a number", i);
        }
      }

      return sum / seq.length;
    }

    public static void main(String[] args) {
      try {
        System.out.println(average(args));
      } catch (BadElementException e) {
        System.out.println("Oops, " + args[e.getIndex()] + " isn't a number");
      }
    }
}