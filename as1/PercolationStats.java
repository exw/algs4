public class PercolationStats {
    
    /*
     * The problem. In a famous scientific problem, 
     * researchers are interested in the following question: 
     * if sites are independently set to be open with probability p 
     * (and therefore blocked with probability 1 ? p), 
     * what is the probability that the system percolates? 
     * When p equals 0, the system does not percolate; 
     * when p equals 1, the system percolates. 
     * The plots below show the site vacancy probability p versus 
     * the percolation probability 
     * for 20-by-20 random grid (left) and 100-by-100 random grid (right). 
     * 
     * 
     * */
    
    
    private int[] results;
    private int times;
    private int pSize;
    
    public PercolationStats(int N, int T) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid input");
        }
        
        results = new int[T];
        times = T;
        pSize = N*N;
        boolean[] opened = new boolean[pSize];
        
        for (int k = 0; k < T; k++) {
            Percolation p = new Percolation(N);
            reset(opened);
            int result = 0;
            while (!p.percolates()) {
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                int target = (i-1)*N + j-1;
                
                if (!opened[target]) {
                    p.open(i, j);
                    result = result + 1;
                    opened[target] = true;
                }
            }
            results[k] = result;
        }
    }
    
    private void reset(boolean[] b) {
        for (int i = 0; i < pSize; i++) {
            b[i] = false;
        }
        return;
    }
    
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results)/((double) pSize);
    }
    
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(results)/((double) pSize);
    }
    
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return (this.mean() - 1.96*this.stddev() / Math.sqrt(times));
    }
    
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return (this.mean() + 1.96*this.stddev() / Math.sqrt(times));
    }
    
    public static void main(String[] args) {
        // test client (described below)
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        System.out.println("mean                    = "
                           + String.valueOf(ps.mean()));
        System.out.println("stddev                  = " 
                           + String.valueOf(ps.stddev()));
        System.out.println("95% confidence interval = "
                           + String.valueOf(ps.confidenceLo())
                           + ", " + String.valueOf(ps.confidenceHi()));
    }
}