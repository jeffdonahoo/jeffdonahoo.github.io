import java.util.*;
import java.math.*;

public class Calculator2 {

  private BigInteger number = BigInteger.ZERO;

  class Add implements Command {
    BigInteger adder;
    public Add(int adder) { this(new BigInteger(Integer.toString(adder))); }
    private Add(BigInteger adder) { this.adder = adder; }
    public void execute() { number.add(adder); }
    public void undo() { number.subtract(adder); }
  }

  public Command getAdd(int adder) {
    return new Add(adder);
  }

  public Command getMultiply(final int multiplier) {
    return new CompositeCommand() {
      private BigInteger oldNumber;
      public void execute() {
        if (multiplier == 0) { oldNumber = number;}
        for (int i = 0; i < (multiplier - 1); i++) {
          append(new Add(number));
        }
      }
      public void undo() {
        if (multiplier == 0) { number = oldNumber;}
        else { number.divide(new BigInteger(Integer.toString(multiplier))); }
      }
    };
  }

  public Command getSubtract(int subtractor) {
    return new Add(-subtractor);
  }

  public Command getDivide(final int divisor) {
    return new Command() {
      private BigInteger oldNumber;
      public void execute() {
        oldNumber = number;
        number.divide(new BigInteger(Integer.toString(divisor)));
      }
      public void undo() {
        number = oldNumber;
      }
    };
  }

  public Command getLinear(final int a, final int b) {
    return new CompositeCommand() {
      {
        append(getMultiply(a));
        append(getAdd(b));
      }
    };
  }

  public int getNumber() {  return number.intValue(); }

  public static void printMenu() {
    System.out.println("1 - Add\n2 - Subtract\n3 - Multiply\n4 - Divide");
    System.out.println("5 - Linear (aX + b)\n6 - Undo\n7 - Quit");
  }

  public static void main(String[] args) {
    Calculator2 calc = new Calculator2();

    Stack cmds = new Stack();

    boolean quit = false;
    while (!quit) {
      System.out.println("Value: " + calc.getNumber());
      printMenu();
      int selection = Console.readInt("> ");
      if ((selection < 1) || (selection > 6)) {
        quit = true;
      } else if (selection == 6) {
        try {
          ((Command) cmds.pop()).undo();
        } catch (EmptyStackException e) {
          System.out.println("Nothing to undo");
        }
      } else {
        switch (selection) {
          case 1:
            cmds.push(calc.getAdd(Console.readInt("Adder: ")));break;
          case 2:
            cmds.push(calc.getSubtract(Console.readInt("Subtractor: ")));break;
          case 3:
            cmds.push(calc.getMultiply(Console.readInt("Multiplier: ")));break;
          case 4:
            cmds.push(calc.getDivide(Console.readInt("Divisor: ")));break;
          case 5:
            cmds.push(calc.getLinear(Console.readInt("a: "),
              Console.readInt("b: ")));
            break;
        }
        ((Command) cmds.peek()).execute();
      }
    }
  }
}