package main.java.list;

import main.java.list.linkedList.MyLinkedList;

public class Main {

    public static void main(String[] args) {
        MyList<Integer> list = new MyLinkedList<>();
        System.out.println(list.addAll(new Integer[] {3, 4, 5, 6, 7, 8, 9}));
        System.out.println(list.addAll(new Integer[] {10, 11, 12, 13, 14 }, 7));
        System.out.println(list);
        //System.out.println(list.retainAll(new Integer[] {11, 12, 13, 14}));
        System.out.println(list.retain(14));
        System.out.println(list);
        System.out.println(list.contains(34));
    }
}
