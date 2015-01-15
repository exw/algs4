import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;      //size
    private Node first; //top of stack
    private Node last;  //bot of stack
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    public Deque() {
        // construct an empty deque
        first = null;
        last = null;
        N = 0;
    }
    
    private void checkEmpty(){
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
    }
    
    public boolean isEmpty() {
        // is the deque empty?
        return (first == null && last == null);
    }
    
    public int size() {
        // return the number of items on the deque
        return N;
    }
    
    public void addFirst(Item item) {
        // insert the item at the front
        if (N == 0) {
            first = new Node();
            first.item = item;
            last = first;
            N++;
        }            
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev = first;
            N++;
        }
    }
    
    public void addLast(Item item) {
        // insert the item at the end
        if (N == 0) {
            last = new Node();
            last.item = item;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.prev = oldlast;
            oldlast.next = last;
            N++;
        }
    }
    
    public Item removeFirst() {
        // delete and return the item at the front
        checkEmpty();
        if (N == 1) {
            Item item = first.item;
            first = null;
            last = null;
            N--;
            return item;
        }
        else {
            Item item = first.item;
            first = first.next;
            first.prev = null;
            N--;
            return item;
        }
    }
    
    public Item removeLast() {
        // delete and return the item at the end
        checkEmpty();
        if (N == 1) {
            Item item = first.item;
            first = null;
            last = null;
            N--;
            return item;
        }
        else {
            Item item = last.item;
            last = last.prev;
            last.next = null;
            N--;
            return item;
        }
    }
    
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
        
    public static void main(String[] args) {
        // unit testing
        Deque<String> s = new Deque<String>();
        s.addFirst("O");
        s.addLast("K");
        s.addFirst("A");
        s.addLast("B");
        s.addFirst("C");
        s.addFirst("D");
        s.addLast("E");
        s.addFirst("F");
        s.addLast("G");
        String quote = s.removeFirst();
        quote = quote + s.removeLast();
        System.out.println(quote);
        
    }
}