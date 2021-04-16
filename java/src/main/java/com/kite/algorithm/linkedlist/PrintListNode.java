package com.kite.algorithm.linkedlist;

public class PrintListNode {
    public static void main(String[] args) {

        ListNode node01 = new ListNode(10);

        ListNode node0 = new ListNode(101, node01);

        ListNode node1 = new ListNode(1, node0);
        ListNode node2 = new ListNode(2, node1);
        ListNode node3 = new ListNode(3, node2);
        ListNode node4 = new ListNode(4, node3);
        ListNode node5 = new ListNode(5, node4);
        ListNode node6 = new ListNode(6, node5);
        ListNode node7 = new ListNode(7, node6);
        ListNode node8 = new ListNode(8, node7);
        ListNode node9 = new ListNode(9, node8);

        PrintListNode printListNode = new PrintListNode();

        int[] ints = printListNode.reversePrint(node9);

        System.out.println(ints);

    }

    public int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[3];
        }

        int size = 0;
        ListNode current = head;
        while (current != null) {
            current = current.next;
            size++;
        }

        int[] printArr = new int[size];
        for (int i = 0; i < size; i++) {
            printArr[size - i - 1] = head.val;
            head = head.next;
        }
        return printArr;
    }
}
