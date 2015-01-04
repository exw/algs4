public class Percolation {
    
    /*
     * Week 1
     * 
     * The model: We model a percolation system using an N-by-N grid of sites. 
     * Each site is either open or blocked. 
     * A full site is an open site that can be connected to an open site 
     * in the top row via a chain of neighboring (left, right, up, down) open sites. 
     * We say the system percolates if there is a full site in the bottom row. 
     * In other words, a system percolates if we fill all open sites connected 
     * to the top row and that process fills some open site on the bottom row. 
     * 
     * */
    
    private int gridLen;
    private int pSize;
    private int[] status;
    /* status codes
     * 0: blocked
     * 1: open, not connected to top or bottom
     * 2: open, connected to top
     * 3: open, connected to bottom
     * 4: open, connected to top and bottom
     * */
    private WeightedQuickUnionUF p;
    private boolean done;
    
        
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        gridLen = N;
        pSize = N*N;
        //initialize with all sites blocked
        status = new int[pSize];
        p = new WeightedQuickUnionUF(N*N);
        return;
    }
    
    private int pIndex(int i, int j) {
        return (i-1)*gridLen + j-1;
    }
    
    private boolean outOfRange(int i, int j) {
        return (i <= 0 || i > gridLen || j <= 0 || j > gridLen);
    }
    
    private void checkRange(int i, int j) {
        if (outOfRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        return;
    }
    
    private int combineStatus(int l, int r, int u, int d) {
        int statusCode = 1;    
        if (l == 4 || r == 4 || u == 4 || d == 4) {
            return 4;
        }
        if (u == 2 || l == 2 || r == 2 || d == 2) {
            statusCode = 2;
        }
        if (d == 3 || l == 3 || r == 3 || d == 3) {
            statusCode = statusCode + 2;
        }
        return statusCode;
    }
    
    
    public void open(int i, int j) {
        checkRange(i, j);
        int target = pIndex(i, j);
        
        int[] adjIndex = new int[4];
        int[] adjStatus = new int[4];
        
        // connect UF data structure with adjacent open sites
        
        // left is i,j-1
        if (!outOfRange(i, j-1)) {
            adjIndex[0] = pIndex(i, j-1);
            adjStatus[0] = status[p.find(adjIndex[0])];
            if (adjStatus[0] > 0) {
                p.union(target, adjIndex[0]);
            }
        }
        
        // right is i,j+1    
        if (!outOfRange(i, j+1)) {
            adjIndex[1] = pIndex(i, j+1);
            adjStatus[1] = status[p.find(adjIndex[1])];
            if (adjStatus[1] > 0) {
                p.union(target, adjIndex[1]);
            }
        }
        
        // up is i-1,j
        if (!outOfRange(i-1, j)) {
            adjIndex[2] = pIndex(i-1, j);
            adjStatus[2] = status[p.find(adjIndex[2])];
            if (adjStatus[2] > 0) {
                p.union(target, adjIndex[2]);
            }
        }
        else {
            adjStatus[2] = 2;
        }

        // down is i+1,j
        if (!outOfRange(i+1, j)) {
            adjIndex[3] = pIndex(i+1, j);
            adjStatus[3] = status[p.find(adjIndex[3])];
            if (adjStatus[3] > 0) {
                p.union(target, adjIndex[3]);
            }
        }
        else {
            adjStatus[3] = 3;
        }
        
        int newStatus = combineStatus(adjStatus[0], // l
                                      adjStatus[1], // r
                                      adjStatus[2], // u
                                      adjStatus[3]  // d
                                     );
    
        if (newStatus == 4) {
            done = true;
        }
   
        status[p.find(target)] = newStatus;
        return;
    }
    
    public boolean isOpen(int i, int j) {
        checkRange(i, j);
        return (status[p.find(pIndex(i, j))] > 0);
        
    }
    
    public boolean isFull(int i, int j) {
        checkRange(i, j);
        return (status[p.find(pIndex(i, j))] % 2 == 0 
                && status[p.find(pIndex(i, j))] > 0);
    }
    
    public boolean percolates() {
        return done;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Percolation q = new Percolation(5);
        q.open(1, 2);
        if (q.isOpen(1, 2)) {
            System.out.println("Open isOpen");
        }
        q.open(5, 5);
        q.open(2, 2);
        q.open(3, 2);
        q.open(4, 2);
        q.open(4, 1);
        if (q.isFull(4, 1)) {
            System.out.println("isFull working");
        }
        q.open(5, 3);
        q.open(4, 3);
        if (q.isFull(5, 5)) {
            System.out.println("Backwash issue");
        }
        if (q.percolates()) {
            System.out.println("Percolates");
        }
        return;

    }

}