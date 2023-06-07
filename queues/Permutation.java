import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randomQue.enqueue(str);
            if (randomQue.size() > k) {
                randomQue.dequeue();
            }
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randomQue.dequeue());
        }
    }
}
