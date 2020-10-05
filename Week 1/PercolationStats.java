/* *****************************************************************************
 *  Name:              Johnlee
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *  Created by Johnlee
 *  Completed on Saturday, August 15th, 2020 at 09:24 AM morning
 */

public class PercolationStats {

    private final int mTrialNum;
    private final int mGridSize;
    private static final double CONFIDENCE = 1.96;
    private double mMean;
    private double mDeviation;
    private double[] mThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        // handle corner case
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        mTrialNum = trials;
        mGridSize = n;
        mThresholds = null;
        mMean = 0;
        mDeviation = 0;
    }

    // sample mean of percolation threshold
    private double[] getSampleThresholds() {

        Percolation percolation;
        int randomRow;
        int randomCol;

        double[] thresholds = new double[mTrialNum];

        for (int i = 0; i < mTrialNum; i++) {

            percolation = new Percolation(mGridSize);

            while (true) {
                randomRow = StdRandom.uniform(1, mGridSize + 1);
                randomCol = StdRandom.uniform(1, mGridSize + 1);
                if (!percolation.isOpen(randomRow, randomCol)) {
                    percolation.open(randomRow, randomCol);

                    if (percolation.percolates()) {
                        break;
                    }
                }
            }
            // reach here means already percolated
            thresholds[i] = (double) percolation.numberOfOpenSites() / (double) (mGridSize
                    * mGridSize);

        }
        return thresholds;
    }

    public double mean() {

        if (mMean > 0) {
            return mMean;
        }
        else {
            if (mThresholds == null || mThresholds.length <= 0) {
                mThresholds = getSampleThresholds();
            }
            mMean = StdStats.mean(mThresholds);
            return mMean;
        }
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        if (mDeviation > 0) {
            return mDeviation;
        }
        else {
            if (mThresholds == null || mThresholds.length <= 0) {
                mThresholds = getSampleThresholds();
            }
            mDeviation = StdStats.stddev(mThresholds);
            return mDeviation;
        }
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double lo = mean() - (CONFIDENCE * stddev()) / Math.sqrt(mTrialNum);
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double hi = mean() + (CONFIDENCE * stddev()) / Math.sqrt(mTrialNum);
        return hi;
    }

    // test client (see below)
    public static void main(String[] args) {

        int trialNum = StdRandom.uniform(20, 31);
        int gridSize = StdRandom.uniform(30, 61);

        PercolationStats percolationStats = new PercolationStats(gridSize, trialNum);
        StdOut.println("mean                    = " + percolationStats.mean());

        StdOut.println("stddev                  = " + percolationStats.stddev());

        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + " ,  "
                               + percolationStats.confidenceHi() + "]");

    }

}
