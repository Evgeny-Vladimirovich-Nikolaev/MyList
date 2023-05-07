package main.java.list;

import main.java.list.myListExceptions.OutOfRangeException;

import java.util.NoSuchElementException;

public interface MyList <E> {

    int size();
    boolean isEmpty();
    boolean add(E element) throws OutOfRangeException;
    boolean add(E element, int index)throws OutOfRangeException, IndexOutOfBoundsException;
    boolean addAll(E[] arr)throws OutOfRangeException;
    boolean addAll(E[] arr, int index)throws OutOfRangeException, IndexOutOfBoundsException;
    boolean remove(E element) throws NoSuchElementException;
    E remove(int index)throws IndexOutOfBoundsException;
    boolean removeAll();
    boolean set(E element, int index)throws IndexOutOfBoundsException;
    boolean contains(E element);
    E get(int index) throws IndexOutOfBoundsException;
    int getIndexOf(E element) throws NoSuchElementException;
    boolean retain(E element);
    boolean retainAll(E[] arr);

}
