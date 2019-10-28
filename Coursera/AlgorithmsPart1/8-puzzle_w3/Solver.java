import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver
{
//    private Board currNode;
    private Board prevNode;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null)
            throw new IllegalArgumentException("Null argument was sent by the client call.");
        prevNode = null;
        moves = 0;
//        currNode = initial;
        MinPQ<Board> pq = new MinPQ<Board>();
        pq.insert(initial);

        while(true) {
            Board searchNode = pq.delMin();

            StdOut.println("SearchNode:");
            StdOut.println(searchNode);
            StdOut.println("Moves: " + moves);

            if(searchNode.isGoal()) {
                break;
            }
            moves++;
            prevNode = searchNode;

            for(Board board : searchNode.neighbors()) {
                pq.insert(board);
            }
//            Iterator iterator = searchNode.neighbors();
//            while (iterator.hasNext()) {
//                pq.insert(iterator.next());
//            }

        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {

    }

    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {

    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
    }

}