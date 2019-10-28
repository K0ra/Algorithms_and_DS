import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    
    private WeightedQuickUnionUF uf;
    private boolean[][] Grid;
    private int openCnt;
    private int virtualTop;
    private int virtualBottom;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        
        uf = new WeightedQuickUnionUF(n * n + 2);
        
        Grid = new boolean[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                Grid[i][j] = false;
            }
        }
        
        openCnt = 0;
        virtualTop = 0;
        virtualBottom = n * n + 1;
        
        connectVirtualSides();
    }
    
    // open site (row, col) if it is not open already
    public    void open(int row, int col) {
        validate(row);
        validate(col);
        Grid[row][col] = true;
        openCnt += 1;
        int currSite = xyTo1D(row, col);
        int n = Grid[0].length - 1;
        
        if (row > 1 && Grid[row - 1][col])
            uf.union(currSite, xyTo1D(row - 1, col));
        if (row < n && Grid[row + 1][col])
            uf.union(currSite, xyTo1D(row + 1, col));
        if (col > 1 && Grid[row][col - 1])
            uf.union(currSite, xyTo1D(row, col - 1));
        if (col < n && Grid[row][col + 1])
            uf.union(currSite, xyTo1D(row, col + 1));
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return Grid[row][col] == true;
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        int currSite = xyTo1D(row, col);
        return (isOpen(row, col) & uf.connected(currSite, virtualTop));
    }
    
    // number of open sites
    public     int numberOfOpenSites() {
        return openCnt;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }
    
    private int xyTo1D(int row, int col) {
        int n = Grid[0].length - 1;
        return (row - 1) * n + col;
    }
    
    private void connectVirtualSides() {
        int n = Grid[0].length - 1;
        for (int i = 1; i <= n; i++)
        {
            int currTopSite = xyTo1D(1, i);
            int currBottomSite = xyTo1D(n, i);
            
            uf.union(virtualTop, currTopSite);
            uf.union(virtualBottom, currBottomSite);
        }
    }
    
    // validate that p is a valid index
    private void validate(int p) {
        int n = Grid[0].length - 1;
        if (p < 1 || p > n) {
            throw new IllegalArgumentException("index " + p + " is not between 1 and " + n);  
        }
    }
    
    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation pc = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            if (pc.isOpen(row, col)) continue;
            pc.open(row, col);
            if (pc.isOpen(row, col)) { StdOut.println("{ " + row + ", " + col + " }"); }
            if (pc.isFull(row, col)) {
                StdOut.println("Full site { " + row + ", " + col + " }");
            }
            if(pc.percolates()) {
                StdOut.println("System is percolated");
                break;
            }
        }
        StdOut.println(pc.numberOfOpenSites() + " open sites");
    }
} 
