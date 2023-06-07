import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null;
    private Node last = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;

    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        first = new Node(item, first, null);
        if (first.next != null) {
            first.next.prev = first;
        } else {
            last = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        last = new Node(item, null, last);
        if (last.prev != null) {
            last.prev.next = last;
        } else {
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        //normal actions
        deque.addFirst("2");
        deque.addFirst("1");
        deque.addLast("3");
        deque.addLast("4");
        for (String c : deque) {
            System.out.print( c + " "); // suppose to be "1", "2", "3", "4"
        }
        System.out.println(" size: " + deque.size());
        System.out.println("------------");

        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst()); //suppose to be "4", "1", "3", "2"

        System.out.println("------------");

        //exceptions
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        try {
            deque.addLast(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }


        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }

        try {
            deque.removeFirst();
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

    private class DequeIterator implements Iterator<Item> {
        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unimplemented method 'hasNext'");
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

}
