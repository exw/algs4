public class Subset {
  public static void main(String[] args) {
    RandomizedQueue<String> q = new RandomizedQueue<String>();
    int n = Integer.parseInt(args[0]);
    int argSize = 0;
    while (!StdIn.isEmpty()) {
      q.enqueue(StdIn.readString());
      argSize++;
    }
    for (int i = 0; i < n; i++) {
      System.out.println(q.dequeue());
    }
  }
}