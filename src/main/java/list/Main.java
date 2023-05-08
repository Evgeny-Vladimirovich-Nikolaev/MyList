package main.java.list;

import main.java.list.arrayList.MyArrayList;
import main.java.list.linkedList.MyLinkedList;

public class Main {

    public static void main(String[] args) {

        MyList<Integer> list = new MyArrayList<>();

        System.out.println(list);

        list.add(1);

        System.out.println(list);

        list.add(0, 0);

        System.out.println(list);

        list.addAll(new Integer[] {2, 3, 4, 5});

        System.out.println(list);

        list.remove(3);
        System.out.println(list);

        list.retain(2);
        System.out.println(list);

        list.remove((Integer)3);
        System.out.println(list);
    }
}
