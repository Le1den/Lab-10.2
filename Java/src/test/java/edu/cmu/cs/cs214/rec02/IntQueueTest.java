package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {
    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        // Part 1: LinkedIntQueue - specification testing
        // mQueue = new LinkedIntQueue();

        // Part 2: ArrayIntQueue - structural testing
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    // -------------------------------------------------------
    // Basic state tests
    // -------------------------------------------------------

    @Test
    public void testIsEmpty() {
        // New queue must be empty
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // After enqueue, queue must NOT be empty
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    // -------------------------------------------------------
    // Peek tests
    // -------------------------------------------------------

    @Test
    public void testPeekEmptyQueue() {
        // Peek on empty queue must return null
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // Peek must return head element without removing it
        mQueue.enqueue(42);
        assertEquals(Integer.valueOf(42), mQueue.peek());
        // Size must stay the same after peek
        assertEquals(1, mQueue.size());
    }

    @Test
    public void testPeekDoesNotRemove() {
        // Peek multiple times - element must still be there
        mQueue.enqueue(10);
        mQueue.peek();
        mQueue.peek();
        assertEquals(1, mQueue.size());
        assertEquals(Integer.valueOf(10), mQueue.peek());
    }

    // -------------------------------------------------------
    // Enqueue tests
    // -------------------------------------------------------

    @Test
    public void testEnqueue() {
        // Enqueue multiple elements, check size and head
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testEnqueueReturnTrue() {
        // Enqueue must return true
        assertTrue(mQueue.enqueue(5));
    }

    // -------------------------------------------------------
    // Dequeue tests
    // -------------------------------------------------------

    @Test
    public void testDequeue() {
        // Dequeue must return elements in FIFO order
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
    }

    @Test
    public void testDequeueEmptyQueue() {
        // Dequeue on empty queue must return null
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testDequeueReducesSize() {
        // After dequeue, size must decrease by 1
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.dequeue();
        assertEquals(1, mQueue.size());
    }

    @Test
    public void testDequeueUntilEmpty() {
        // Dequeue all elements, queue must become empty
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.dequeue();
        mQueue.dequeue();
        assertTrue(mQueue.isEmpty());
    }

    // -------------------------------------------------------
    // Clear tests
    // -------------------------------------------------------

    @Test
    public void testClear() {
        // After clear, queue must be empty
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testClearThenEnqueue() {
        // After clear, must be able to enqueue again
        mQueue.enqueue(1);
        mQueue.clear();
        mQueue.enqueue(99);
        assertEquals(Integer.valueOf(99), mQueue.peek());
        assertEquals(1, mQueue.size());
    }

    // -------------------------------------------------------
    // Size tests
    // -------------------------------------------------------

    @Test
    public void testSizeEmpty() {
        // New queue size must be 0
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testSizeAfterEnqueue() {
        // Size must increase with each enqueue
        mQueue.enqueue(1);
        assertEquals(1, mQueue.size());
        mQueue.enqueue(2);
        assertEquals(2, mQueue.size());
    }

    // -------------------------------------------------------
    // Capacity / resize test (structural - ArrayIntQueue)
    // -------------------------------------------------------

    @Test
    public void testEnsureCapacity() {
        // Enqueue more than initial capacity (10) to trigger resize
        for (int i = 0; i < 15; i++) {
            mQueue.enqueue(i);
        }
        assertEquals(15, mQueue.size());
        // Elements must still come out in correct FIFO order
        for (int i = 0; i < 15; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }

    @Test
    public void testWrapAroundResize() {
        // Dequeue some elements first so head moves forward,
        // then enqueue past capacity to trigger wrap-around resize
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }
        // Dequeue 5 so head = 5
        for (int i = 0; i < 5; i++) {
            mQueue.dequeue();
        }
        // Enqueue 6 more to trigger resize with non-zero head
        for (int i = 10; i < 16; i++) {
            mQueue.enqueue(i);
        }
        // Remaining elements: 5,6,7,8,9,10,11,12,13,14,15
        assertEquals(Integer.valueOf(5), mQueue.dequeue());
        assertEquals(Integer.valueOf(6), mQueue.dequeue());
    }

    // -------------------------------------------------------
    // File content test
    // -------------------------------------------------------

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");
            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }
            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }
}