package java_program;

public class TestQueue
{
    public static void main(String[] args) {
        {
            Queue<String> greeting  = new QueueLinkedList<>(); 

            greeting.enqueue("Hello").enqueue(", ").enqueue("World!");

            System.out.println(greeting.dequeue() + greeting.dequeue() + greeting.dequeue());
        }
    }
}
