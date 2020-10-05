/* *****************************************************************************
 *  Name:              Johnlee
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Johnlee
 * Completed on Saturday, August 16th, 2020
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }


    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        // For Queue case

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            first = last;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            oldLast.next = last;
        }
        size++;

    }

    // remove and return a random item
    public Item dequeue() {

        // For Queue case

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node tempIndex = first;
        Node previousNode = null;

        int i = 0;
        int randomIndex = StdRandom.uniform(0, size()); // 0 to size -1
        Item item;

        if (first != null) {
            item = first.item;

            if (size() == 1) {
                first = null;
                last = null;
            }
            else {

                while (i != randomIndex && i < randomIndex) {
                    previousNode = tempIndex;
                    tempIndex = tempIndex.next;
                    i++;
                }

                if (randomIndex == 0) {
                    item = tempIndex.item;
                    first = first.next;
                    // if (first.next == null) {
                    //     last = first;
                    // }
                }
                else if (tempIndex != null) {
                    item = tempIndex.item;
                    previousNode.next = tempIndex.next;

                    if (previousNode.next == null) {
                        last = previousNode;
                    }
                }
                else {
                    item = null;
                    last = null;
                }
            }

        }
        else {
            last = null;
            item = null;
        }
        size--;

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node tempIndex = first;

        int i = 0;
        int randomIndex = StdRandom.uniform(0, size()); // 0 to size -1
        Item item;

        if (first != null) {
            item = first.item;

            if (size() == 1) {
                first = null;
                last = null;
            }
            else {

                while (i != randomIndex && i < randomIndex) {
                    tempIndex = tempIndex.next;
                    i++;
                }

                if (randomIndex == 0) {
                    item = tempIndex.item;
                }
                else if (tempIndex != null) {
                    item = tempIndex.item;
                }
                else {
                    item = null;
                    last = null;
                }
            }

        }
        else {
            last = null;
            item = null;
        }

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {

            Item item;

            if (current != null) {
                item = current.item;
                current = current.next;
            }
            else {
                throw new NoSuchElementException();
            }

            return item;
        }

        public void remove() {
            // Not support
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // do nothing
    }

}
