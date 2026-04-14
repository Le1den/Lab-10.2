package edu.cmu.cs.cs214.rec02;

import java.util.Arrays;

/**
 * A resizable-array implementation of the {@link IntQueue} interface.
 * BUGS FIXED:
 *   Bug 1 - isEmpty()      : size >= 0  →  size == 0
 *   Bug 2 - peek()         : missing null check for empty queue
 *   Bug 3 - ensureCapacity : wrong index when copying wrapped elements
 */
public class ArrayIntQueue implements IntQueue {

    private int[] elementData;
    private int head;
    private int size;
    private static final int INITIAL_SIZE = 10;

    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    public void clear() {
        Arrays.fill(elementData, 0);
        size = 0;
        head = 0;
    }

    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        Integer value = elementData[head];
        head = (head + 1) % elementData.length;
        size--;
        return value;
    }

    public boolean enqueue(Integer value) {
        ensureCapacity();
        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;
        return true;
    }

    // BUG 1 FIXED: was "size >= 0" which is always true
    public boolean isEmpty() {
        return size == 0;
    }

    // BUG 2 FIXED: was missing null check for empty queue
    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return elementData[head];
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = 2 * oldCapacity + 1;
            int[] newData = new int[newCapacity];

            // Copy elements from head to end of old array
            for (int i = head; i < oldCapacity; i++) {
                newData[i - head] = elementData[i];
            }

            // BUG 3 FIXED: was "newData[head - i]" which is wrong index
            // Copy wrapped elements (from 0 to head)
            for (int i = 0; i < head; i++) {
                newData[oldCapacity - head + i] = elementData[i];
            }

            elementData = newData;
            head = 0;
        }
    }
}