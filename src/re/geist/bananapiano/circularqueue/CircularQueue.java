package re.geist.bananapiano.circularqueue;

public interface CircularQueue<typename> {
    /** Return the capacity of the circular queue. */
    int capacity();

    /** Return the size of the circular queue. */
    int size();

    /** Insert the @item at the end of the queue. */
    void enqueue(typename item);

    /** Remove the front item from the queue and return it. */
    typename dequeue();

    /** Return the front item but doesn't remove it. */
    typename peek();

    /** Return 1 if the queue is empty, otherwise 0. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Return 1 if the queue is full, otherwise 0. */
    default boolean isFull() {
        return size() == capacity();
    }
}
