import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point a, Point b) {
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            if (slopeA == slopeB) {
                return 0;
            } else if (slopeA < slopeB) {
                return -1;
            } else {
                return 1;
            }
        }
    };
        
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        // pattern match degenerate, vertical lines when dx = 0 
        if (that.x - x == 0){
            if (that.y -y == 0) {
                // degenerate line when dy also = 0
                return Double.NEGATIVE_INFINITY;
            } else {
                // vertical line when dy != 0
                return Double.POSITIVE_INFINITY;
            } 
        } else if (that.y - y == 0) {
            // horizontal line when dy = 0 && dx != 0
            return 0.0;
        } else {
            // otherwise calculate slope
            return (that.y - y) / (that.x - x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        // invokingPoint.y less than argPoint.y
        if (y < that.y) {
            return -1;
        }
        // invokingPoint.y equal to argPoint.y
        else if (y == that.y) {
            if (x == that.x) {
                return 0;
            }
            else if (x > that.x) {
                return 1;
            }
            else {
                return -1;
            }
        }
        else {
            return 1;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point vert = new Point(1,0);
        Point horiz = new Point(0,1);
        Point degen = new Point(0,0);
        Point neg = new Point(-1,-1);
        Point origin = new Point(0,0);
        Point[] tests = new Point[] {vert, horiz, degen, neg};
        double[] slopes = new double[tests.length];
        for (int i = 0; i < tests.length; i++) {
            slopes[i] = origin.slopeTo(tests[i]);
            // System.out.println(String.valueOf(slopes[i]));
            System.out.println(String.valueOf(origin.compareTo(tests[i])));
        }
    }
}