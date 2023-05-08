package main.java.list.arrayList;

import main.java.list.MyList;
import main.java.list.myListExceptions.OutOfRangeException;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements MyList {

    private int size = 0;
    private int capacity;
    private E[] array;

    private final static int MAX = 2147483647;
    private static final String EXCEEDING_MAXIMUM_NUMBER
            = "Exceeding the maximum possible number of items in the MyArrayList";
    private static final String ADDING_ON_NONEXISTENT_INDEX
            = "attempt to add an element with a nonexistent index or with an index larger than the size of the MyArrayList";
    private static final String REMOVING_NONEXISTENT_ELEMENT = "attempt to delete a non-existent element";
    private static final String REMOVING_ON_NONEXISTENT_INDEX
            = "attempt to remove an element with a nonexistent index";
    private static final String ACCESSING_A_NONEXISTENT_INDEX = "accessing a non-existent index";
    private static String ELEMENT_IS_MISSING = "the element is missing in the MyArrayList";

    public MyArrayList() {
        this.array = (E[]) new Object[10];
    }

    public MyArrayList(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    public MyArrayList(E[] array) {
        this.array = array;
        size = capacity = array.length;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public boolean add(Object element) throws OutOfRangeException {
        if (size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (size >= capacity) {
            increaseArray();
        }
        array[size++] = (E) element;
        return true;
    }

    @Override
    public boolean add(Object element, int index) throws OutOfRangeException, IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(ADDING_ON_NONEXISTENT_INDEX);
        }
        if (size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (size >= capacity) {
            increaseArray();
        }
        int currentIndex = size;
        while (currentIndex != index) {
            array[currentIndex] = array[--currentIndex];
        }
        array[index] = (E) element;
        size++;
        return true;
    }

    @Override
    public boolean addAll(Object[] arr) throws OutOfRangeException {
        return false;
    }

    @Override
    public boolean addAll(Object[] arr, int index) throws OutOfRangeException, IndexOutOfBoundsException {
        return false;
    }

    @Override
    public boolean remove(Object element) throws NoSuchElementException {
        return false;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(REMOVING_ON_NONEXISTENT_INDEX);
        }
        while(index < size - 1) {
            array[index++] = array[index];
        }
        E element = array[index];
        array[index] = null;
        size--;
        return element;
    }

    @Override
    public boolean removeAll() {
        for(int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        return true;
    }

    private void clear() {
        for(int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        capacity = 10;
        array = (E[]) new Object[0];
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
    public E get(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(ACCESSING_A_NONEXISTENT_INDEX);
        }
        return array[index];
    }

    @Override
    public int getIndexOf(Object element) throws NoSuchElementException {
        return 0;
    }

    @Override
    public boolean retain(Object element) {
        return false;
    }

    @Override
    public boolean retainAll(Object[] arr) {
        return false;
    }

    private void increaseArray() {
        if (capacity < 1431655765) {
            if (capacity < 3) {
                capacity++;
            } else {
                capacity = capacity / 2 * 3 + 1;
            }
        } else {
            capacity = MAX;
        }
        array = Arrays.copyOf(array, capacity);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if(size == 0) {
            return "[]";
        }
        String s = "[";
        for (int i = 0; i < size - 1; i++) {
            s = s + "[" + array[i] + "], ";
        }
        return s + "[" + array[size - 1] + "]]";
    }
}
