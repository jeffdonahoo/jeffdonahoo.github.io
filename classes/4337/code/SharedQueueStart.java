import java.util.LinkedList;
import java.util.Queue;

public class SharedQueueStart {

    private static final int NOPRODUCEDELMTS = 100000;
    private Queue<Object> q = new LinkedList<Object>();
    private int maxSize;

    public SharedQueueStart(int maxSize) {
        this.maxSize = maxSize;
    }

    public void enqueue(Object in) throws InterruptedException {
        q.offer(in);
    }

    public Object dequeue() throws InterruptedException {
        return q.poll();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public boolean isFull() {
        return q.size() == maxSize;
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < NOPRODUCEDELMTS; i++) {
                    enqueue(i);
                }
                System.out.println("Done producing");
            } catch (InterruptedException e) {
            }
        }
    };

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread() + ": "
                            + dequeue());
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        SharedQueueStart q = new SharedQueueStart(5);

        new Thread(q.new Consumer(), "C1").start();
        new Thread(q.new Producer(), "P1").start();
    }
}
