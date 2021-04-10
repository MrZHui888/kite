package com.kite.algorithm.linkedlist;

/**
 * 链表反转
 */
public class LinkedListReverse {

    public static void main(String[] args) {

        Node node = new Node(1, null);
        Node node1 = new Node(2, node);
        Node node2 = new Node(3, node1);
        Node node3 = new Node(4, node2);

        printLinkedList(node3);

        Node res = new Node(-1);
        reverse1(node3, null);
        System.out.println("------");

        printLinkedList(node);

        System.out.println("");

    }


    /**
     * 链表的反转
     *
     * @return Node
     */
    public static Node reverse(Node node) {
        if (node == null) {
            return node;
        }

        Node result = new Node(-1);
        result.next = node;

        Node prev = result.next;
        Node pCur = prev.next;

        while (pCur != null) {
            prev.next = pCur.next;
            pCur.next = result.next;
            result.next = pCur;
            pCur = prev.next;
        }
        return result.next;
    }

    public static void reverse1(Node node, Node result) {
        if (node == null) {
            return;
        }
        Node tmpNode = node.next;
        node.next = result;
        reverse1(tmpNode, node);
    }

    /**
     * 链表的打印ååååååååååå
     *
     * @param head
     */
    public static void printLinkedList(Node head) {
        if (head == null) {
            return;
        }
        printLinkedList(head.next);
        System.out.print(head.value + " ");
    }


    static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
