import java.util.*;

public class Game {

  private static final int RANGE = 100;
  public static final int TARGET = 50;
  private static final int BOUND = 5;
  private int number;

  public Game() { number = (int) (Math.random() * RANGE); }

  class Add implements Command {
    int adder;
    public Add(int adder) { this.adder = adder; }
    public void execute() { number += adder; }
    public void undo() { number -= adder; }
  }

  public Command getAdd(int adder) {
    return new Add(adder);
  }

  public Command getSubtract(final int subtractor) {
    return new Add(-subtractor) {};
  }

  class Multiply extends CompositeCommand {

    public Multiply(int multiplier) {
      for (int i=0; i < (multiplier - 1); i++) {
        append(new Add(number));      // Assumes this executed next
      }
    }
  }

  public Command getMultiply(int multiplier) {
    return new Multiply(multiplier);
  }

  public Command getSquare() {
    return new Command() {
      public void execute() { number *= number; }
      public void undo() { number = (int)Math.sqrt(number); }
    };
  }

  public void move() {
    int x = (int) (Math.random() * BOUND - BOUND);
    System.out.println("Adding " + x);
    number += x;
  }

  public int getNumber() {
    return number;
  }

  public static void printMenu() {
    System.out.println("1 - Add");
    System.out.println("2 - Subtract");
    System.out.println("3 - Multiply");
    System.out.println("4 - Square");
    System.out.println("5 - Undo");
    System.out.println("6 - Quit");
  }

  public static void main(String[] args) {
    Game game = new Game();

    Stack cmds = new Stack();
    Command c = null;

    while (true) {
      if (game.getNumber() == Game.TARGET) {
        System.out.println("You win!");
        System.exit(0);
      }
      System.out.println("Current #: " + game.getNumber());
      printMenu();
      int selection = Console.readInt("> ");
      if ((selection < 1) || (selection > 5)) {
        System.exit(0);
      } else if (selection == 5) {
        try {
          ((Command) cmds.pop()).undo();
        } catch (EmptyStackException e) {
          System.out.println("Nothing to undo");
        }
      } else if ((selection >=1) && (selection <=3)) {
        int operand = Console.readInt("Operand: ");
        game.move();
        switch (selection) {
          case 1:
            c = game.getAdd(operand);break;
          case 2:
            c = game.getSubtract(operand);break;
          case 3:
            c = game.getMultiply(operand);break;
          case 4:
            c = game.getSubtract(operand);break;
        }
        c.execute();
        cmds.push(c);
      } else {
        c = game.getSquare();
        c.execute();
        cmds.push(c);
      }
    }
  }
}