import java.util.LinkedList;
import java.util.Queue;

public class SharedQueue {

    private static final int NOPRODUCEDELMTS = 100000;
    private Queue<Object> q = new LinkedList<Object>();
    private int maxSize;

    public SharedQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void enqueue(Object in) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        q.offer(in);
        notify();
    }

    public synchronized Object dequeue() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        Object rtn = q.poll();
        notify();
        return rtn;
    }

    public synchronized boolean isEmpty() {
        return q.isEmpty();
    }

    public synchronized boolean isFull() {
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
        SharedQueue q = new SharedQueue(5);

        new Thread(q.new Consumer(), "C1").start();
        new Thread(q.new Producer(), "P1").start();
    }
}
