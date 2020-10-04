
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Jiwen Li
 * Created on Saturday, August 29th, 2020 at 01:40 PM
 */

public class Board {

    private final int[][] mTiles;
    private final int mSize;
    private final int randomRow;
    private final int randomCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(final int[][] tiles) {

        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        mSize = tiles.length;
        mTiles = new int[mSize][mSize];
        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                mTiles[i][j] = tiles[i][j];
            }
        }

        int randomRowLocal = StdRandom.uniform(0, mSize);
        int randomColLocal = StdRandom.uniform(0, mSize);

        while (mTiles[randomRowLocal][randomColLocal] == 0 || (randomColLocal + 1) > (mSize - 1)
                || mTiles[randomRowLocal][randomColLocal + 1] == 0) {
            randomRowLocal = StdRandom.uniform(0, mSize);
            randomColLocal = StdRandom.uniform(0, mSize);
        }

        randomRow = randomRowLocal;
        randomCol = randomColLocal;
    }

    // string representation of this board
    public String toString() {

        String tempString = mTiles.length + "\n";

        for (int i = 0; i < mTiles.length; i++) {
            for (int j = 0; j < mTiles[i].length; j++) {
                tempString += " " + mTiles[i][j];
            }
            tempString += "\n";
        }

        return tempString;
    }

    // board dimension n
    public int dimension() {

        return mSize;
    }

    // number of tiles out of place
    public int hamming() {

        int count = 0;

        for (int i = 0; i < mTiles.length; i++) {
            for (int j = 0; j < mTiles[i].length; j++) {

                if ((i == (mSize - 1)) && (j == (mSize - 1))) {
                    // Skip blank item
                }
                else if (mTiles[i][j] != (i * mSize + j + 1)) {
                    count++;
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int distance = 0;

        for (int i = 0; i < mTiles.length; i++) {
            for (int j = 0; j < mTiles[i].length; j++) {

                if ((i == (mSize - 1)) && (j == (mSize - 1))) {
                    // Skip blank item
                }
                else {

                    int desiredValue = i * mSize + j + 1;

                    if (mTiles[i][j] != desiredValue) {

                        int row = 0, col = 0;
                        for (int k = 0; k < mTiles.length; k++) {
                            for (int m = 0; m < mTiles[k].length; m++) {
                                if (mTiles[k][m] == desiredValue) {
                                    // Find the item
                                    row = k;
                                    col = m;
                                    break;
                                }
                            }
                        }
                        distance += Math.abs(row - i) + Math.abs(col - j);
                    }
                }
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {

        boolean result = true;

        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                if ((i == (mSize - 1)) && (j == (mSize - 1))) {
                    // must 0
                    if (mTiles[i][j] != 0) {
                        result = false;
                    }
                }
                else {
                    int desiredValue = i * mSize + j + 1;
                    if (mTiles[i][j] != desiredValue) {
                        result = false;
                        break;
                    }
                }
            }
        }

        return result;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }

        return (this.mSize == ((Board) y).mSize) &&
                Arrays.deepEquals(this.mTiles, ((Board) y).mTiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        final Board[] boards;
        List<Board> boardList = new ArrayList<>();

        int[][] topNeighbor = null;
        int[][] bottomNeighbor = null;
        int[][] leftNeighbor = null;
        int[][] rightNeighbor = null;

        Board topNeighborBoard = null;
        Board bottomNeighborBoard = null;
        Board leftNeighborBoard = null;
        Board rightNeighborBoard = null;

        // Find current blank item position
        int blankRowNum = mSize - 1;
        int blankColNum = mSize - 1;
        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                if (mTiles[i][j] == 0) {
                    blankRowNum = i;
                    blankColNum = j;
                }
            }
        }

        // Check left neighbor exist or not
        if (blankColNum != 0) {
            // Change blank with left neighbor
            leftNeighbor = new int[mSize][mSize];
            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    leftNeighbor[i][j] = mTiles[i][j];
                }
            }

            leftNeighbor[blankRowNum][blankColNum] = leftNeighbor[blankRowNum][blankColNum - 1];
            leftNeighbor[blankRowNum][blankColNum - 1] = 0;
            leftNeighborBoard = new Board(leftNeighbor);
        }

        // Check right neighbor exist or not
        if (blankColNum < (mSize - 1)) {
            rightNeighbor = new int[mSize][mSize];
            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    rightNeighbor[i][j] = mTiles[i][j];
                }
            }

            rightNeighbor[blankRowNum][blankColNum] = rightNeighbor[blankRowNum][blankColNum + 1];
            rightNeighbor[blankRowNum][blankColNum + 1] = 0;
            rightNeighborBoard = new Board(rightNeighbor);
        }

        // Check top neighbor exist or not
        if (blankRowNum > 0) {
            // Change blank with top neighbor
            topNeighbor = new int[mSize][mSize];
            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    topNeighbor[i][j] = mTiles[i][j];
                }
            }

            topNeighbor[blankRowNum][blankColNum] = topNeighbor[blankRowNum - 1][blankColNum];
            topNeighbor[blankRowNum - 1][blankColNum] = 0;
            topNeighborBoard = new Board(topNeighbor);
        }

        // Check bottom neighbor exist or not
        if (blankRowNum < (mSize - 1)) {
            // Change blank with top neighbor
            bottomNeighbor = new int[mSize][mSize];
            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    bottomNeighbor[i][j] = mTiles[i][j];
                }
            }

            bottomNeighbor[blankRowNum][blankColNum] = bottomNeighbor[blankRowNum + 1][blankColNum];
            bottomNeighbor[blankRowNum + 1][blankColNum] = 0;
            bottomNeighborBoard = new Board(bottomNeighbor);
        }

        if (leftNeighborBoard != null) {
            boardList.add(leftNeighborBoard);
        }
        if (rightNeighborBoard != null) {
            boardList.add(rightNeighborBoard);
        }
        if (topNeighborBoard != null) {
            boardList.add(topNeighborBoard);
        }
        if (bottomNeighborBoard != null) {
            boardList.add(bottomNeighborBoard);
        }

        // boards = boardList.toArray();

        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {

                    private int count = 0;

                    public boolean hasNext() {
                        return count < boardList.size();
                    }

                    public Board next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return boardList.get(count++);
                    }
                };
            }
        };

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] twinLocal = new int[mSize][mSize];

        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                twinLocal[i][j] = mTiles[i][j];
            }
        }

        // Change pair
        int temp = twinLocal[randomRow][randomCol];
        twinLocal[randomRow][randomCol] = twinLocal[randomRow][randomCol + 1];
        twinLocal[randomRow][randomCol + 1] = temp;

        return new Board(twinLocal);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // do nothing
    }

}