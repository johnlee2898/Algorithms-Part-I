/* *****************************************************************************
 *  Name:              Jiwen Li
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Jiwen Li
 * Completed on Saturday, August 16th, 2020
 */

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        // For Stack case

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size() == 0) {
            first = new Node();
            first.item = item;
            first.next = null;
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
        }
        size++;

    }

    // add the item to the back
    public void addLast(Item item) {
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

    // remove and return the item from the front
    public Item removeFirst() {
        // For Stack case

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        size--;

        if (first == null) {
            last = null;
            size = 0;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        // For Queue case

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node tempFirst = first;
        Node tempFirstOld = null;

        while (tempFirst.next != null) {
            tempFirstOld = tempFirst;
            tempFirst = tempFirst.next;
        }

        Item item = last.item;
        if (tempFirstOld != null) {
            tempFirstOld.next = null;
            last = tempFirstOld;
        }
        else {
            first = null;
            last = null;
        }
        size--;

        return item;
    }

    // return an iterator over items in order from front to back
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
