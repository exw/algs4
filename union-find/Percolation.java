public class Percolation {
    
    private int gridLen;
    private int gridSize;
    private boolean[] openSite;
    private WeightedQuickUnionUF p;
    private WeightedQuickUnionUF backwash;
    private int top;
    private int bot;
    
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        gridLen = N;
        gridSize = N*N;
        openSite = new boolean[gridSize + 2];
        p = new WeightedQuickUnionUF(gridSize + 2);
        backwash = new WeightedQuickUnionUF(gridSize + 1);
        top = gridSize;
        bot = gridSize + 1;
        openSite[top] = true;
        openSite[bot] = true;
    }
    
    private int pIndex(int i, int j) {
        return (i-1)*gridLen +j-1;
    }
    
    private boolean outOfRange(int i, int j) {
        return (i <= 0 || j <= 0 || i > gridLen || j > gridLen);
    }
    
    private void checkRange(int i, int j) {
        if (outOfRange(i, j)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return;
    }
    
    private void connect(int s1, int s2) {
        if (s1 == bot || s2 == bot) {
            p.union(s1, s2);
        }
        else if (openSite[s1] && openSite[s2]) {
            p.union(s1, s2);
            backwash.union(s1, s2);
        }
    }
    
    public void open(final int i, final int j) {
        checkRange(i, j);
        
        int target = pIndex(i, j);
        openSite[target] = true;
        
        if (i == 1) {
            connect(target, top);
        }        
        else {
            connect(target, pIndex(i-1, j));
        }
        
        if (i == gridLen) {
            connect(target, bot);
        } 
        else {
            connect(target, pIndex(i+1, j));
        }
        if (j > 1) {
            connect(target, pIndex(i, j-1));
        }
        if (j < gridLen) {
            connect(target, pIndex(i, j+1));
        }
    }
    
    public boolean isOpen(final int i, final int j) {
        checkRange(i, j);
        return openSite[pIndex(i, j)];
    }
    
    public boolean isFull(final int i, final int j) {
        checkRange(i, j);
        return backwash.connected(top, pIndex(i, j));
    }
    
    public boolean percolates() {
        return p.connected(top, bot);
    }
}