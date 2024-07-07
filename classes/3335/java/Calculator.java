import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    private double number = 0;

    // Member Class
    private class Add implements Command {
	private double adder;

	public Add(double adder) {
	    this.adder = adder;
	}

	public void execute() {
	    number += adder;
	}

	public void undo() {
	    number -= adder;
	}
    }

    public Command getAdd(double adder) {
	return new Add(adder);
    }

    public Command getSubtract(double subtractor) {
	return new Add(-subtractor);
    }

    // Local Class
    public Command getMultiply(final double multiplier) {
	class Multiply implements Command {
	    private double oldNumber;

	    public void execute() {
		if (multiplier == 0) {
		    oldNumber = number;
		}
		number *= multiplier;
	    }

	    public void undo() {
		if (multiplier == 0) {
		    number = oldNumber;
		} else {
		    number /= multiplier;
		}
	    }
	}

	return new Multiply();
    }

    // Anonymous Class
    public Command getDivide(final double divisor) {
	return new Command() {
	    private double number;

	    public void execute() {
		number = Calculator.this.number;
		Calculator.this.number /= divisor;
	    }

	    public void undo() {
		Calculator.this.number = number;
	    }
	};
    }

    public Command getLinear(final double a, final double b) {
	return new CompositeCommand() {
	    {
		append(getMultiply(a));
		append(getAdd(b));
	    }
	};
    }

    public double getNumber() {
	return number;
    }

    public static void printMenu(PrintStream out) {
	out.println("1 - Add\n2 - Subtract\n3 - Multiply\n4 - Divide");
	out.println("5 - Linear (aX + b)\n6 - Undo\n7 - Quit");
    }

    public String toString() {
	return String.valueOf(number);
    }

    public static void main(String[] args) {
	Calculator calc = new Calculator();
	Stack<Command> cmds = new Stack<>();
	Scanner sin = new Scanner(System.in);
	PrintStream out = System.out;

	boolean quit = false;
	while (!quit) {
	    out.println("Value: " + calc.getNumber());
	    printMenu(out);
	    out.print("> ");
	    int selection = sin.nextInt();
	    sin.nextLine(); // Consume EOLN
	    if ((selection < 1) || (selection > 6)) {
		quit = true;
	    } else if (selection == 6) {
		try {
		    cmds.pop().undo();
		} catch (EmptyStackException e) {
		    out.println("Nothing to undo");
		}
	    } else {
		switch (selection) {
		case 1: // Get adder
		    out.print("Adder: ");
		    cmds.push(calc.getAdd(sin.nextDouble()));
		    sin.nextLine(); // Consume EOLN
		    break;
		case 2:
		    out.println("Subtractor: ");
		    cmds.push(calc.getSubtract(sin.nextDouble()));
		    sin.nextLine(); // Consume EOLN
		    break;
		case 3:
		    out.print("Multiplier: ");
		    cmds.push(calc.getMultiply(sin.nextDouble()));
		    sin.nextLine(); // Consume EOLN
		    break;
		case 4:
		    out.print("Divisor: ");
		    cmds.push(calc.getDivide(sin.nextDouble()));
		    sin.nextLine(); // Consume EOLN
		    break;
		case 5:
		    out.print("a: ");
		    double a = sin.nextDouble();
		    sin.nextLine(); // Consume EOLN
		    out.print("b: ");
		    double b = sin.nextDouble();
		    sin.nextLine(); // Consume EOLN
		    cmds.push(calc.getLinear(a, b));
		    break;
		}
		cmds.peek().execute();
	    }
	}
    }
}
