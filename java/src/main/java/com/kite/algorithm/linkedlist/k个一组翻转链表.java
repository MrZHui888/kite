package com.kite.algorithm.linkedlist;

public class k个一组翻转链表 {


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

        ListNode.printLinkedList(node9);

        ListNode result = reverseKGroup(node9, 3);

        System.out.println();
        ListNode.printLinkedList(result);

        ListNode successor = null; // 后驱节点

        ListNode result1 = reverseKGroup1(successor, node9, 3);

        System.out.println();
        ListNode.printLinkedList(result1);


    }

    public static ListNode reverseKGroup1(ListNode successor, ListNode head, int k) {
        if (k == 1) {
            successor = head.next;
            return head;
        }
        ListNode node = reverseKGroup1(successor, head.next, k - 1);
        head.next.next = head;
        head.next = successor;
        return node;

    }


    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode result = new ListNode(-1);
        result.next = head;
        ListNode pre = result;
        ListNode end = result;

        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }


            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;
            pre = start;
            end = pre;
        }


        return result.next;

    }


    private static ListNode reverse(ListNode headNode) {
        if (headNode.next == null) {
            return headNode;
        }
        ListNode lastNode = reverse(headNode.next);
        headNode.next.next = headNode;
        headNode.next = null;
        return lastNode;
    }
}
