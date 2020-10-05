/* *****************************************************************************
 *  Name:              Johnlee
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 *  Created by Johnlee
 *  Completed on Saturday, August 16th, 2020
 */

public class Permutation {

    public static void main(String[] args) {

        int printCount = 1;
        if (args.length >= 1) printCount = Integer.parseInt(args[0]);

         // StdOut.println(printCount);

        if (args.length > 1) {

            In in = new In(args[1]);      // input file
            RandomizedQueue<String> randomQ = new RandomizedQueue<>();

            while (!in.isEmpty()) {
                String item = in.readString();
                randomQ.enqueue(item);
            }

            for (int j = 0; j < printCount; j++) {
                String item = randomQ.dequeue();
                StdOut.println(item);
            }

        }
    }
}
