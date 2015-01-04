public class Percolation {
    private int gridLen;
    private WeightedQuickUnionUF p;
    private int pSize;
    private int[] status;
    /* status codes
     * 0: blocked
     * 1: open, not connected to top or bottom
     * 2: open, connected to top
     * 3: open, connected to bottom
     * 4: open, connected to top and bottom
     * */
    private boolean done;
    
    private int pIndex(int i, int j){
        return (i-1)*gridLen + j;
    }
    
    private boolean outOfRange(int i, int j){
        return (i <= 0 || i > gridLen || j <= 0 || j > gridLen);
    }
    
    private void checkRange(int i, int j){
        if(outOfRange(i,j)){
            throw new IndexOutOfBoundsException();
        }
    }
    
    private int combineStatus(int l, int r, int u, int d){
        int statusCode = 1;
        if (l == 4 || r == 4){
            return 4;
        }
        if (u == 2 || l == 2 || r == 2){
            statusCode = 2;
        }
        if (d == 3 || l == 3 || r == 3){
            statusCode = statusCode + 2;
        }
        return statusCode;
    }
        
    public Percolation(int N){
        if(N <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        gridLen = N;
        pSize = N*N + 1;
        p = new WeightedQuickUnionUF(pSize);
        //initialize with all sites blocked
        status = new int[pSize];
    }
    
    public void open(int i, int j){
        checkRange(i,j);
        int target = pIndex(i,j);
        int[] adjIndex = new int[4];
        int[] adjStatus = new int[4];
        
        // connect UF data structure with adjacent open sites
        
        // left is i,j-1
        if(!outOfRange(i,j-1)){
            adjIndex[0] = pIndex(i,j-1);
            //adjStatus[0] = status[p.find(adjIndex[0])];
            adjStatus[0] = status[adjIndex[0]];
        }
        
        // right is i,j+1    
        if(!outOfRange(i,j+1)){
            adjIndex[1] = pIndex(i,j+1);
            //adjStatus[1] = status[p.find(adjIndex[1])];
            adjStatus[1] = status[adjIndex[1]];
        }
        
        // up is i-1,j
        if(!outOfRange(i-1,j)){
            adjIndex[2] = pIndex(i-1,j);
            //adjStatus[2] = status[p.find(adjIndex[2])];
            adjStatus[2] = status[adjIndex[2]];
        }
        else{
            adjStatus[2] = 2;
        }

        // down is i+1,j
        if(!outOfRange(i+1,j)){
            adjIndex[3] = pIndex(i+1,j);
            //adjStatus[3] = status[p.find(adjIndex[3])];
            adjStatus[3] = status[adjIndex[3]];
        }
        else{
            adjStatus[3] = 3;
        }
        
        int newStatus = combineStatus(adjStatus[0], // l
                                      adjStatus[1], // r
                                      adjStatus[2], // u
                                      adjStatus[3]  // d
                                     );
    
        if (newStatus == 4){
            done = true;
        }
        
        
        for(int k=0;k<4;k++){
            if(adjStatus[k] > 0 && adjIndex[k] > 0){
                status[p.find(adjIndex[k])] = newStatus;
                p.union(target,adjIndex[k]);    
            }
        }
        // set newStatus for all connected components
        status[p.find(target)] = newStatus;
        status[target] = newStatus;
        
        return;
    }
    public boolean isOpen(int i, int j){
        checkRange(i,j);
        return (status[pIndex(i,j)] > 0);
        
    }
    public boolean isFull(int i, int j){
        checkRange(i,j);
        return (status[p.find(pIndex(i,j))] % 2 == 0 && status[pIndex(i,j)] > 0);
    }
    
    public boolean percolates(){
        return done;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Percolation q = new Percolation(5);
        q.open(1,2);
        q.open(5,5);
        q.open(2,2);
        q.open(3,2);
        q.open(4,2);
        q.open(5,3);
        q.open(4,3);
        if(q.isFull(5,5)){
            System.out.println("Backwash issue");
        }
        return;

    }

}