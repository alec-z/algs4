import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF uf = null;
    private boolean[] isOpen;
    private int[] maxI;
    private int nsize = 0;
    private int opened = 0;
    private boolean percolated = false;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        this.nsize = n;
        isOpen = new boolean[n * n];
        maxI = new int[n * n + 2];
        for (int i = 0; i < n * n; i++) {
            maxI[i] = i;
        }
        maxI[n * n] = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int i = tranferPosition(row, col);
        isOpen[i] = true;
        opened++;
        int maxTemp = maxI[i];
        int otherComponentMax = 0;

        // top
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                otherComponentMax = componentMaxId(i - nsize);
                if (otherComponentMax > maxTemp) {
                    maxTemp = otherComponentMax;
                } 
                uf.union(i - nsize, i);
            }
        } else {
            otherComponentMax = componentMaxId(nsize * nsize);
            if (otherComponentMax > maxTemp) {
                maxTemp = otherComponentMax;
            } 
            uf.union(nsize * nsize, i);
        }
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                otherComponentMax = componentMaxId(i - 1);
                if (otherComponentMax > maxTemp) {
                    maxTemp = otherComponentMax;
                } 
                uf.union(i - 1, i);
                
            }
        }
        if (col < nsize) {
            if (isOpen(row, col + 1)) {
                otherComponentMax = componentMaxId(i + 1);
                if (otherComponentMax > maxTemp) {
                    maxTemp = otherComponentMax;
                } 
                uf.union(i + 1, i);
            }
        }
        if (row < nsize) {
            if (isOpen(row + 1, col)) {
                otherComponentMax = componentMaxId(i + nsize);
                if (otherComponentMax > maxTemp) {
                    maxTemp = otherComponentMax;
                } 
                uf.union(i + nsize, i);
            }
        }
        int component = uf.find(i);

        maxI[component] = maxTemp;

        if (maxTemp >= nsize * (nsize - 1) 
            && (percolated || uf.find(i) == uf.find(nsize * nsize))) {
            percolated = true;
        }
        
    }

    private int componentMaxId(int i) {
        return maxI[uf.find(i)];
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int i = tranferPosition(row, col);
        return isOpen[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int i = tranferPosition(row, col);
        return uf.find(i) == uf.find(nsize * nsize);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    private int tranferPosition(int row, int col) {
        if (row < 1 || row > nsize || col < 1 || col > nsize) {
            throw new IllegalArgumentException();
        }
        return (row - 1) * nsize + (col - 1);

    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 6;
        Percolation p = new Percolation(n);
        p.open(1, 6);
        p.open(2, 6);
        p.open(3, 6);
        p.open(4, 6);
        p.open(5, 6);

        p.open(5, 5);
        p.open(4, 4);
        p.open(3, 4);
        p.open(2, 4);

        p.open(2, 3);
        p.open(2, 2);

        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        p.open(5, 2);
        p.open(6, 2);
        p.open(5, 4);
        System.out.println(p.percolates());
    }


}
