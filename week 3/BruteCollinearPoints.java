import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Johnlee
 * Created on Wednesday, August 26th, 2020 at 08:55 PM
 */

public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }

        // Check duplicate arguments
        if (checkIfDuplicateElementExist(points, points.length))
            throw new IllegalArgumentException();

        int length = points.length;

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {

                int count = 0;
                int max = i;
                int min = i;

                if (points[max].compareTo(points[j]) < 0) {
                    max = j;
                }
                else if (points[min].compareTo(points[j]) > 0) {
                    min = j;
                }
                for (int k = j + 1; k < length; k++) {
                    if (points[i].slopeOrder().compare(points[j], points[k]) == 0) {
                        count++;
                        if (points[max].compareTo(points[k]) < 0) {
                            max = k;
                        }
                        else if (points[min].compareTo(points[k]) >= 0) {
                            min = k;
                        }
                    }
                }

                if (count >= 2) {
                    // find lineSegment
                    lineSegments.add(new LineSegment(points[min], points[max]));
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            result[i] = lineSegments.get(i);
        }
        return result;
    }

    private boolean checkIfDuplicateElementExist(Point[] points, int n) {
        if (n == 0 || n == 1) return false;

        Arrays.sort(points);

        // int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    // private int removeDuplicateElements(Point[] points, int n) {
    //     if (n == 0 || n == 1) {
    //         return n;
    //     }
    //
    //     Arrays.sort(points);
    //
    //     Point[] temp = new Point[n];
    //     int j = 0;
    //     for (int i = 0; i < n - 1; i++) {
    //         if (points[i].compareTo(points[i + 1]) != 0) {
    //             temp[j++] = points[i];
    //         }
    //     }
    //     temp[j++] = points[n - 1];
    //     // Changing original array
    //     for (int i = 0; i < j; i++) {
    //         points[i] = temp[i];
    //     }
    //     return j;
    // }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
