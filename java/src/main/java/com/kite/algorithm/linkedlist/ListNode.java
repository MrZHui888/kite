package com.kite.algorithm.linkedlist;

public class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void printLinkedList(ListNode head) {
        if (head == null) {
            return;
        }
        printLinkedList(head.next);
        System.out.print(head.val + " ");
    }
}
