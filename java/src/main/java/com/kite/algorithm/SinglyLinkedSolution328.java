package com.kite.algorithm;


public class SinglyLinkedSolution328 {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(7, null);
        ListNode listNode1 = new ListNode(4, listNode);
        ListNode listNode2 = new ListNode(6, listNode1);
        ListNode listNode3 = new ListNode(5, listNode2);
        ListNode listNode4 = new ListNode(3, listNode3);
        ListNode listNode5 = new ListNode(1, listNode4);
        ListNode listNode6 = new ListNode(2, listNode5);

        System.out.println("");

    }


    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }


        // 偶数
        ListNode eventHead = head.next;

        // 奇数链表
        ListNode oddNode = head;

        // 偶数链表
        ListNode evenNode = eventHead;
        // 闪电五连鞭
        while (evenNode != null && evenNode.next != null) {
            // 偶数节点->奇数链表的下个节点
            oddNode.next = evenNode.next;
            oddNode = oddNode.next;

            // 奇数节点-> 奇数链表下个节点是偶数
            evenNode.next = oddNode.next;
            evenNode = evenNode.next;
        }
        oddNode.next = eventHead;
        return head;

    }


}

class ListNode {

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


