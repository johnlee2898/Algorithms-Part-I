/* *****************************************************************************
 *  Name:              Jiwen Li
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *  Created by Jiwen Li
 *  Completed on Saturday, August 15th, 2020 at 09:24 AM morning
 */

public class Percolation {

    private boolean[][] mIsOpenStatus;  // All sites status: 1 - open or 0 - blocked
    private boolean[][] mIsFull;
    private final int mGridSize;
    private final WeightedQuickUnionUF uf;
    private int mNumOpenSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        // handle corner case
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        mGridSize = n;
        mNumOpenSite = 0;
        uf = new WeightedQuickUnionUF(n * n);

        mIsOpenStatus = new boolean[n][n];
        mIsFull = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // handle corner case: row or col out of range (0 -- mGridSize -1)
        if (!checkValidate(row, col)) {
            throw new IllegalArgumentException();
        }

        int index = (row - 1) * mGridSize + (col - 1);
        if (!isOpen(row, col)) {
            mIsOpenStatus[row - 1][col - 1] = true;
            mNumOpenSite++;


            // Union all opened neighbors
            // check left neighbor
            if (index % mGridSize != 0) {
                // left neighbor exists
                if (mIsOpenStatus[row - 1][col - 1 - 1]) {
                    // left neighbor is opened
                    uf.union(index, index - 1);
                }
            }
            // check right neighbor
            if ((index % mGridSize != (mGridSize - 1))) {
                // right neighbor exists
                if (mIsOpenStatus[row - 1][col]) {
                    // right neighbor is opened
                    uf.union(index, index + 1);
                }
            }

            // check up neighbor
            if (index >= mGridSize) {
                // up neighbor exists
                if (mIsOpenStatus[row - 2][col - 1]) {
                    // up neighbor is opened
                    uf.union(index, index - mGridSize);
                }
            }
            // check down neighbor
            if (index < mGridSize * (mGridSize - 1)) {
                // down neighbor exists
                if (mIsOpenStatus[row][col - 1]) {
                    // up neighbor is opened
                    uf.union(index, index + mGridSize);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        // handle corner case: row or col out of range (0 -- mGridSize -1)
        if (!checkValidate(row, col)) {
            throw new IllegalArgumentException();
        }

        return mIsOpenStatus[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {

        // handle corner case: row or col out of range (0 -- mGridSize -1)
        if (!checkValidate(row, col)) {
            throw new IllegalArgumentException();
        }

        int index = (row - 1) * mGridSize + (col - 1);
        return checkIsFullRecursively(0, index);
    }

    private boolean checkIsFullRecursively(int indexTemp, int index) {
        if (indexTemp == mGridSize) return false;
        if (mIsOpenStatus[0][indexTemp]) {
            if (uf.connected(index, indexTemp)) {
                return true;
            }
            while (((indexTemp + 1) < mGridSize) && mIsOpenStatus[0][indexTemp + 1]) {
                indexTemp++;
            }
            indexTemp++;
        }
        else {
            indexTemp++;
        }
        return checkIsFullRecursively(indexTemp, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return mNumOpenSite;
    }

    public boolean percolates() {
        return percolates(mIsOpenStatus);
    }

    private boolean percolates(boolean[][] isOpen) {
        int n = isOpen.length;
        mIsFull = exploreFullSites(isOpen);
        for (int j = 0; j < n; j++) {
            if (mIsFull[n - 1][j]) return true;
        }
        return false;
    }

    private boolean checkValidate(int row, int col) {

        // handle corner case: row or col out of range (0 -- mGridSize -1)
        if (!(row >= 1 && row <= mGridSize && col >= 1 && col <= mGridSize)) {
            return false;
        }
        return true;
    }

    // given an n-by-n matrix of open sites, return an n-by-n matrix
    // of sites reachable from the top
    private boolean[][] exploreFullSites(boolean[][] isOpen) {
        int n = isOpen.length;
        boolean[][] isFull = new boolean[n][n];
        for (int j = 0; j < n; j++) {
            exploreFullSites(isOpen, isFull, 0, j);
        }
        return isFull;
    }

    // determine set of full sites using depth first search
    private void exploreFullSites(boolean[][] isOpen, boolean[][] isFullVariable, int i, int j) {
        int n = isOpen.length;

        // base cases
        if (i < 0 || i > (n - 1)) return;    // invalid row
        if (j < 0 || j > (n - 1)) return;    // invalid column
        if (!isOpen[i][j]) return;      // not an open site
        if (isFullVariable[i][j]) return;       // already marked as full

        // mark i-j as full
        if (!isFullVariable[i][j]) {
            isFullVariable[i][j] = true;
        }

        exploreFullSites(isOpen, isFullVariable, i + 1, j);   // down
        exploreFullSites(isOpen, isFullVariable, i, j + 1);   // right
        exploreFullSites(isOpen, isFullVariable, i, j - 1);   // left
        exploreFullSites(isOpen, isFullVariable, i - 1, j);   // up
    }

    // test client (optional)
    public static void main(String[] args) {
        // keep it empty
    }
}
