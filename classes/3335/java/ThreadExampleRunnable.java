public class ThreadExampleRunnable implements Runnable {

  private String greeting;   // Message to print to console

  public ThreadExampleRunnable(String greeting) {
    this.greeting = greeting;
  }

  public void run() {
    for (;;) {
      System.out.println(Thread.currentThread().getName() + ":  " + greeting);
      Thread.yield();
    }
  }

  public static void main(String[] args) {
    // new Thread(new ThreadExampleRunnable("Hello")).start();
    ThreadExampleRunnable hello = new ThreadExampleRunnable("Hello");
    Thread helloThread = new Thread(hello);
    helloThread.start();

    // new Thread(new ThreadExampleRunnable("Aloha")).start();
    ThreadExampleRunnable aloha = new ThreadExampleRunnable("Aloha");
    Thread alohaThread = new Thread(aloha);
    alohaThread.start();

    // new Thread(new ThreadExampleRunnable("Ciao")).start();
    ThreadExampleRunnable ciao = new ThreadExampleRunnable("Ciao");
    Thread ciaoThread = new Thread(ciao);
    ciaoThread.start();
  }
}