import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation
{
    public static void main(String args[]) {
        int k = Integer.parseInt(args[0]);
        int i = 0;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            if (k == 0)   { break; }

            String s = StdIn.readString();
            if (i++ >= k) { String removed = rq.dequeue(); }
            rq.enqueue(s);
        }
        Iterator iterator = rq.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}