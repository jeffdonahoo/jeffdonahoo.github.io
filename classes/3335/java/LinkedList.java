public class LinkedList {

  private Node head = null;

  void insert(String newData) {
    head = new Node(newData, head);
  }

  public String toString() {
    if (head == null)
      return "";
    else
      return head.toString();
  } 

  public static void main(String args[]) {
    LinkedList l = new LinkedList();

    l.insert("Mom");
    l.insert("Dad");
    System.out.println(l);
  }
}
    
class Node {
  private String data;
  private Node next;

  public Node(String newData, Node next) {
    data = newData;
    this.next = next;
  }

  public String toString() {
    if (next == null)
      return data;
    else
      return data + " " + next.toString();
  }
}