public class ConversionExNotes {

  public static void main(String args[]) {

    /* Without a cast, you can only assign in the following direction
       byte -> short -> int -> long -> float -> double and char -> int */

    // Literal numbers containing a decimal are assumed to be double
    // float f1 = 3.14;      // ERROR: Cannot assign double (8 bytes) to float (4 bytes)
    float f2 = (float) 3.14; // OK: Need cast
    f2 = 3.14F;              // OK: Float constant (D - double, L - long)
    double d1 = 3.14;        // OK

    // Literal numbers without a decimal are assumed to be an integer
    byte b1 = 3;             // OK: Special assignment converts *literal* int to byte or short

    // long l1 = 3000000000; // ERROR: Integer overflow
    long l2 = 3000000000L;   // OK: Long constant (no short or byte constants)
    //double d3 = 3000000000;// ERROR: Integer overflow.  Need D suffix.

    int i1 = b1;             // OK: Assign byte to int
    // int i2 = l2;          // ERROR: Cannot assign long to int
    int i2 = (int) l2;       // LEGAL: Only copies least signficant 4 bytes
    System.out.println(i2);  // Prints -1294967296 as a result of truncation

    // byte b2 = b1 + b1;    // ERROR: Arithmetic operation results in int
    byte b2 = b1;            // OK: No promotion
    b2 += b1;                // OK but bizarre since this is b2 = b2 + b1
    double d2 = f2 + i1;     // OK: Operands converted to float, results assigned to double

    i2 = (int) 3.53;
    System.out.println(i2);      // Prints 3 (truncation)
    i2 = (int) Math.round(3.53); // Still must cast because round taking a double returns long
    System.out.println (i2);     // Prints 4 (rounding)

    // Order of operator associativity.  First first with parentheses.
    // If b1 + b1 changed to *, it works
    System.out.println("Answer: " + b1 + b1);
    System.out.println(b1 + b1 + " is the answer");

    d2 = 5.0/0;                  // OK: Dividing a floating-point number by 0 is not caught
    System.out.println(d2);      // Prints "Infinity"
    i2 = 5/0;                    // Throws an exception
  }
}