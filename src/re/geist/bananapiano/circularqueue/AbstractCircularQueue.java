package re.geist.bananapiano.circularqueue;

/** Add the abstract class in the hierarchy
 *  in order to make changes easily. */
public abstract class AbstractCircularQueue<typename> implements CircularQueue<typename> {
    protected int size;
    protected int capacity;

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int size() {
        return this.size;
    }
}
