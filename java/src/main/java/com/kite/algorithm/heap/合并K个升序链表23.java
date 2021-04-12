package com.kite.algorithm.heap;

import com.alibaba.fastjson.JSONObject;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Soluttion {


    public static void main(String[] args) {
        Soluttion solute = new Soluttion();
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(4);
        listNode.next.next = new ListNode(5);


        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(3);
        listNode1.next.next = new ListNode(4);


        ListNode listNode2 = new ListNode(2);
        listNode2.next = new ListNode(6);

        ListNode[] listNodes = new ListNode[]{listNode, listNode1, listNode2};

        ListNode result = solute.mergeKLists1(listNodes);


        System.out.println(JSONObject.toJSONString(result));

    }

    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        ListNode head = new ListNode(0);
        ListNode current = head;
        while (true) {
            ListNode minNode = null;
            int minPointer = 0;
            for (int i = 0; i < k; i++) {
                minNode = lists[i];
            }
            break;
        }


        return head;
    }


    public ListNode mergeKLists1(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        if (lists.length == 1) {
            return lists[0];
        }

        Queue<ListNode> priorityQueue = new PriorityQueue<>((c1, c2) -> c1.val - c2.val);
        for (ListNode listNode : lists) {
            if (listNode != null) {
                priorityQueue.offer(listNode);
            }
        }

        ListNode headNode = new ListNode(0);
        ListNode current = headNode;

        while (!priorityQueue.isEmpty()) {
            ListNode minNode = priorityQueue.poll();
            current.next = minNode;
            current = minNode;
            if (minNode.next != null) {
                priorityQueue.add(minNode.next);
            }

        }
        return headNode.next;
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
