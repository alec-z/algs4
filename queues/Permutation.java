import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQue = new RandomizedQueue<>();
        int n = 0;
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            n++;
            if (n <= k) {
                randomQue.enqueue(str);
            } else {
                if (StdRandom.bernoulli((double)k / n)) {
                    randomQue.dequeue();
                    randomQue.enqueue(str);
                }
            }
            
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randomQue.dequeue());
        }
    }
}
