package java_program;

public class QueueLinkedList<T> implements Queue<T> {

    private int total;

    private Node last; 
    private Node first;

    private class Node {
        private T ele;
        private Node next;
    }

    public QueueLinkedList() { }

    public QueueLinkedList<T> enqueue(T ele)
    {
        Node current = last; //%%current%%gatherer%%
        last = new Node();
        last.ele = ele;

          if (total++ == 0) {
            first = last;
        } else { 
            current.next = last;
        }
        return this;
    }

    public T dequeue()
    {
        if (total == 0) throw new java.util.NoSuchElementException();
        T ele = first.ele;
        first = first.next;
        if (--total == 0) last = null;
        return ele;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node tmp = first;
        while (tmp != null) {
            sb.append(tmp.ele).append(", ");
            tmp = tmp.next;
        }
        return sb.toString();
    }

}