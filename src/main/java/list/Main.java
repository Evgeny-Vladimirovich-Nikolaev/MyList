package main.java.list;

import main.java.list.linkedList.MyLinkedList;

public class Main {

    public static void main(String[] args) {
        MyList<Integer> list = new MyLinkedList<>();
        list.addAll(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.out.println(list);
        for(int i = 9; i >= 0; i--){
            list.remove(i);
            System.out.println(list);
        }
    }
}
