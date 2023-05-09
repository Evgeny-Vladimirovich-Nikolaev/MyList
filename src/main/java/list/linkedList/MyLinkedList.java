package main.java.list.linkedList;

import main.java.list.MyList;
import main.java.list.arrayList.MyArrayList;
import main.java.list.myListExceptions.OutOfRangeException;

import java.util.NoSuchElementException;

public class MyLinkedList<E> implements MyList {

    private int size;
    private Node first;
    private Node last;

    private Node current = first;
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
    public E first() {
        current = first;
        return (E) current.getElement();
    }

    public void setFirst(E element) {
        first.setElement(element);
    }

    @Override
    public E last() {
        current = last;
        return (E) current.getElement();
    }

    public void setLast(E element) {
        last.setElement(element);
    }

    public E current() {
        return (E) current.getElement();
    }

    public boolean hasNext() {
        return current.hasNext();
    }

    public E getNext() {
        if(current.hasNext()) {
            current = current.getNext();
            return (E) current.getElement();
        }
        return null;
    }

    public boolean hasPrevious() {
        return current.hasPrevious();
    }

    public E getPrevious() {
        if(current.hasPrevious()) {
            current = current.getPrevious();
            return (E) current.getElement();
        }
        return null;
    }

    @Override
    public boolean add(Object element) throws OutOfRangeException {
        if (size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        final Node node = new Node(element);
        if (size == 0) {
            first = last = node;
        } else {
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
            Node temp = first.getNext();
            while (currentIndex != index) {
                temp = temp.getNext();
                currentIndex++;
            }
            final Node previous = temp.getPrevious();
            previous.setNext(node);
            node.setPrevious(previous);
            node.setNext(temp);
            temp.setPrevious(node);
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
        Node temp = new Node(arr[0]);
        if (size == 0) {
            first = temp;
        } else {
            last.setNext(temp);
        }
        Node next = null;
        for (int i = 1; i < arr.length; i++) {
            next = new Node(arr[i]);
            temp.setNext(next);
            next.setPrevious(temp);
            temp = next;
        }
        last = next;
        size += arr.length;
        return true;
    }

    @Override
    public boolean addAll(Object[] arr, int index) throws OutOfRangeException, IndexOutOfBoundsException {
        Node end, temp;
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
        temp = first;
        if (index == 0) {
            for (int i = arr.length - 1; i >= 0; i--) {
                temp.setPrevious(new Node(arr[i]));
                temp.getPrevious().setNext(temp);
                temp = temp.getPrevious();
            }
            first = temp;
        } else {
            while (currentIndex < index - 1) {
                temp = temp.getNext();
                currentIndex++;
            }
            end = temp.getNext();
            for (int i = 0; i < arr.length; i++) {
                temp.setNext(new Node(arr[i]));
                temp = temp.getNext();
            }
            temp.setNext(end);
        }
        return true;
    }

    @Override
    public boolean addAll(MyList list) {
        if(list instanceof MyArrayList<?>) {
            for(int i = 0; i < list.size(); i++) {
                this.add(list.get(i));
            }
            return true;
        } else if (list instanceof MyLinkedList<?>) {
            list = (MyLinkedList<E>) list;
            Node temp = ((MyLinkedList<?>) list).first;
            while(temp != null) {
                this.add(temp.getElement());
                temp = temp.getNext();
            }
            return true;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object element) throws NoSuchElementException {
        Node temp = first;
        while (!element.equals(temp.getElement()) && temp.hasNext()) {
            temp = temp.getNext();
        }
        if (element.equals(temp.getElement())) {
            if (temp == first) {
                first.setElement(null);
                first = first.getNext();
                if (size > 1) {
                    first.setPrevious(null);
                }
            } else if (temp == last) {
                last.setElement(null);
                last = last.getPrevious();
                last.setNext(null);
            } else {
                Node previous = temp.getPrevious();
                Node next = temp.getNext();
                previous.setNext(next);
                next.setPrevious(previous);
            }
            temp.setElement(null);
            temp = null;
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
            if (size > 1) {
                first.setPrevious(null);
            }
        } else if (index == size - 1) {
            element = (E) last.getElement();
            last.setElement(null);
            last = last.getPrevious();
            last.setNext(null);
        } else {
            int currentIndex = 1;
            Node temp = first.getNext();
            while (currentIndex != index) {
                temp = temp.getNext();
                currentIndex++;
            }
            element = (E) temp.getElement();
            Node previous = temp.getPrevious();
            Node next = temp.getNext();
            temp.setElement(null);
            temp = null;
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
        Node temp = first;
        do {
            temp.setElement(null);
            temp = temp.getNext();
        } while (temp != null && temp.hasNext());
        first = last = null;
        size = 0;
        return true;
    }

    @Override
    public boolean set(Object element, int index) throws IndexOutOfBoundsException {
        int currentIndex = 0;
        Node temp = first;
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
            temp = temp.getNext();
            currentIndex++;
        }
        temp.setElement(e);
        return true;
    }

    @Override
    public boolean contains(Object element) {
        if (size == 0) {
            return false;
        }
        Node temp = first;
        E e = (E) element;
        while (temp != null) {
            if (temp.getElement().equals(e)) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        int currentIndex = 0;
        Node temp = first;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(ACCESSING_A_NONEXISTENT_INDEX);
        }
        while (currentIndex != index) {
            temp = temp.getNext();
            currentIndex++;
        }
        return (E) temp.getElement();
    }

    @Override
    public int getIndexOf(Object element) throws NoSuchElementException {
        int currentIndex = 0;
        Node temp;
        if (size == 0) {
            throw new NoSuchElementException(ELEMENT_IS_MISSING);
        }
        if (element.equals(first.getElement())) {
            return 0;
        }
        temp = first;
        while (temp.hasNext()) {
            temp = temp.getNext();
            currentIndex++;
            if (element.equals(temp.getElement())) {
                return currentIndex;
            }
        }
        throw new NoSuchElementException(ELEMENT_IS_MISSING);
    }

    @Override
    public boolean retain(Object element) {
        this.removeAll();
        this.add(element);
        return true;
    }

    @Override
    public boolean retainAll(Object[] arr) {
        this.removeAll();
        this.addAll(arr);
        return true;
    }

    private boolean equalNodes(MyLinkedList list) {
        if (this.size != list.size) {
            return false;
        }
        Node thisNode = this.first;
        Node listNode = list.first;
        do {
            if (!thisNode.equals(listNode)) {
                return false;
            }
            thisNode = thisNode.getNext();
            listNode = listNode.getNext();
        } while (thisNode != null);
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
        if (this.hashCode() != list.hashCode()) {
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
        Node temp = first;
        while (temp != null) {
            s = s + '[' + temp + ']';
            if (temp.getNext() == null) {
                break;
            }
            temp = temp.getNext();
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
        if (element != null) {
            return element.hashCode();
        }
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node node = (Node) obj;
        if (this.hashCode() != obj.hashCode()) {
            return false;
        }
        return this.element.equals(((Node<?>) obj).element);
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

