import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q = (Item[]) new Object[8];
    private int capacity = 8;
    private int size = 0;
    private int top = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {}

    private void resize() {
        int newCapacity = capacity * 2;
        Item[] newQ = (Item[]) new Object[newCapacity];
        int newTop = 0;
        for (int i = 0; i < top; i++) {
            if (q[i] != null) {
                newQ[newTop++] = q[i];
            }
        }
        q = newQ;
        top = newTop;
        capacity = newCapacity;
    }

    private void compress() {
        int blanks = 0;
        for (int i = 0; i < top; i++) {
            if (q[i] == null) {
                blanks++;
            } else {
                q[i - blanks] = q[i];
            }
        }
        top -= blanks;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        while (top > 0 && q[top - 1] == null) { 
            top--; 
        }
        if (top == capacity) {
            resize();
        }
        q[top++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        
        int pos = StdRandom.uniformInt(top);
        int adds = pos % 2 == 0 ? 1 : top - 1;
        
        while (q[pos] == null) {
            pos = (pos + adds) % top;
        }
        Item item = q[pos];
        q[pos] = null;
        if (size <= top / 2) {
            compress();
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int pos = StdRandom.uniformInt(top);
        int adds = pos % 2 == 0 ? 1 : top - 1;
        while (q[pos] == null) {
            pos = (pos + adds) % top;
        }
        return q[pos];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> deque = new RandomizedQueue<>();
        //normal actions
        deque.enqueue("1");
        deque.enqueue("2");
        deque.enqueue("3");
        deque.enqueue("4");
        deque.enqueue("5");
        deque.enqueue("6");

        System.out.println(deque.dequeue());
        System.out.println(deque.dequeue());

        for (String c : deque) {
            System.out.print(c + " "); // suppose to be randomly "1", "2", "3", "4"
        }
        System.out.println();
        System.out.println(" size: " + deque.size());
        System.out.println("------------");

        for (String c : deque) {
            System.out.print(c + " "); // suppose to be randomly "1", "2", "3", "4"
        }


        //exceptions
        try {
            deque.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        System.out.println(deque.dequeue());
        System.out.println(deque.dequeue());
        System.out.println(deque.dequeue());
        System.out.println(deque.dequeue());

        
        try {
            System.out.println(deque.dequeue());
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }

        try {
            System.out.println(deque.sample());
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }


        Iterator<String> itor = deque.iterator();
        System.out.println(itor.hasNext());
        try {
            itor.next();
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }

        try {
            itor.remove();
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }

    }

    private class RandomQueIterator implements Iterator<Item> {
        int[] orders = StdRandom.permutation(top);
        int currentP = 0;

        @Override
        public boolean hasNext() {
            findNextP();
            return currentP < top;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = q[orders[currentP]];
            currentP++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void findNextP() {
            while (currentP < top && q[orders[currentP]] == null) {
                currentP++;
            }
        }
    }
}