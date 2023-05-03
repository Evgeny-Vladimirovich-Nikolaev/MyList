package main.java.list.linkedList;

import main.java.list.MyList;
import main.java.list.myListExceptions.OutOfRangeException;

import java.util.NoSuchElementException;

public class MyLinkedList <E> implements MyList {

    private int size;
    private Node first;
    private Node last;
    private final static int MAX = 2147483647;
    private static final String EXCEEDING_MAXIMUM_NUMBER = "Exceeding the maximum possible number of items in the MyLinkedList";
    private static final String NONEXISTENT_INDEX = "attempt to add an element with a nonexistent index or with an index larger than the size of the MyLinkedList";

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(Object element) throws OutOfRangeException {
        if(size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if(size == 0) {
            first = last = new Node(element);
        } else {
            final Node current = new Node(element);
            first.setNext(last);
            last.setPrevious(first);
            last.setNext(current);
            current.setPrevious(last);
            last = current;
        }
        size++;
        return true;
    }

    @Override
    public boolean add(Object element, int index) throws OutOfRangeException, IndexOutOfBoundsException {
        if(size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException(NONEXISTENT_INDEX);
        }
        final Node node = new Node(element);
        if(size == 0) {
            first = last = node;
        } else if(index == 0) {
            first.setPrevious(node);
            node.setNext(first);
            first = node;
        } else if(index == size) {
            last.setNext(node);
            node.setPrevious(last);
            last = node;
        } else {
            int currentIndex = 1;
            Node currentNode = first.getNext();
            while(index != currentIndex) {
                currentNode = currentNode.getNext();
                currentIndex++;
            }
            final Node previous = currentNode.getPrevious();
            previous.setNext(node);
            node.setPrevious(previous);
            node.setNext(currentNode);
            currentNode.setPrevious(node);
        }
        size++;
        return true;
    }

    @Override
    public boolean addAll(Object[] arr) throws OutOfRangeException {
        if(size + arr.length > MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if(arr.length == 0) {
            return true;
        }
        if(size == 0) {
            last = first = new Node(arr[0]);
        }
        Node currentNode = last;
        Node next = null;
        for(int i = 1; i < arr.length; i++) {
            next = new Node(arr[i]);
            currentNode.setNext(next);
            next.setPrevious(currentNode);
        }
        last = next;
        size += arr.length;
        return true;
    }

    @Override
    public boolean addAll(Object[] arr, int index)  throws OutOfRangeException, IndexOutOfBoundsException {
        return false;
    }

    @Override
    public E remove(Object element) throws NoSuchElementException {
        return null;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    @Override
    public boolean set(Object element, int index) throws IndexOutOfBoundsException {
        return false;
    }

    @Override
    public boolean contains(Object element) {
        return false;
    }

    @Override
    public Object get(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public int getIndexOf(Object element) throws NoSuchElementException {
        return 0;
    }

    @Override
    public boolean retain(Object element) throws NoSuchElementException {
        return false;
    }

    @Override
    public boolean retainAll(Object[] arr) throws NoSuchElementException {
        return false;
    }
}

class Node <E> {

    private E element;
    private Node previous;
    private Node next;

    Node(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
