package com.kite.collection;


import java.util.LinkedList;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList linkedList =new LinkedList();

        linkedList.add(21);
        linkedList.add(22);
        linkedList.add(1);
        linkedList.add(12);


        linkedList.stream().forEach(x->System.out.println(x));

        System.out.println("华丽的分割线");
        linkedList.remove();

        linkedList.stream().forEach(x->System.out.println(x));

    }
}
