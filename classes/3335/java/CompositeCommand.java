import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class CompositeCommand implements Command {

  private List<Command> commands = new ArrayList<>();

  public void append(Command c) {
    commands.add(c);
  }

  public void execute() {
    for (Command c : commands) {
      c.execute();
    }
  }

  public void undo() {
    for (ListIterator<Command> i = commands.listIterator(commands.size()); i.hasPrevious();) {
      i.previous().undo();
    }
  }
}
