package main.java.list.arrayList;

import main.java.list.MyList;
import main.java.list.linkedList.MyLinkedList;
import main.java.list.myListExceptions.OutOfRangeException;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements MyList {

    private int size = 0;
    //private int capacity;
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
        size = array.length;
    }

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
        return this.get(0);
    }

    @Override
    public E last() {
        return this.get(size - 1);
    }


    public int capacity() {
        return array.length;
    }

    @Override
    public boolean add(Object element) throws OutOfRangeException {
        if (size == MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if (size == array.length) {
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
        if (size >= array.length) {
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
        if(array.length + arr.length > MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if(size == 0) {
            this.array = (E[]) arr;
        } else {
            if(array.length < size + arr.length) {
                array = Arrays.copyOf(array, array.length + arr.length);
            }
            for(int i = 0; i < arr.length; i++) {
                array[size + i] = (E) arr[i];
            }
        }
        size += arr.length;
        return true;
    }

    @Override
    public boolean addAll(Object[] arr, int index) throws OutOfRangeException, IndexOutOfBoundsException {
        if(index == size) {
            return addAll(arr);
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(ADDING_ON_NONEXISTENT_INDEX);
        }
        if (size + arr.length >= MAX) {
            throw new OutOfRangeException(EXCEEDING_MAXIMUM_NUMBER);
        }
        if(size == 0) {
            this.array = (E[]) arr;
        } else {
            if(array.length < size + arr.length) {
                array = Arrays.copyOf(array, array.length + arr.length);
            }
            for(int i = size - 1; i >= index; i--) {
                array[i + arr.length] = array[i];
            }
            for(int i = 0; i < arr.length; i++) {
                array[index + i] = (E) arr[i];
            }
        }
        size += arr.length;
        return true;
    }

    @Override
    public boolean addAll(MyList list) {
        if(list instanceof MyArrayList<?>) {
            for(int i = 0; i < list.size(); i++) {
                this.add(list.get(i));
            }
            return true;
        }
        if(list instanceof MyLinkedList) {
            if(((MyLinkedList) list).first() != null) {
                this.add(((MyLinkedList) list).current());
            }
            while(((MyLinkedList<?>) list).hasNext()) {
                this.add(((MyLinkedList<?>) list).getNext());
            }
            return true;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object element) throws NoSuchElementException {
        int index = 0;
        while(index < size) {
            if(element.equals(array[index])) {
                for(int i = index; i < size - 1; i++) {
                    array[i] = array[++i];
                }
                array[--size] = null;
                return true;
            }
            index++;
        }
        throw new NoSuchElementException(REMOVING_NONEXISTENT_ELEMENT);
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
        if(size == 0){
            return false;
        }
        for(int i = size -1; i >= 0; i--) {
            if(element.equals(array[i])) {
                continue;
            }
            this.remove(i);
        }
        return true;
    }

    @Override
    public boolean retainAll(Object[] arr) {
        if(size == 0) {
            return false;
        }
        for(int i = size - 1; i >= 0; i--) {
            for(int j = 0; j < arr.length; j++) {
                if(arr[j].equals(this.get(i))) {
                    System.out.println("!!!!!!! " + this.get(i) + " !!!!!!!");
                    break;
                }
                System.out.println("before " + i);
                this.remove(i--);
                System.out.println("after " + i);
                System.out.println("-----------------------------------");
            }
        }
        return true;
    }

    private void increaseArray() {
        int length = array.length;
        if (length < 1431655765) {
            if (length < 3) {
                length++;
            } else {
                length = length / 2 * 3 + 1;
            }
        } else {
            length = MAX;
        }
        array = Arrays.copyOf(array, length);
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
