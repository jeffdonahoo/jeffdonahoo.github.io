import java.util.ArrayList;
import java.util.List;

public class Telethon {

  private Calculator callTotal = new Calculator();
  private Calculator pledgeTotal = new Calculator();

  private class Worker {
    private List<Command> callList = new ArrayList<>();
    private List<Command> pledgeList = new ArrayList<>();

    public void newCall(int calls, int pledges) {
      Command c = callTotal.getAdd(calls);  // Add calls
      c.execute();
      callList.add(c);
      c = pledgeTotal.getAdd(pledges);  // Add pledges
      c.execute();
      pledgeList.add(c);
    }

    public void deleteCalls() {
      // Eliminate calls
      for (Command c : callList) {
        c.undo();
      }
      // Eliminate pledges
      for (Command c : pledgeList) {
        c.undo();
      }
    }
  }
  
  public Worker getWorker() {
    return new Worker();
  }

  public String toString() {
    return "Current totals:\nCalls: " + callTotal + "\tPledges: " + pledgeTotal;
  }

  public static void main(String[] args) {

    Telethon telethon = new Telethon();
    Worker nuttyBob = telethon.getWorker();
    Worker dependableJane = telethon.getWorker();

    // Bob reports
    nuttyBob.newCall(3, 5000);
    dependableJane.newCall(2, 2000);
    nuttyBob.newCall(5, 4000);

    System.out.println(telethon);

    dependableJane.deleteCalls();

    System.out.println(telethon);
  }
}
