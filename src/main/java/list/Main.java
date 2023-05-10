package main.java.list;

import main.java.list.arrayList.MyArrayList;
import main.java.list.linkedList.MyLinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        MyArrayList list1 = new MyArrayList(new Object[]{1, 2, 3, 4, 5, 5, 5, 5, 6, 6, 7, 8, 9});
        System.out.println(list1);
        System.out.println(list1.retainAll(new Object[]{4, 5, 6, "seven"}));
        System.out.println(list1);
    }

}