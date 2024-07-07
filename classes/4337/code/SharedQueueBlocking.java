import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SharedQueueBlocking {
    public static void main(String[] args) {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(4);

        new Thread(new Consumer(q), "C1").start();
        new Thread(new Consumer(q), "C2").start();
        new Thread(new Producer(q), "P1").start();
    }
}

class Producer implements Runnable {
    private static final int NOPRODUCEDELMTS = 100000;
    private final BlockingQueue<Integer> q;

    public Producer(BlockingQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < NOPRODUCEDELMTS; i++) {
            try {
                q.put(i);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Done producing");
    }
};

class Consumer implements Runnable {
    private final BlockingQueue<Integer> q;

    public Consumer(BlockingQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread() + ": " + q.take());
            } catch (InterruptedException e) {
            }
        }
    }
}
