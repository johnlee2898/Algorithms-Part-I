import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Johnlee
 * Created on Saturday, August 29th, 2020 at 01:40 PM
 */

public class Solver {

    private Board finalSolvableBoard;
    private int moveNum;
    private List<SearchNode> mBoardSequence;
    private final Comparator<SearchNode> mByManhattanPriority = new ByManhattanPriority();
    private final Comparator<SearchNode> mByHammingPriority = new ByHammingPriority();

    private boolean completeFlag = false;

    private enum Phrases {
        PHRASE1,
        PHRASE2,
        PHRASE3,
        PHRASE4,
        END
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        completeFlag = false;
        moveNum = 0;
        finalSolvableBoard = null;
        mBoardSequence = new ArrayList<>();
        execution(initial);
    }

    private void execution(Board initial) {

        MinPQ<SearchNode> mMinPQ;

        // use A* algorithm to find solution
        // Start with initial board
        SearchNode boardOut = new SearchNode(initial, 0);
        mBoardSequence.add(boardOut);
        int index = 0;
        int PHRASE1TRYCOUNT = 20;
        int phrase1TryIndex = 0;
        int PHRASE2TRYCOUNT = 50;
        int phrase2TryIndex = 0;
        int PHRASE3TRYCOUNT = 100;
        int phrase3TryIndex = 0;
        int PHRASE4TRYCOUNT = 300;
        int phrase4TryIndex = 0;

        boolean isSwapFlag = false;

        Phrases phrases = Phrases.PHRASE1;

        while (!(boardOut.getBoard().isGoal())) {

            mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);

            Iterable<Board> iterableBoard = boardOut.getBoard().neighbors();

            for (Board board : iterableBoard) {
                if (index > 0 && !((mBoardSequence.get(index - 1).getBoard()).equals(board))) {

                    mMinPQ.insert(new SearchNode(board, moveNum + 1));
                }
                else if (index == 0) {
                    mMinPQ.insert(new SearchNode(board, moveNum + 1));
                }
            }
            boardOut = mMinPQ.delMin();
            // StdOut.println("mMinPQ.delMin():  boardOut ==" + boardOut);
            mBoardSequence.add(boardOut);
            index++;
            moveNum++;

            if (index == PHRASE3TRYCOUNT) {
                isSwapFlag = false;
                boardOut = null;
                mBoardSequence.clear();
                mBoardSequence.add(boardOut);
                break;
            }

            // if (phrases == Phrases.PHRASE1) {
            //     if (index == PHRASE1TRYCOUNT && phrase1TryIndex < 2) {
            //
            //         if (phrase1TryIndex == 0) {
            //             // Alternate to twin board
            //             isSwapFlag = true;
            //             boardOut = new SearchNode(initial.twin(), 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             phrase1TryIndex++;
            //             StdOut.println("Phrase1 twin retry");
            //         }
            //         else {
            //             // twin phrase1 tried, but no result
            //             // switch to phrase2
            //             isSwapFlag = false;
            //             phrases = Phrases.PHRASE2;
            //             boardOut = new SearchNode(initial, 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             StdOut.println("Phrase2 retry");
            //         }
            //
            //     }
            // }
            // else if (phrases == Phrases.PHRASE2) {
            //     if (index == PHRASE2TRYCOUNT && phrase2TryIndex < 2) {
            //
            //         if (phrase2TryIndex == 0) {
            //             // Alternate to twin board
            //             isSwapFlag = true;
            //             boardOut = new SearchNode(initial.twin(), 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             phrase2TryIndex++;
            //             StdOut.println("Phrase2 twin retry");
            //         }
            //         else {
            //             // twin phrase2 tried, but no result
            //             // switch to phrase3
            //             isSwapFlag = false;
            //             phrases = Phrases.PHRASE3;
            //             boardOut = new SearchNode(initial, 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             StdOut.println("Phrase3 retry");
            //         }
            //
            //     }
            // }
            // else if (phrases == Phrases.PHRASE3) {
            //     if (index == PHRASE3TRYCOUNT && phrase3TryIndex < 2) {
            //
            //         if (phrase3TryIndex == 0) {
            //             // Alternate to twin board
            //             isSwapFlag = true;
            //             boardOut = new SearchNode(initial.twin(), 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             phrase3TryIndex++;
            //             StdOut.println("Phrase3 twin retry");
            //         }
            //         else {
            //             // twin phrase3 tried, but no result
            //             // switch to phrase4
            //             isSwapFlag = false;
            //             phrases = Phrases.PHRASE4;
            //             boardOut = new SearchNode(initial, 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             StdOut.println("Phrase4 retry");
            //         }
            //
            //     }
            // }
            // else  if (phrases == Phrases.PHRASE4) {
            //     // Phrase4
            //     if (index == PHRASE4TRYCOUNT && phrase4TryIndex < 2) {
            //
            //         if (phrase4TryIndex == 0) {
            //             // Alternate to twin board
            //             isSwapFlag = true;
            //             boardOut = new SearchNode(initial.twin(), 0);
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             phrase4TryIndex++;
            //             StdOut.println("Phrase4 twin retry");
            //         }
            //         else {
            //             // twin phrase4 tried, but no result
            //             // switch to phrase5 -- END
            //             isSwapFlag = false;
            //             phrases = Phrases.END;
            //             boardOut = null;
            //             mBoardSequence.clear();
            //             mBoardSequence.add(boardOut);
            //             mMinPQ = new MinPQ<SearchNode>(mByManhattanPriority);
            //             index = 0;
            //             moveNum = 0;
            //             StdOut.println("Phrase5 END");
            //
            //             break;
            //         }
            //
            //     }
            //}

        }

        if (!isSwapFlag) {
            if (boardOut != null) {
                finalSolvableBoard = boardOut.getBoard();
            }
            else {
                finalSolvableBoard = null;
            }
        }
        else {
            finalSolvableBoard = null;
        }
        completeFlag = true;
    }

    private class SearchNode {

        private final Board mBoard;
        private final int mManhattanValue;
        private final int mHamming;
        private final int mMoveNum;

        public SearchNode(Board board, int moveCount) {
            this.mBoard = board;
            this.mManhattanValue = this.mBoard.manhattan();
            this.mHamming = this.mBoard.hamming();
            this.mMoveNum = moveCount;
        }

        public Board getBoard() {
            return mBoard;
        }

        public int getManhattanValue() {
            return mManhattanValue;
        }

        public int getMoveNum() {
            return mMoveNum;
        }

        public int getHamming() {
            return mHamming;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {

        int j = 1;
        while (!completeFlag) {
            // waiting for complete
            for (int i = 0; i < 5; i++) {
                j += 5;
                if (j == 245) {
                    j = 0;
                }
            }
        }
        return finalSolvableBoard != null && finalSolvableBoard.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {

        if (!isSolvable()) {
            return -1;
        }
        else {
            return moveNum;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!isSolvable()) {
            return null;
        }
        else {
            return new Iterable<Board>() {
                public Iterator<Board> iterator() {
                    return new Iterator<Board>() {

                        private int count = 0;

                        public boolean hasNext() {
                            return count < mBoardSequence.size();
                        }

                        public Board next() {
                            if (!hasNext()) throw new NoSuchElementException();
                            return mBoardSequence.get(count++).getBoard();
                        }
                    };
                }
            };
        }

    }

    private class ByManhattanPriority implements Comparator<SearchNode> {

        public int compare(SearchNode t0, SearchNode t1) {
            return t0.getManhattanValue() + t0.getMoveNum() - (t1.getManhattanValue() + t1
                    .getMoveNum());
        }
    }

    private class ByHammingPriority implements Comparator<SearchNode> {

        public int compare(SearchNode t0, SearchNode t1) {
            return t0.getHamming() + t0.getMoveNum() - (t1.getHamming() + t1.getMoveNum());
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        // for each command-line argument
        // for (String filename : args) {
        //
        //     // read in the board specified in the filename
        //     In in = new In(filename);
        //     int n = in.readInt();
        //     int[][] tiles = new int[n][n];
        //     for (int i = 0; i < n; i++) {
        //         for (int j = 0; j < n; j++) {
        //             tiles[i][j] = in.readInt();
        //         }
        //     }
        //
        //     // solve the slider puzzle
        //     Board initial = new Board(tiles);
        //     Solver solver = new Solver(initial);
        //     StdOut.println(filename + ": " + solver.moves());
        // }

    }

}
