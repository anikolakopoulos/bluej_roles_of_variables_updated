package java_program;

interface Queue<T> {
    Queue<T> enqueue(T ele);

    T dequeue();
}