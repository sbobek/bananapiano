package re.geist.bananapiano.circularqueue;

public class ArrayCircularQueue<typename> extends AbstractCircularQueue<typename> {
    /** Index for the next dequeue or peek. */
    private int head;
    /** Index for the next enqueue. */
    private int rear;
    /** Array for storing the data. */
    private typename[] array;

    /** Create a new ArrayCircularQueue with a given capacity. */
    public ArrayCircularQueue(int capacity) {
        this.head = 0;
        this.rear = 0;
        this.size = 0;
        this.capacity = capacity;
        this.array = (typename[]) new Object[this.capacity];
    }

    /** Add @item to the end of the queue.
     *  If there is no room, then throw a RuntimeException.*/
    @Override
    public void enqueue(typename item) {
        if (this.isFull()) {
            throw new RuntimeException("Queue Overflow!");
        } else {
            this.array[this.rear] = item;
            this.rear = (this.rear + 1) % this.capacity;
            this.size += 1;
        }
    }

    /** Remove the front item in the queue.
     *  If the queue is empty, then throw a RuntimeException. */
    @Override
    public typename dequeue() {
        if (this.isEmpty()) {
            throw new  RuntimeException("Queue underflow!");
        } else {
            typename val = this.array[this.head];
            this.head = (this.head  + 1) % this.capacity;
            this.size -= 1;
            return val;
        }
    }

    /** Return the front item in the queue, but doesn't remove it. */
    @Override
    public  typename peek() {
        return this.array[this.head];
    }
}
















