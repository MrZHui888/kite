package com.kite.algorithm.linkedlist;

public class Reverse {

    public static void main(String[] args) {
        ListNode listNode = buildNode();

        ListNode reverse = reverse(listNode);
        System.out.println(listNode);
    }


    private static ListNode reverse(ListNode listNode) {
        ListNode reverseListNode = new ListNode(-1);
        ListNode p = listNode;
        while (p != null) {
            ListNode tmp = p.next;
            p.next = reverseListNode.next;
            reverseListNode.next = p;
            p = tmp;
        }
        return reverseListNode.next;
    }


    private static ListNode buildNode() {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode1.next = listNode2;
        ListNode listNode3 = new ListNode(3);
        listNode2.next = listNode3;
        ListNode listNode4 = new ListNode(4);
        listNode3.next = listNode4;
        ListNode listNode5 = new ListNode(5);
        listNode4.next = listNode5;
        ListNode listNode6 = new ListNode(6);
        listNode5.next = listNode6;
        return listNode1;
    }

    static class ListNode {
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
    }
}
