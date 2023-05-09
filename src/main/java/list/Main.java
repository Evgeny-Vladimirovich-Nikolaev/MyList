package main.java.list;

import main.java.list.arrayList.MyArrayList;
import main.java.list.linkedList.MyLinkedList;

public class Main {

    public static void main(String[] args) {

        MyList<Integer> list = new MyLinkedList();
        MyList<Integer> extra = new MyArrayList();
        list.addAll(new Integer[]{0, 1, 2, 3, 4});
        System.out.println(list);
        extra.addAll(new Integer[]{5, 6, 7, 8, 9});
        System.out.println(extra);
        list.addAll(extra);
        System.out.println(list);

    }
}
