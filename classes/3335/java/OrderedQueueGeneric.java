public class OrderedQueueGeneric<T extends Comparable> {

  private T[] queue;  // Array implementation of queue
  private int head;   // Array element of first queue element + 1

  public OrderedQueueGeneric(int size) {
    queue = (T[])new Comparable[size];  // NOTE:  You cannot use new T[size];
    head = 0;
  }

  public void enqueue(T newItem) {
    int insPos;
    for (insPos = head; ((insPos > 0) && (newItem.compareTo(queue[insPos-1]) >= 0)); --insPos) {
      queue[insPos] = queue[insPos-1];
    }
    queue[insPos] = newItem;
    head++;
  }

  public T dequeue() throws Exception {
    if (head == 0) throw new Exception("Dequeue on empty queue");
    return queue[--head];
  }

  public int size() {
    return head;
  }

  public static void main(String args[]) throws Exception {
    OrderedQueueGeneric<Person> q = new OrderedQueueGeneric<>(10);

    q.enqueue(new Person("Bob"));
    //q.enqueue(new String("Ed"));   // Typesafety
    q.enqueue(new Child("Jane", 3));

    while (q.size() > 0) {
      System.out.println(q.dequeue().getName());
    }
  }
}
