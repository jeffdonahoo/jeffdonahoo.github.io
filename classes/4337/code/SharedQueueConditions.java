import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedQueueConditions {

    private static final int NOPRODUCEDELMTS = 100000;
    private Queue<Object> q = new LinkedList<Object>();
    private int maxSize;

    private Lock mutex = new ReentrantLock(true);
    private Condition notFull = mutex.newCondition();
    private Condition notEmpty = mutex.newCondition();

    public SharedQueueConditions(int maxSize) {
        this.maxSize = maxSize;
    }

    public void enqueue(Object in) throws InterruptedException {
        mutex.lock();
        while (isFull()) {
            notFull.await();
        }
        q.offer(in);
        notEmpty.signal();
        mutex.unlock();
    }

    public Object dequeue() throws InterruptedException {
        mutex.lock();
        while (isEmpty()) {
            notEmpty.await();
        }
        Object rtn = q.poll();
        notFull.signal();
        mutex.unlock();
        return rtn;
    }

    public boolean isEmpty() {
        mutex.lock();
        boolean empty = q.isEmpty();
        mutex.unlock();
        return empty;
    }

    public boolean isFull() {
        mutex.lock();
        boolean full = q.size() == maxSize;
        mutex.unlock();
        return full;
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
        SharedQueueConditions q = new SharedQueueConditions(1);

        new Thread(q.new Consumer(), "C1").start();
        new Thread(q.new Consumer(), "C2").start();
        new Thread(q.new Producer(), "P1").start();
    }
}
