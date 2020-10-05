import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Johnlee
 * Created on Thursday, September 10th, 2020 at 09:30 AM
 * 2d-tree implementation.
 * java-algs4 KdTree input10.txt
 * java-algs4 KdTree input10K.txt
 * java-algs4 KdTree input20K.txt
 * java-algs4 KdTree input500.txt  try this
 */

public class KdTree {

    private KdTreeNode firstKdTreeNode;
    private final List<Point2D> kdTreeNodesPoint2DList = new ArrayList<>();
    private boolean checkContainFindFlag;


    private class KdTreeNode {
        private Point2D point2D;
        private final int level;
        private RectHV rectHV;
        private KdTreeNode leftNode;
        private KdTreeNode rightNode;

        public KdTreeNode(Point2D point2D, int level) {
            this.point2D = point2D;
            this.level = level;
        }

        public boolean contains(Point2D item) {

            boolean result = false;

            if (checkContainFindFlag) return false;

            if (level % 2 == 0) {
                // even level, compare x coordinate to judge left or right
                if (point2D.x() > item.x()) {
                    // left
                    if (leftNode != null) {
                        result |= leftNode.contains(item);
                    }
                }
                else if (point2D.x() < item.x()) {
                    // right
                    if (rightNode != null) {
                        result |= rightNode.contains(item);
                    }
                }
                else if (point2D.x() == item.x()) {
                    // x equal, use y to decide right or left
                    if (point2D.y() > item.y()) {
                        // left
                        if (leftNode != null) {
                            result |= leftNode.contains(item);
                        }
                    }
                    else if (point2D.y() < item.y()) {
                        // right
                        if (rightNode != null) {
                            result |= rightNode.contains(item);
                        }
                    }
                    else {
                        // x equal and y equal
                        // it's duplicate point
                        // find it
                        checkContainFindFlag = true;
                        return true;
                    }
                }
            }
            else {
                // odd level, compare y coordinate to judge top or down
                if (point2D.y() > item.y()) {
                    // down
                    if (leftNode != null) {
                        result |= leftNode.contains(item);
                    }
                }
                else if (point2D.y() < item.y()) {
                    // up
                    if (rightNode != null) {
                        result |= rightNode.contains(item);
                    }
                }
                else if (point2D.y() == item.y()) {
                    // y equal, use x to decide right or left
                    if (point2D.x() > item.x()) {
                        // down
                        if (leftNode != null) {
                            result |= leftNode.contains(item);
                        }
                    }
                    else if (point2D.x() < item.x()) {
                        // up
                        if (rightNode != null) {
                            result |= rightNode.contains(item);
                        }
                    }
                    else {
                        // y equal and x equal
                        // it's duplicate point
                        // find it
                        checkContainFindFlag = true;
                        return true;
                    }
                }
            }

            // no matching node was found
            return result;
        }

        public void setPoint2D(Point2D point2D) {
            this.point2D = point2D;
        }

        public void setLeftNode(KdTreeNode left) {
            this.leftNode = left;
        }

        public void setRightNode(KdTreeNode right) {
            this.rightNode = right;
        }

        public void setRectHV(RectHV rectHV) {
            this.rectHV = rectHV;
        }

        public Point2D getPoint2D() {
            return point2D;
        }

        public int getLevel() {
            return level;
        }

        public RectHV getRectHV() {
            return rectHV;
        }

        public KdTreeNode getLeftNode() {
            return leftNode;
        }

        public KdTreeNode getRightNode() {
            return rightNode;
        }
    }

    // construct an empty set of points
    public KdTree() {
        firstKdTreeNode = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return firstKdTreeNode == null;
    }

    // number of points in the set
    public int size() {
        return kdTreeNodesPoint2DList.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (firstKdTreeNode == null) {
            firstKdTreeNode = new KdTreeNode(p, 0);
            firstKdTreeNode.leftNode = null;
            firstKdTreeNode.rightNode = null;
            firstKdTreeNode.setRectHV(new RectHV(0, 0, 1, 1));
            kdTreeNodesPoint2DList.add(firstKdTreeNode.getPoint2D());
        }
        else {
            // if (!contains(p)) {
            insertNewTdTreeNode(p, firstKdTreeNode, 0);
            // firstKdTreeNode.insert(p);
            // }
        }
    }


    private void insertNewTdTreeNode(Point2D p, KdTreeNode treeNode, int level) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        // avoid duplicate point
        // if (treeNode.getPoint2D().equals(p)) return;

        if (level % 2 == 0) {
            // even level, compare x coordinate to judge left or right
            if (treeNode.getPoint2D().x() > p.x()) {
                // left
                if (treeNode.leftNode == null) {
                    KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                    tempKdTreeNode.leftNode = null;
                    tempKdTreeNode.rightNode = null;
                    tempKdTreeNode.setRectHV(
                            new RectHV(treeNode.getRectHV().xmin(), treeNode.getRectHV().ymin(),
                                       treeNode.getPoint2D().x(), treeNode.getRectHV().ymax()));
                    treeNode.leftNode = tempKdTreeNode;
                    kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                }
                else {
                    insertNewTdTreeNode(p, treeNode.leftNode, level + 1);
                }
            }
            else if (treeNode.getPoint2D().x() < p.x()) {
                // right
                if (treeNode.rightNode == null) {
                    KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                    tempKdTreeNode.leftNode = null;
                    tempKdTreeNode.rightNode = null;
                    tempKdTreeNode.setRectHV(
                            new RectHV(treeNode.getPoint2D().x(), treeNode.getRectHV().ymin(),
                                       treeNode.getRectHV().xmax(), treeNode.getRectHV().ymax()));
                    treeNode.rightNode = tempKdTreeNode;
                    kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                }
                else {
                    insertNewTdTreeNode(p, treeNode.rightNode, level + 1);
                }
            }
            else if (treeNode.getPoint2D().x() == p.x()) {
                // x equal, use y to decide right or left
                if (treeNode.getPoint2D().y() > p.y()) {
                    // left
                    if (treeNode.leftNode == null) {
                        KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                        tempKdTreeNode.leftNode = null;
                        tempKdTreeNode.rightNode = null;
                        tempKdTreeNode.setRectHV(
                                new RectHV(treeNode.getRectHV().xmin(), treeNode.getRectHV().ymin(),
                                           treeNode.getPoint2D().x(), treeNode.getRectHV().ymax()));
                        treeNode.leftNode = tempKdTreeNode;
                        kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                    }
                    else {
                        insertNewTdTreeNode(p, treeNode.leftNode, level + 1);
                    }
                }
                else if (treeNode.getPoint2D().y() < p.y()) {
                    // right
                    if (treeNode.rightNode == null) {
                        KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                        tempKdTreeNode.leftNode = null;
                        tempKdTreeNode.rightNode = null;
                        tempKdTreeNode.setRectHV(
                                new RectHV(treeNode.getPoint2D().x(), treeNode.getRectHV().ymin(),
                                           treeNode.getRectHV().xmax(),
                                           treeNode.getRectHV().ymax()));
                        treeNode.rightNode = tempKdTreeNode;
                        kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                    }
                    else {
                        insertNewTdTreeNode(p, treeNode.rightNode, level + 1);
                    }
                }
                else {
                    // x equal and y equal
                    // it's duplicate point
                    // Skip
                }
            }
        }
        else {
            // odd level, compare y coordinate to judge top or down
            if (treeNode.getPoint2D().y() > p.y()) {
                // down
                if (treeNode.leftNode == null) {
                    KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                    tempKdTreeNode.leftNode = null;
                    tempKdTreeNode.rightNode = null;
                    tempKdTreeNode.setRectHV(
                            new RectHV(treeNode.getRectHV().xmin(), treeNode.getRectHV().ymin(),
                                       treeNode.getRectHV().xmax(), treeNode.getPoint2D().y()));
                    treeNode.leftNode = tempKdTreeNode;
                    kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                }
                else {
                    insertNewTdTreeNode(p, treeNode.leftNode, level + 1);
                }
            }
            else if (treeNode.getPoint2D().y() < p.y()) {
                // up
                if (treeNode.rightNode == null) {
                    KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                    tempKdTreeNode.leftNode = null;
                    tempKdTreeNode.rightNode = null;
                    tempKdTreeNode.setRectHV(
                            new RectHV(treeNode.getRectHV().xmin(), treeNode.getPoint2D().y(),
                                       treeNode.getRectHV().xmax(), treeNode.getRectHV().ymax()));
                    treeNode.rightNode = tempKdTreeNode;
                    kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                }
                else {
                    insertNewTdTreeNode(p, treeNode.rightNode, level + 1);
                }
            }
            else if (treeNode.getPoint2D().y() == p.y()) {
                // y equal, use x to decide right or left
                if (treeNode.getPoint2D().x() > p.x()) {
                    // down
                    if (treeNode.leftNode == null) {
                        KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                        tempKdTreeNode.leftNode = null;
                        tempKdTreeNode.rightNode = null;
                        tempKdTreeNode.setRectHV(
                                new RectHV(treeNode.getRectHV().xmin(), treeNode.getRectHV().ymin(),
                                           treeNode.getRectHV().xmax(), treeNode.getPoint2D().y()));
                        treeNode.leftNode = tempKdTreeNode;
                        kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                    }
                    else {
                        insertNewTdTreeNode(p, treeNode.leftNode, level + 1);
                    }
                }
                else if (treeNode.getPoint2D().x() < p.x()) {
                    // up
                    if (treeNode.rightNode == null) {
                        KdTreeNode tempKdTreeNode = new KdTreeNode(p, level + 1);
                        tempKdTreeNode.leftNode = null;
                        tempKdTreeNode.rightNode = null;
                        tempKdTreeNode.setRectHV(
                                new RectHV(treeNode.getRectHV().xmin(), treeNode.getPoint2D().y(),
                                           treeNode.getRectHV().xmax(),
                                           treeNode.getRectHV().ymax()));
                        treeNode.rightNode = tempKdTreeNode;
                        kdTreeNodesPoint2DList.add(tempKdTreeNode.getPoint2D());
                    }
                    else {
                        insertNewTdTreeNode(p, treeNode.rightNode, level + 1);
                    }
                }
                else {
                    // y equal and x equal
                    // it's duplicate point
                    // Skip
                }
            }
        }


    }

    // does the set contain point p?
    // private boolean findFlag = false;
    //
    // public boolean contains(Point2D p) {
    //
    //     if (p == null) {
    //         throw new IllegalArgumentException();
    //     }
    //
    //     findFlag = false;
    //     return checkContainPoint(firstKdTreeNode, p);
    //     //  return kdTreeNodesPoint2DList.contains(p);
    // }

    public boolean contains(Point2D item) {
        if (firstKdTreeNode == null) return false;

        checkContainFindFlag = false;

        return firstKdTreeNode.contains(item);
    }

    // private boolean checkContainPoint(KdTreeNode node, Point2D p) {
    //
    //     boolean result = false;
    //
    //     // if (findFlag) return false;
    //     if (node == null) return false;
    //
    //     if (node.getPoint2D().equals(p)) {
    //         findFlag = true;
    //         return true;
    //     }
    //
    //     if (node.leftNode != null && !findFlag) {
    //         result |= checkContainPoint(node.leftNode, p);
    //     }
    //     if (node.rightNode != null && !findFlag) {
    //         result |= checkContainPoint(node.rightNode, p);
    //     }
    //
    //     return result;
    // }

    // draw all points to standard draw
    public void draw() {

        if (size() == 0) return;

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.01);
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        drawKdTree(firstKdTreeNode);
    }

    private void drawKdTree(KdTreeNode treeNode) {

        if (treeNode == null) return;

        if (treeNode.getLevel() % 2 == 0) {
            // draw vertical line with Red color
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(treeNode.getPoint2D().x(), treeNode.getRectHV().ymin(),
                         treeNode.getPoint2D().x(), treeNode.getRectHV().ymax());
        }
        else {
            // draw horizontal line with blue color
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(treeNode.getRectHV().xmin(), treeNode.getPoint2D().y(),
                         treeNode.getRectHV().xmax(), treeNode.getPoint2D().y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(treeNode.getPoint2D().x(), treeNode.getPoint2D().y());

        drawKdTree(treeNode.leftNode);
        drawKdTree(treeNode.rightNode);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<KdTreeNode> nodeList = new ArrayList<>();

        rangeChecking(rect, firstKdTreeNode, nodeList);

        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {

                    private int count = 0;

                    public boolean hasNext() {
                        return count < nodeList.size();
                    }

                    public Point2D next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return nodeList.get(count++).getPoint2D();
                    }
                };
            }
        };

    }

    private void rangeChecking(RectHV rect, KdTreeNode treeNode, List<KdTreeNode> nodeList) {

        if (treeNode == null) return;
        if (!rect.intersects(treeNode.getRectHV())) return;

        if (rect.contains(treeNode.getPoint2D())) {
            nodeList.add(treeNode);
        }

        // check rectangle intersection
        rangeChecking(rect, treeNode.leftNode, nodeList);
        rangeChecking(rect, treeNode.rightNode, nodeList);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        if (p == null) {
            throw new IllegalArgumentException();
        }

        KdTreeNode nearestNode = firstKdTreeNode;
        nearestPointChecking(p, firstKdTreeNode, nearestNode, Integer.MAX_VALUE);

        if (nearestNode == null) {
            return null;
        }
        else {
            return nearestNode.getPoint2D();
        }
    }

    private void nearestPointChecking(Point2D queryPoint, KdTreeNode treeNode,
                                      KdTreeNode nearestNode, double shortestLen) {

        if (treeNode == null) return;

        // if (shortestLen < treeNode.getRectHV().distanceSquaredTo(queryPoint) && !treeNode.getRectHV().contains(queryPoint)) return;

        double distance = queryPoint.distanceSquaredTo(treeNode.getPoint2D());

        if (treeNode.getLevel() % 2 == 0) {
            // compare x coordinate
            if (treeNode.getPoint2D().x() > queryPoint.x()) {
                // left
                if (shortestLen > distance) {
                    shortestLen = distance;
                    nearestNode.setPoint2D(treeNode.getPoint2D());
                    // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                    // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                }

                if (treeNode.leftNode != null && (
                        (shortestLen > treeNode.leftNode.getRectHV().distanceSquaredTo(queryPoint))
                                || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                    nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                }
                // test
                // if (treeNode.rightNode != null && (
                //         (shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint))
                //                 || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                //     nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                // }
            }
            else if (treeNode.getPoint2D().x() < queryPoint.x()) {
                // right
                if (shortestLen > distance) {
                    shortestLen = distance;
                    nearestNode.setPoint2D(treeNode.getPoint2D());
                    // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                    // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                }

                if (treeNode.rightNode != null && (
                        (shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint))
                                || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                    // if (treeNode.rightNode != null && shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint)) {
                    nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                }
                // test
                // if (treeNode.leftNode != null && (
                //         (shortestLen > treeNode.leftNode.getRectHV().distanceSquaredTo(queryPoint))
                //                 || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                //     nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                // }
            }
            else if (treeNode.getPoint2D().x() == queryPoint.x()) {
                // x equal, use y to decide right or left
                if (treeNode.getPoint2D().y() > queryPoint.y()) {
                    // left
                    if (shortestLen > distance) {
                        shortestLen = distance;
                        nearestNode.setPoint2D(treeNode.getPoint2D());
                        // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                        // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                    }

                    if (treeNode.leftNode != null && (
                            (shortestLen > treeNode.leftNode.getRectHV()
                                                            .distanceSquaredTo(queryPoint))
                                    || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                        nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode,
                                             shortestLen);
                    }
                    if (treeNode.rightNode != null && (
                            (shortestLen > treeNode.rightNode.getRectHV()
                                                             .distanceSquaredTo(queryPoint))
                                    || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                        // if (treeNode.rightNode != null && shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint)) {
                        nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode,
                                             shortestLen);
                    }

                }
                else if (treeNode.getPoint2D().y() < queryPoint.y()) {
                    // right
                    if (shortestLen > distance) {
                        shortestLen = distance;
                        nearestNode.setPoint2D(treeNode.getPoint2D());
                        // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                        // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                    }

                    if (treeNode.rightNode != null && (
                            (shortestLen > treeNode.rightNode.getRectHV()
                                                             .distanceSquaredTo(queryPoint))
                                    || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                        // if (treeNode.rightNode != null && shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint)) {
                        nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode,
                                             shortestLen);
                    }
                    if (treeNode.leftNode != null && (
                            (shortestLen > treeNode.leftNode.getRectHV()
                                                            .distanceSquaredTo(queryPoint))
                                    || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                        nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode,
                                             shortestLen);
                    }

                }
                else {
                    // x equal and y equal
                    // current node point is the query point
                    if (shortestLen > distance) {
                        shortestLen = distance;
                        nearestNode.setPoint2D(treeNode.getPoint2D());
                        // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                        // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                    }

                    // already find a point the same as query point , so the nearest distance is 0, no need to continue searching.
                }

            }
        }
        else {
            // compare y coordinate
            if (treeNode.getPoint2D().y() > queryPoint.y()) {
                // down
                if (shortestLen > distance) {
                    shortestLen = distance;
                    nearestNode.setPoint2D(treeNode.getPoint2D());
                    // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                    // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                }

                if (treeNode.leftNode != null && (
                        (shortestLen > treeNode.leftNode.getRectHV().distanceSquaredTo(queryPoint))
                                || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                    nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                }
                // test
                // if (treeNode.rightNode != null && (
                //         (shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint))
                //                 || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                //     nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                // }
            }
            else if (treeNode.getPoint2D().y() < queryPoint.y()) {
                // up
                if (shortestLen > distance) {
                    shortestLen = distance;
                    nearestNode.setPoint2D(treeNode.getPoint2D());
                    // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                    // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                }

                if (treeNode.rightNode != null && (
                        (shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint))
                                || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                    nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                }
                // test
                // if (treeNode.leftNode != null && (
                //         (shortestLen > treeNode.leftNode.getRectHV().distanceSquaredTo(queryPoint))
                //                 || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                //     nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                // }
            }
            else if (treeNode.getPoint2D().y() == queryPoint.y()) {
                // y equal, use x to decide right or left

                if (treeNode.getPoint2D().x() > queryPoint.x()) {
                    // left
                    if (shortestLen > distance) {
                        shortestLen = distance;
                        nearestNode.setPoint2D(treeNode.getPoint2D());
                        // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                        // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                    }

                    if (treeNode.leftNode != null && (
                            (shortestLen > treeNode.leftNode.getRectHV()
                                                            .distanceSquaredTo(queryPoint))
                                    || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                        nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode,
                                             shortestLen);
                    }
                    if (treeNode.rightNode != null && (
                            (shortestLen > treeNode.rightNode.getRectHV()
                                                             .distanceSquaredTo(queryPoint))
                                    || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                        // if (treeNode.rightNode != null && shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint)) {
                        nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode,
                                             shortestLen);
                    }

                }
                else if (treeNode.getPoint2D().x() < queryPoint.x()) {
                    // right
                    if (shortestLen > distance) {
                        shortestLen = distance;
                        nearestNode.setPoint2D(treeNode.getPoint2D());
                        // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                        // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                    }

                    if (treeNode.rightNode != null && (
                            (shortestLen > treeNode.rightNode.getRectHV()
                                                             .distanceSquaredTo(queryPoint))
                                    || (treeNode.rightNode.getRectHV().contains(queryPoint)))) {
                        // if (treeNode.rightNode != null && shortestLen > treeNode.rightNode.getRectHV().distanceSquaredTo(queryPoint)) {
                        nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode,
                                             shortestLen);
                    }
                    if (treeNode.leftNode != null && (
                            (shortestLen > treeNode.leftNode.getRectHV()
                                                            .distanceSquaredTo(queryPoint))
                                    || (treeNode.leftNode.getRectHV().contains(queryPoint)))) {
                        nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode,
                                             shortestLen);
                    }

                }
                else {
                    // y equal and x equal
                    // current node point is the query point
                    if (shortestLen > distance) {
                        shortestLen = distance;
                        nearestNode.setPoint2D(treeNode.getPoint2D());
                        // nearestPointChecking(queryPoint, treeNode.rightNode, nearestNode, shortestLen);
                        // nearestPointChecking(queryPoint, treeNode.leftNode, nearestNode, shortestLen);
                    }

                    // already find a point the same as query point , so the nearest distance is 0, no need to continue searching.
                }
            }
        }

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        KdTree kdTree = new KdTree();

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
            kdTree.insert(p);
        }

        kdTree.draw();

        double x0 = StdRandom.uniform(0.0, 0.8);
        double y0 = StdRandom.uniform(0.0, 0.8);

        double x1 = StdRandom.uniform(x0 + 0.02, 1.0);
        double y1 = StdRandom.uniform(y0 + 0.02, 1.0);

        RectHV rect = new RectHV(x0, y0, x1, y1);

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);
        rect.draw();

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);

        for (Point2D p : kdTree.range(rect)) {
            p.draw();
        }

        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.RED);
        kdTree.nearest(queryPoint2D).draw();

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.GREEN);
        queryPoint2D.draw();

    }

}
