import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board
{
    private int[][] tiles;
    private int N;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        N = tiles.length;
        this.tiles = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++)
                this.tiles[i][j] = tiles[i][j];
        }
    }

    // string representation of this board
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming()
    {
        int wrongPos = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != ((i + 1) * N - N + j + 1))
                    wrongPos++;
            }
        }
        return wrongPos;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int currentValue = tiles[i][j];
                if(currentValue == 0) continue;
                // m, n are the goal coordinates
                int m, n;
                if(currentValue % N == 0) {     // the rightmost values per row
                    m = currentValue / N - 1;
                    n = N - 1;
                }
                else {                          // other values per row
                    m = currentValue / N;
                    n = currentValue % N - 1;
                }
                // Manhattan distance = (sum of goal coord-s) - (sum of current coord-s)
                sum += Math.abs(m - i) + Math.abs(n - j);
//                StdOut.println(currentValue + ": " + (Math.abs(m - i) + Math.abs(n - j)));
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if(hamming() == 0) return true;
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // self check
        if (this == y)
            return true;
        // null check
        if (y == null)
            return false;
        // type check and cast
        if (getClass() != y.getClass())
            return false;
        Board brd = (Board) y;
        // field comparison
        return Arrays.deepEquals(tiles, brd.tiles);
    }

    // all neighboring boards
    public Iterator<Board> neighbors() { return new NeighborIterator(); }

    private class NeighborIterator implements Iterator<Board>
    {
        private Board[] neighbors;
        private int size;
        private int index;

        private int swap(int x, int y) {
            return x;
        }

        private NeighborIterator() {
            neighbors = new Board[4];
            size = 0;
            index = 0;
            int x = 0, y = 0;
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++)
                    if(tiles[i][j] == 0) {
                        x = i; y = j;
                        break;
                    }
            }

            initializeNeighbor(x - 1, y, x, y);
            initializeNeighbor(x + 1, y, x, y);
            initializeNeighbor(x, y - 1, x, y);
            initializeNeighbor(x, y + 1, x, y);

        }

        private void initializeNeighbor(int i, int j, int row, int col) {
            if(i >= 0 && i < N && j >= 0 && j < N) {
                int temp[][] = createCopy();
                temp[i][j] = swap(temp[row][col], temp[row][col] = temp[i][j]);
                neighbors[size++] = new Board(temp);
            }
        }

        public boolean hasNext() { return index < size; }

        public Board next()      {
            if (!hasNext()) {
                throw new NoSuchElementException("There are no more items to return.");
            }
            return neighbors[index++];
        }

        public void remove()     {
            throw new UnsupportedOperationException("Unsupported operation remove()");
        }
    }

    private int[][] createCopy() {
        int twin[][] = new int[N][N];
        for(int i = 0; i < N; i++) {
            twin[i] = tiles[i].clone();
        }
        return twin;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int twin[][] = createCopy();
        int i1 = StdRandom.uniform(N);
        int j1 = StdRandom.uniform(N);
        int i2 = StdRandom.uniform(N);
        int j2 = StdRandom.uniform(N);

        while(twin[i1][j1] == 0) {
            i1 = StdRandom.uniform(N);
            j1 = StdRandom.uniform(N);
        }
        while(twin[i2][j2] == 0 || twin[i1][j1] == twin[i2][j2]) {
            i2 = StdRandom.uniform(N);
            j2 = StdRandom.uniform(N);
        }

        int temp = twin[i1][j1];
        twin[i1][j1] = twin[i2][j2];
        twin[i2][j2] = temp;

        return new Board(twin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // n-by-n

        int tiles[][] = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board brd = new Board(tiles);
        StdOut.println(brd.toString());
        StdOut.println("Hamming distance: " + brd.hamming());
        StdOut.println("Manhattan distance: " + brd.manhattan());

        Iterator iterator = brd.neighbors();
        while (iterator.hasNext())
            System.out.println(iterator.next());

        System.out.println();

        System.out.println("Twin");
        System.out.println(brd.twin());
    }

}