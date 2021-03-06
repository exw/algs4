import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N = 0;
 
    public RandomizedQueue() {
        // construct an empty randomized queue
        a = (Item[]) new Object[1];
    }
 
    public boolean isEmpty() {
        // is the queue empty?
        return N == 0;
    }
   
    public int size() {
        // return the number of items on the queue
        return N;
    }
 
    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
    }
    
    private void checkNull(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("NPE");
        }
    }
    
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    
    public void enqueue(Item item) {
        // add the item
        checkNull(item);
        if (N == a.length) resize(2*a.length);
        a[N++] = item;
    }
    
    public Item dequeue() {
        // delete and return a random item
        checkEmpty();
        Item item = this.sample();
        a[N-1] = null;
        N--;
        if (N > 4 && N <= a.length/4) resize(a.length/2);
        return item;
    }
    
    public Item sample() {
        // return (but do not delete) a random item
        checkEmpty();
        int targetIndex = StdRandom.uniform(N);
        Item target = a[targetIndex];
        a[targetIndex] = a[N-1];
        a[N-1] = target;
        return a[N-1];
    }
    
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomOrderIterator();
    }
    
     private class RandomOrderIterator implements Iterator<Item> {
         private int i;
         
         public RandomOrderIterator() {
             i = N;
         }
         
         public boolean hasNext() {
             return i > 0;
         }
         
         public void remove() {
             throw new UnsupportedOperationException();
         }
         public Item next() {
             if (!hasNext()) throw new NoSuchElementException();
             int targetIndex = StdRandom.uniform(i);
             Item target = a[targetIndex];
             a[targetIndex] = a[i-1];
             a[i-1] = target;
             a[i] = null;
             return a[--i];
         }
     }
    public static void main(String[] args) {
        // unit testing
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        for (int i=0;i<2000;i++) {
            q.enqueue(String.valueOf(i));
        }
        for (int i=0;i<20;i++) {
            String output = "";
            output = output + " " + q.dequeue();
            output = output + "-" + String.valueOf(q.size());
        }
    }
}
