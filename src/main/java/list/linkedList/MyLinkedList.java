package main.java.list.linkedList;

import main.java.list.MyList;
import main.java.list.myListExceptions.OutOfRangeException;

import java.lang.ref.PhantomReference;
import java.util.NoSuchElementException;

public class MyLinkedList <E> implements MyList {

    private int size;
    private Node first;
    private Node last;
    private final static int MAX = 2147483647;
    private static final String EXCEEDING_MAXIMUM_NUMBER
            = "Exceeding the maximum possible number of items in the MyLinkedList";
    private static final String ADDING_ON_NONEXISTENT_INDEX
            = "attempt to add an element with a nonexistent index or with an index larger than the size of the MyLinkedList";
    private static final String REMOVING_NONEXISTENT_ELEMENT = "attempt to delete a non-existent element";
    private static final String REMOVING_ON_NONEXISTENT_INDEX
            = "attempt to remove an element with a nonexistent index";
    private static final String ACCESSING_A_NONEXISTENT_INDEX = "accessing a non-existent index";


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
            final Node node = new Node(element);
            last.setNext(node);
            node.setPrevious(last);
            last = node;
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
            throw new IndexOutOfBoundsException(ADDING_ON_NONEXISTENT_INDEX);
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
            while(currentIndex != index) {
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
            currentNode = next;
        }
        last = next;
        size += arr.length;
        return true;
    }

    @Override
    public boolean addAll(Object[] arr, int index)  throws OutOfRangeException, IndexOutOfBoundsException {
        Node start, end;
        int currentIndex = 0;
        if(index == size) {
            return addAll(arr);
        }
        if(size + arr.length > MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException(ADDING_ON_NONEXISTENT_INDEX);
        }
        if(arr.length == 0) {
            return true;
        }
        end = first;
        while(currentIndex != index) {
            end = end.getNext();
            currentIndex++;
        }
        start = end.getPrevious();
        for(int i = arr.length - 1; i >= 0; i--) {
            final Node node = new Node(arr[i]);
            end.setPrevious(node);
            node.setNext(end);
            end = end.getPrevious();
        }
        if(index !=0) {
           start.setNext(end);
           end.setPrevious(start);
        }
        size += arr.length;
        return true;
    }

    @Override
    public E remove(Object element) throws NoSuchElementException {
        Node current = first;
        while(!element.equals(current.getElement()) && current.getNext() != null) {
            current = current.getNext();
        }
        if(element.equals(current.getElement())) {
            if(current == first) {
                first = first.getNext();
                first.setPrevious(null);
            } else if(current ==  last) {
                last = last.getPrevious();
                last.setNext(null);
            } else {
                Node previous = current.getPrevious();
                Node next = current.getNext();
                previous.setNext(next);
                next.setPrevious(previous);
            }
            size--;
            return (E) current.getElement();
        }
        throw new NoSuchElementException(REMOVING_NONEXISTENT_ELEMENT);
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        Node currentNode;
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(REMOVING_ON_NONEXISTENT_INDEX);
        }
        if(index == 0) {
            currentNode = first;
            first = first.getNext();
            first.setPrevious(null);
        } else if(index == size - 1) {
            currentNode = last;
            last = last.getPrevious();
            last.setNext(null);
        } else {
            int currentIndex = 1;
            currentNode = first.getNext();
            while(currentIndex != index) {
                currentNode = currentNode.getNext();
                currentIndex++;
            }
            Node previous = currentNode.getPrevious();
            Node next = currentNode.getNext();
            previous.setNext(next);
            next.setPrevious(previous);
        }
        size--;
        return (E) currentNode.getElement();
    }

    @Override
    public boolean removeAll() {
        first = last = null;
        size = 0;
        return true;
    }

    @Override
    public boolean set(Object element, int index) throws IndexOutOfBoundsException {
        int currentIndex = 0;
        Node currentNode = first;
        E e = null;
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(ACCESSING_A_NONEXISTENT_INDEX);
        }
        try {
            e = (E) element;
        } catch (ClassCastException ex) {
            System.out.println(ex.getMessage());
        }
        while(currentIndex != index) {
            currentNode = currentNode.getNext();
            currentIndex++;
        }
        currentNode.setElement(e);
        return true;
    }

    @Override
    public boolean contains(Object element) {
        Node currentNode = first;
        if(size == 0) {
            return false;
        }
        do {
            if(element.equals(currentNode.getElement())) {
                return true;
            }
        } while (currentNode.getNext() != null);{
                currentNode = currentNode.getNext();
            }
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
