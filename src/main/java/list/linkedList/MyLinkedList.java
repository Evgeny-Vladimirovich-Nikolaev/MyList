package main.java.list.linkedList;

import main.java.list.MyList;
import main.java.list.myListExceptions.OutOfRangeException;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements MyList {

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
    private static String ELEMENT_IS_MISSING = "the element is missing in the MyLinkedList";


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
        if (size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (size == 0) {
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
        if (size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(ADDING_ON_NONEXISTENT_INDEX);
        }
        final Node node = new Node(element);
        if (size == 0) {
            first = last = node;
        } else if (index == 0) {
            first.setPrevious(node);
            node.setNext(first);
            first = node;
        } else if (index == size) {
            last.setNext(node);
            node.setPrevious(last);
            last = node;
        } else {
            int currentIndex = 1;
            Node currentNode = first.getNext();
            while (currentIndex != index) {
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
        if (size + arr.length > MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (arr.length == 0) {
            return true;
        }
        if (size == 0) {
            last = first = new Node(arr[0]);
        }
        Node currentNode = last;
        Node next = null;
        for (int i = 1; i < arr.length; i++) {
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
    public boolean addAll(Object[] arr, int index) throws OutOfRangeException, IndexOutOfBoundsException {
        Node start, end;
        int currentIndex = 0;
        if (index == size) {
            return addAll(arr);
        }
        if (size + arr.length > MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(ADDING_ON_NONEXISTENT_INDEX);
        }
        if (arr.length == 0) {
            return true;
        }
        end = first;
        while (currentIndex != index) {
            end = end.getNext();
            currentIndex++;
        }
        start = end.getPrevious();
        for (int i = arr.length - 1; i >= 0; i--) {
            final Node node = new Node(arr[i]);
            end.setPrevious(node);
            node.setNext(end);
            end = end.getPrevious();
        }
        if (index != 0) {
            start.setNext(end);
            end.setPrevious(start);
        }
        size += arr.length;
        return true;
    }

    @Override
    public boolean remove(Object element) throws NoSuchElementException {
        Node currentNode = first;
        while (!element.equals(currentNode.getElement()) && currentNode.hasNext()) {
            currentNode = currentNode.getNext();
        }
        if (element.equals(currentNode.getElement())) {
            if (currentNode == first) {
                first.setElement(null);
                first = first.getNext();
                first.setPrevious(null);
            } else if (currentNode == last) {
                last.setElement(null);
                last = last.getPrevious();
                last.setNext(null);
            } else {
                Node previous = currentNode.getPrevious();
                Node next = currentNode.getNext();
                previous.setNext(next);
                next.setPrevious(previous);
            }
            currentNode.setElement(null);
            currentNode = null;
            size--;
            return true;
        }
        throw new NoSuchElementException(REMOVING_NONEXISTENT_ELEMENT);
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        E element;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(REMOVING_ON_NONEXISTENT_INDEX);
        }
        if (index == 0) {
            element = (E) first.getElement();
            first.setElement(null);
            first = first.getNext();
            first.setPrevious(null);
        } else if (index == size - 1) {
            element = (E) last.getElement();
            last.setElement(null);
            last = last.getPrevious();
            last.setNext(null);
        } else {
            int currentIndex = 1;
            Node currentNode = first.getNext();
            while (currentIndex != index) {
                currentNode = currentNode.getNext();
                currentIndex++;
            }
            element = (E) currentNode.getElement();
            Node previous = currentNode.getPrevious();
            Node next = currentNode.getNext();
            currentNode.setElement(null);
            currentNode = null;
            previous.setNext(next);
            next.setPrevious(previous);
        }
        size--;
        return element;
    }


    @Override
    public boolean removeAll() {
        if (first == null) {
            return true;
        }
        Node currentNode = first;
        do {
            currentNode.setElement(null);
            currentNode = currentNode.getNext();
        } while (currentNode != null && currentNode.hasNext());
        first = last = null;
        size = 0;
        return true;
    }

    @Override
    public boolean set(Object element, int index) throws IndexOutOfBoundsException {
        int currentIndex = 0;
        Node currentNode = first;
        E e = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(ACCESSING_A_NONEXISTENT_INDEX);
        }
        try {
            e = (E) element;
        } catch (ClassCastException ex) {
            System.out.println(ex.getMessage());
        }
        while (currentIndex != index) {
            currentNode = currentNode.getNext();
            currentIndex++;
        }
        currentNode.setElement(e);
        return true;
    }

    @Override
    public boolean contains(Object element) {
        if (size == 0) {
            return false;
        }
        Node currentNode = first;
        E e = (E) element;
        while(currentNode != null) {
            if(currentNode.getElement().equals(e)) {
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        int currentIndex = 0;
        Node currentNode = first;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(ACCESSING_A_NONEXISTENT_INDEX);
        }
        while (currentIndex != index) {
            currentNode = currentNode.getNext();
            currentIndex++;
        }
        return (E) currentNode.getElement();
    }

    @Override
    public int getIndexOf(Object element) throws NoSuchElementException {
        int currentIndex = 0;
        Node currentNode;
        if (size == 0) {
            throw new NoSuchElementException(ELEMENT_IS_MISSING);
        }
        if (element.equals(first.getElement())) {
            return 0;
        }
        currentNode = first;
        while (currentNode.hasNext()) {
            currentNode = currentNode.getNext();
            currentIndex++;
            if (element.equals(currentNode.getElement())) {
                return currentIndex;
            }
        }
        throw new NoSuchElementException(ELEMENT_IS_MISSING);
    }

    @Override
    public boolean retain(Object element) {
        Node currentNode;
        if (size == 0) {
            return false;
        }
        if (element.equals(first.getElement())) {
            first.setNext(null);
            last = first;
            return true;
        }
        currentNode = first;
        while (currentNode.hasNext()) {
            currentNode = currentNode.getNext();
            if (element.equals(currentNode.getElement())) {
                currentNode.setPrevious(null);
                currentNode.setNext(null);
                first = last = currentNode;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean retainAll(Object[] arr) {
        List list;
        Node currentNode;
        if (size == 0) {
            return false;
        }
        list = List.of(arr);
        currentNode = first;
        for (Object ob : list) {
            if (ob.equals(first.getElement())) {
                list.remove(ob);
                continue;
            }
        }
        while (currentNode.hasNext()) {
            boolean isMissing = true;
            currentNode = currentNode.getNext();
            for (Object ob : list) {
                if (ob.equals(currentNode.getElement())) {
                    list.remove(ob);
                    isMissing = false;
                    break;
                }
            }
            if (isMissing) {
                Node previous = currentNode.getPrevious();
                Node next = currentNode.getNext();
                previous.setNext(next);
                previous.setPrevious(previous);
            }
        }
        return list.isEmpty();
    }

    private boolean equalNodes(MyLinkedList list) {
        if(this.size != list.size) {
            return false;
        }
        Node thisNode = this.first;
        Node listNode = list.first;
        do {
            if(!thisNode.equals(listNode)) {
                return false;
            }
            thisNode = thisNode.getNext();
            listNode = listNode.getNext();
        } while(thisNode != null);
        return true;
    }

    @Override
    public int hashCode() {
        return size * 31 + first.hashCode() + last.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MyLinkedList)) {
            return false;
        }
        MyLinkedList list = (MyLinkedList) obj;
        if(this.hashCode() != list.hashCode()) {
            return false;
        }
        if (!first.equals(list.first) || !last.equals(list.last)) {
            return false;
        }
        return equalNodes(list);
    }

    @Override
    public String toString() {
        String s = "[";
        Node currentNode = first;
        while(currentNode != null) {
            s = s + '[' + currentNode + ']';
            if(currentNode.getNext() == null) {
                break;
            }
            currentNode = currentNode.getNext();
            s = s + ", ";
        }
        return s + ']';
    }
}

class Node<E> {

    private E element;
    private Node previous;
    private Node next;

    Node(E element) {
        this.element = element;
    }

    E getElement() {
        return element;
    }

    void setElement(E element) {
        this.element = element;
    }

    Node getPrevious() {
        return previous;
    }

    void setPrevious(Node previous) {
        this.previous = previous;
    }

    Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }

    boolean hasPrevious() {
        return previous != null;
    }

    boolean hasNext() {
        return next != null;
    }

    @Override
    public int hashCode() {
        if(element != null) {
            return element.hashCode();
        }
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Node)) {
            return false;
        }
        Node node = (Node) obj;
        if(this.hashCode() != obj.hashCode()) {
            return false;
        }
        return this.element.equals(((Node<?>) obj).element);
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
