import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private Node first;
    private Node last;
    private int N;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = last = null;
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() { return N == 0; }

    // return the number of items on the deque
    public int size()        { return N;   }

    // add the item to the front
    public void addFirst(Item item) {
        validateArgument(item);

        Node oldfirst = first;
        first = new Node();
        first.prev = null;
        first.item = item;

        if (isEmpty()) {
            first.next = null;
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateArgument(item);

        Node oldlast = last;
        last = new Node();
        last.next = null;
        last.item = item;

        if (isEmpty()) {
            last.prev = null;
            first = last;
        } else {
            last.prev = oldlast;
            oldlast.next = last;
        }
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateEmptyDeque();

        Item item = first.item;
        first = first.next;
        N--;

        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateEmptyDeque();

        Item item = last.item;
        last = last.prev;
        N--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove()     {
            throw new UnsupportedOperationException("Unsupported operation remove()");
        }

        public Item next()
        {
            if (current == null) {
                throw new NoSuchElementException("There are no more items to return.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void validateEmptyDeque() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty, no element to remove/return.");
        }
    }

    private void validateArgument(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null argument was sent by the client call.");
        }
    }

    // unit test (required)
    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();
        q.addLast(10);
        q.addFirst(20);
        q.addLast(30);
        q.addFirst(15);

        if (!q.isEmpty())   q.removeFirst();

        if (!q.isEmpty())   q.removeLast();

        Iterator iterator = q.iterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());

        System.out.println(q.size());
    }
} 
