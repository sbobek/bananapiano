package re.geist.bananapiano.circularqueue;

import static org.junit.Assert.*;
import org.junit.Test;


public class TestArrayCircularQueue {
    /** Random small test on type int. */
    @Test
    public void testCase0() {
        ArrayCircularQueue<Integer> test0 = new ArrayCircularQueue<Integer>(10);
        int expected0 = (int) Math.random() * 100;
        test0.enqueue(expected0);

        assertEquals(expected0, (int) test0.peek());

        int expected1 = (int) Math.random() * 100;
        test0.enqueue(expected1);
        int expected2 = (int) Math.random() * 100;
        test0.enqueue(expected2);

        assertEquals(expected0, (int) test0.dequeue());
        assertEquals(expected1, (int) test0.dequeue());
        assertEquals(expected2, (int) test0.dequeue());
    }

    /** Random small test on type double. */
    @Test
    public void testCase1() {
        ArrayCircularQueue<Double> test1 = new ArrayCircularQueue<Double>(10);
        double expected0 = Math.random() * 100;
        test1.enqueue(expected0);

        assertEquals(expected0, test1.peek(), 0.0001);

        double expected1 = Math.random() * 100;
        test1.enqueue(expected1);
        double expected2 = Math.random() * 100;
        test1.enqueue(expected2);

        assertEquals(expected0, test1.dequeue(), 0.0001);
        assertEquals(expected1, test1.dequeue(), 0.0001);
        assertEquals(expected2, test1.dequeue(), 0.0001);
    }

    /** Large test on type int. */
    @Test
    public void testCase2() {
        final int CAPACITY = 100000;
        ArrayCircularQueue<Integer> test2 = new ArrayCircularQueue<Integer>(CAPACITY);

        assertEquals(true, test2.isEmpty());

        for (int i = 0; i < CAPACITY; i += 1) {
            test2.enqueue(i + CAPACITY);
        }

        assertEquals(CAPACITY, (int) test2.peek());

        for (int i = 0; i < CAPACITY; i += 1) {
            int temp = test2.dequeue();
            if (temp % 7 == 0) {
                assertEquals(i + CAPACITY, temp);
            }
        }
    }

    /** Large test on type double. */
    @Test
    public void testCase3() {
        final int CAPACITY = 100000;
        final double pi = Math.acos(-1);
        ArrayCircularQueue<Double> test3 = new ArrayCircularQueue<Double>(CAPACITY);
        for (int i = 0; i < CAPACITY; i += 1) {
            test3.enqueue(i * pi * CAPACITY);
        }

        assertEquals(.0, test3.peek(), 0.0001);
        assertEquals(true, test3.isFull());

        for (int i = 0; i < CAPACITY; i += 1) {
            double temp = test3.dequeue();
            if (temp % 7 == 0) {
                assertEquals(i * pi * CAPACITY, temp, 0.0001);
            }
        }
    }

}
