package re.geist.bananapiano.synthesizer;


import re.geist.bananapiano.circularqueue.ArrayCircularQueue;

public class MusicString {
    /** Constants. */
    private static final int SAMPLING_RATE = 64200;    // Sampling Rate
    private static final double DECEY = .996;          // Energy decay factor

    /** Buffer for storing sound data. */
    private ArrayCircularQueue<Double> buffer;

    /** Create a music string of the given frequency. */
    public MusicString(double frequency) {
        int capacity = (int) Math.round(SAMPLING_RATE / frequency);
        buffer = new ArrayCircularQueue<Double>(capacity);
        for (int i = 0; i < buffer.capacity(); i += 1) {
            buffer.enqueue(.0);
        }
    }

    /** Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < buffer.capacity(); i += 1) {
            buffer.dequeue();
            buffer.enqueue(Math.random() - .5);
        }
    }

    /** Advance the simulation one time step by performing one iteration
     *  of the Karplus-Strong Algorithm. */
    public void advance() {
        double first = buffer.dequeue();
        double second = buffer.peek();
        double next = (first + second) / 2 * DECEY;
        buffer.enqueue(next);
    }

    /** Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}

















