import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jiwen Li
 * Created on Wednesday, August 26th, 2020 at 08:55 PM
 */

public class FastCollinearPoints {

    private final List<LineSegment> lineSegments = new ArrayList<>();


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        double[] slopes;

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
            slopes = new double[length - i - 1];

            if (slopes.length > 0) {

                int index = 0;
                // Vertical case
                boolean hasVerticalLineSegmentFlag = false;
                int verticalCount = 1;
                int verticalMax = i;
                int verticalMin = i;

                // Horizontal case
                boolean hasHorizontalLineSegmentFlag = false;
                int horizontalCount = 1;
                int horizontalMax = i;
                int horizontalMin = i;

                for (int j = i + 1; j < length; j++) {
                    double slope = points[i].slopeTo(points[j]);

                    if (slope == Double.POSITIVE_INFINITY) {
                        // Check vertical case
                        if (points[verticalMax].compareTo(points[j]) < 0) {
                            verticalMax = j;
                        }
                        if (points[verticalMin].compareTo(points[j]) > 0) {
                            verticalMin = j;
                        }
                        verticalCount++;
                        hasVerticalLineSegmentFlag = true;
                    }
                    else if (slope == 0.0) {
                        // Check horizontal case
                        if (points[horizontalMax].compareTo(points[j]) < 0) {
                            horizontalMax = j;
                        }
                        if (points[horizontalMin].compareTo(points[j]) > 0) {
                            horizontalMin = j;
                        }
                        horizontalCount++;
                        hasHorizontalLineSegmentFlag = true;
                    }
                    else {
                        // slopes[j - (i + 1)] = slope;
                        slopes[index++] = slope;
                    }
                }

                // check vertical case
                if (hasVerticalLineSegmentFlag) {
                    if (verticalCount >= 4) {
                        lineSegments.add(new LineSegment(points[verticalMin], points[verticalMax]));
                    }
                }

                // Check horizontal case
                if (hasHorizontalLineSegmentFlag) {
                    if (horizontalCount >= 4) {
                        lineSegments.add(new LineSegment(points[horizontalMin], points[horizontalMax]));
                    }
                }

                // Check normal cases
                if (index > 2) {

                    double[] temp = new double[index];
                    for (int ii = 0; ii < index; ii++) {
                        temp[ii] = slopes[ii];
                    }
                    slopes = new double[index];
                    for (int ii = 0; ii < index; ii++) {
                        slopes[ii] = temp[ii];
                    }

                    mergeSort(slopes, slopes.length);

                    int count = 1;
                    double first = slopes[0];
                    for (int k = 1; k < slopes.length; k++) {
                        if (slopes[k] == first) {
                            count++;
                        }
                        else {

                            if (count >= 3) {
                                // find lineSegment with more than 4 points

                                int h = 0;
                                int[] indexs = new int[count + 1];
                                indexs[h++] = i;

                                for (int w = i + 1; w < length; w++) {
                                    if (points[i].slopeTo(points[w]) == first) {
                                        indexs[h++] = w;
                                    }
                                }
                                int max = getMax(indexs, points);
                                int min = getMin(indexs, points);
                                lineSegments.add(new LineSegment(points[min], points[max]));
                            }

                            first = slopes[k];
                            count = 1;
                        }

                    }
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

    private int getMax(int[] a, Point[] points) {
        int max = a[0];
        for (int index : a) {
            if (points[max].compareTo(points[index]) < 0) {
                max = index;
            }
        }
        return max;
    }

    private int getMin(int[] a, Point[] points) {
        int min = a[0];
        for (int index : a) {
            if (points[min].compareTo(points[index]) > 0) {
                min = index;
            }
        }
        return min;
    }

    private void mergeSort(double[] a, int n) {
        if (n < 2)
            return;
        int mid = n / 2;
        double[] leftPart = new double[mid];
        double[] rightPart = new double[n - mid];

        for (int i = 0; i < mid; i++) {
            leftPart[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            rightPart[i - mid] = a[i];
        }
        mergeSort(leftPart, mid);
        mergeSort(rightPart, n - mid);

        merge(a, leftPart, rightPart, mid, n - mid);
    }

    private void merge(double[] a, double[] leftPart, double[] rightPart, int left, int right) {

        int i = 0, j = 0, k = 0;

        while (i < left && j < right) {

            if (leftPart[i] <= rightPart[j])
                a[k++] = leftPart[i++];
            else
                a[k++] = rightPart[j++];

        }

        while (i < left)
            a[k++] = leftPart[i++];

        while (j < right)
            a[k++] = rightPart[j++];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}