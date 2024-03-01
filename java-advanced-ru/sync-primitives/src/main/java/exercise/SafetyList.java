package exercise;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SafetyList {
    // BEGIN
    private int[] array;
    private int size;
    private final Lock lock;

    public SafetyList() {
        this.array = new int[10];
        this.size = 0;
        this.lock = new ReentrantLock();
    }

    public void add(int element) {
        lock.lock();
        try {
            ensureCapacity();
            array[size++] = element;
        } finally {
            lock.unlock();
        }
    }

    public int get(int index) {
        lock.lock();
        try {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return array[index];
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        lock.lock();
        try {
            return size;
        } finally {
            lock.unlock();
        }
    }

    private void ensureCapacity() {
        if (size == array.length) {
            int[] newArray = new int[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }
    // END
}
