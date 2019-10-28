import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Merge
{
    private Merge() { }

    private static boolean less(Comparable v, Comparable w)
    { return v.compareTo(w) < 0; }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if      (i > mid)           aux[k] = a[j++];
            else if (j > hi)            aux[k] = a[i++];
            else if (less(a[j], a[i]))  aux[k] = a[j++];
            else                        aux[k] = a[i++];
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;

        System.out.println("Low-mid");
        show(a, lo, mid);
        show(aux, lo, mid);
        sort (aux, a, lo, mid);

        System.out.println("Mid+1 - hi");
        show(a, mid+1, hi);
        show(aux, mid+1, hi);
        sort (aux, a, mid+1, hi);

        merge(a, aux, lo, mid, hi);
        //show(a, lo, hi);
    }

    public static void sort(Comparable[] a)
    {
        Comparable[] aux = new Comparable[a.length];
        for (int k = 0; k < a.length; k++)
            aux[k] = a[k];
        sort(a, aux, 0, a.length-1);
    }

    private static void show(Comparable[] a, int lo, int hi)
    { // Print the array, on a single line.
        for (int i = lo; i <= hi; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    public static void main(String[] args)
    { // Read strings from standard input, sort them, and print.
        String[] a = StdIn.readAllStrings();
//        for (int i = 0; i <= a.length - 1; i++)
//            StdOut.print(a[i] + " ");
//        StdOut.println();
//        show(a, 0, a.length - 1);
        Merge.sort(a);
    }
}