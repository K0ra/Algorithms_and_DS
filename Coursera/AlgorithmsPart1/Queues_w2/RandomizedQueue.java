import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] randQueue;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randQueue = (Item[]) new Object[1];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return N == 0; }

    // return the number of items on the randomized queue
    public int size()        { return N;   }

    // move randQueue to a new array of new size
    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < N; ++i)
            temp[i] = randQueue[i];
        randQueue = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null argument was sent by the client call.");

        if (N == randQueue.length)
            resize(2 * randQueue.length);
        randQueue[N++] = item;
    }

    private void validateEmptyQueue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty, no element to dequeue/return.");
        }
    }

    // remove and return a random item
    public Item dequeue() {
        validateEmptyQueue();

        int randIndex = getRandIndex();
        swap(randIndex, N-1);
        Item item = randQueue[--N];
        randQueue[N] = null;

        if (N > 0 && N == randQueue.length / 4)
            resize(randQueue.length / 2);
        return item;
    }

    // get random index in interval [0; N), head = 0, tail = N-1
    private int getRandIndex() {
        return StdRandom.uniform(size());
    }

    // swap uniformly random index with the tail index
    private void swap(int randIndex, int tailIndex) {
        Item tmp = randQueue[randIndex];
        randQueue[randIndex] = randQueue[tailIndex];
        randQueue[tailIndex] = tmp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateEmptyQueue();

        int randIndex = getRandIndex();
        return randQueue[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item>
    {
        private Item[] bufferQueue;
        private int i = 0;

        private RandomArrayIterator() {
            bufferQueue = (Item[]) new Object[N];
            initializeBufferQueue();
            StdRandom.shuffle(bufferQueue);
        }

        private void initializeBufferQueue() {
            for (int i = 0; i < N; ++i)
                bufferQueue[i] = randQueue[i];
        }

        public boolean hasNext() { return i < N; }

        public Item next()       {
            if (!hasNext()) {
                throw new NoSuchElementException("There are no more items to return.");
            }
            return bufferQueue[i++];
        }

        public void remove()     {
            throw new UnsupportedOperationException("Unsupported operation remove()");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 10; i <= 30; i += 5)
            rq.enqueue(i);

        Iterator iterator = rq.iterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());

        System.out.println();

        Iterator iterator1 = rq.iterator();
        while (iterator1.hasNext())
            System.out.println(iterator1.next());

        if (!rq.isEmpty())   rq.dequeue();

        StdOut.println(rq.size());
        StdOut.println(rq.sample());
    }

}