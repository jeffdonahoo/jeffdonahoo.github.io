/**
 * OrderedQueue is a cheesy implementation of an ordered queue.  Array bound
 * exceptions are used to signal some boundary cases (e.g., queue full).  The
 * queue has a fixed maximum length.
 */
public class OrderedQueue<E extends Comparable> {

  private E queue[];  // Array implementation of queue
  private int head;        // Array element represent queue head + 1

  public OrderedQueue(int size) {
    queue = (E[]) new Comparable[size];
    head = 0;
  }

  public void enqueue(E newItem) {
    int insPos;
    for (insPos = head; ((insPos > 0) && (newItem.compareTo(queue[insPos-1]) >= 0)); --insPos) {
      queue[insPos] = queue[insPos-1];
    }
    queue[insPos] = newItem;
    head++;
  }

  public E dequeue() throws Exception {
    if (head == 0) throw new Exception("Dequeue on empty queue");
    return queue[--head];
  }

  public int size() {
    return head;
  }

  public static void main(String args[]) throws Exception {
    OrderedQueue<Person> q = new OrderedQueue<>(10);

    q.enqueue(new Person("Bob"));
    //q.enqueue(new String("Ed"));
    q.enqueue(new Child("Jane", 3));

    while (q.size() > 0) {
      System.out.println(q.dequeue().getName());
    }
  }
}
