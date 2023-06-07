import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static  final double CONFIDENCE_95 = 1.96;

    private int trials = 0;
    private double[] portions;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        portions = new double[trials];
        int wholeCells = n * n;
        int randI = -1;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (true) {
                randI = StdRandom.uniformInt(0, wholeCells);
                int row = randI / n + 1;
                int col = randI % n + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    if (p.percolates()) {
                        portions[i] = (double) p.numberOfOpenSites() / wholeCells;
                        break;
                    }
                }
            }

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(portions);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(portions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        int n = Integer.parseInt(args[0]);
        final int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.printf("%-23s = %f%n", "mean", ps.mean());
        System.out.printf("%-23s = %f%n", "stddev", ps.stddev());
        System.out.printf("%-23s = [%f, %f]%n", "95% confidence interval",
                ps.confidenceLo(), ps.confidenceHi());
    }

}