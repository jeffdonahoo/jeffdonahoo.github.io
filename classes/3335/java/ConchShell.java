public class ConchShell extends Thread {

  public static void main(String[] args) {
    for (int i = 0; i < 4; i++) {
      new ConchShell().start();
    }
  }

  public synchronized void getShell() {
    System.out.println(Thread.currentThread().getName() + ": I have the shell.");
    System.out.flush();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {}
    System.out.println(Thread.currentThread().getName() + ": I release the shell");
    System.out.flush();
  }

  public void run() {
    for (int times = 0; times < 5; times++) {
      getShell();
    }
  }
}