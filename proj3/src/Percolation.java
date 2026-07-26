import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] box;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF WQUF;
    private int total_row;
    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) throw new IllegalArgumentException("N must be > 0");
        box = new boolean[N][N];
        WQUF = new WeightedQuickUnionUF(N*N + 2);
        numberOfOpenSites = 0;
        total_row = N;
    }

    private int index(int row, int col) {
        return row * total_row + col + 1;
    }

    private void connect(int row, int col) {
        if (!isOpen(row, col)) {
            return;
        }

        // 第一行连接虚拟顶部
        if (row == 0) {
            WQUF.union(index(row, col), 0);
        }

        // 最后一行连接虚拟底部
        if (row == total_row - 1) {
            WQUF.union(index(row, col), total_row * total_row + 1);
        }

        // 左
        if (col > 0 && isOpen(row, col - 1)) {
            WQUF.union(index(row, col), index(row, col - 1));
        }

        // 右
        if (col < total_row - 1 && isOpen(row, col + 1)) {
            WQUF.union(index(row, col), index(row, col + 1));
        }

        // 上
        if (row > 0 && isOpen(row - 1, col)) {
            WQUF.union(index(row, col), index(row - 1, col));
        }

        // 下
        if (row < total_row - 1 && isOpen(row + 1, col)) {
            WQUF.union(index(row, col), index(row + 1, col));
        }
    }

    private boolean connectTOTOP(int row, int col) {
        if(isOpen(row, col)){
            return WQUF.connected(total_row * row + col + 1, 0);
        }
        return false;
    }


    public void open(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);

        if (isOpen(row, col)) return;
        box[row][col] = true;
        numberOfOpenSites++;
        connect(row, col);
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);

        return box[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);

        return connectTOTOP(row, col);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return WQUF.connected(0, total_row * total_row + 1);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private void validate(int row, int col) {
        if (row < 0 || row >= total_row || col < 0 || col >= total_row) {
            throw new IllegalArgumentException();
        }
    }
}
