import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * Created by Johnlee
 * Created on Thursday, September 10th, 2020 at 09:30 AM
 * Brute-force implementation.
 * java-algs4 PointSET input10K.txt
 * java-algs4 PointSET input80K.txt
 * java-algs4 PointSET input20K.txt
 * java-algs4 PointSET input500.txt  try this
 */

public class PointSET {

    private final TreeSet<Point2D> mPoint2DTreeSet;

    // construct an empty set of points
    public PointSET() {
        mPoint2DTreeSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return mPoint2DTreeSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return mPoint2DTreeSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!contains(p)) {
            mPoint2DTreeSet.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        return mPoint2DTreeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.01);
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);

        if (mPoint2DTreeSet != null) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLACK);
            for (Point2D point2D : mPoint2DTreeSet) {
                point2D.draw();
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point2D> pointList = new ArrayList<>();

        for (Point2D point2D : mPoint2DTreeSet) {
            if (rect.contains(point2D)) {
                pointList.add(point2D);
            }
        }

        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {

                    private int count = 0;

                    public boolean hasNext() {
                        return count < pointList.size();
                    }

                    public Point2D next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return pointList.get(count++);
                    }
                };
            }
        };

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            return null;
        }

        Point2D nearestPoint = mPoint2DTreeSet.first();
        double nearestDistance = p.distanceSquaredTo(mPoint2DTreeSet.first());
        double tempDistance = 0;

        for (Point2D point : mPoint2DTreeSet) {
            tempDistance = p.distanceSquaredTo(point);
            if (tempDistance < nearestDistance) {
                nearestDistance = tempDistance;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();

        double pointX = StdRandom.uniform(0.0, 1.0);
        double pointY = StdRandom.uniform(0.0, 1.0);

        Point2D queryPoint2D = new Point2D(pointX, pointY);

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.GREEN);
        queryPoint2D.draw();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        brute.draw();

        double x0 = StdRandom.uniform(0.0, 0.8);
        double y0 = StdRandom.uniform(0.0, 0.8);

        double x1 = StdRandom.uniform(x0 + 0.02, 1.0);
        double y1 = StdRandom.uniform(y0 + 0.02, 1.0);

        RectHV rect = new RectHV(x0, y0, x1, y1);

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);
        rect.draw();

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.PINK);
        for (Point2D p : brute.range(rect)) {
            p.draw();
        }

        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.RED);
        brute.nearest(queryPoint2D).draw();

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.GREEN);
        queryPoint2D.draw();

    }

}
