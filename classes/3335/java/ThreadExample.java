public class ThreadExample extends Thread {

  private String greeting;   // Message to print to console

  public ThreadExample(String greeting) {
    this.greeting = greeting;
  }

  public void run() {
    for (;;) {
      System.out.println(Thread.currentThread().getName() + ":  " + greeting);
      Thread.yield();
    }
  }

  public static void main(String[] args) {
    new Thread("Hello").start();
    new ThreadExample("Aloha").start();
    new ThreadExample("Ciao").start();
  }
}