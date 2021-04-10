package com.kite.algorithm.linkedlist;

/**
 * 判断链表是否有环
 */
public class CLinkedList {
    public static void main(String[] args) {
        Node node = new Node(1, null);
        Node node1 = new Node(2, node);
        Node node2 = new Node(3, node1);
        Node node3 = new Node(4, node2);
        node.setNext(node3);

        System.out.println(isCyc(node3));
    }


    private static boolean isCyc(Node node) {
        if (node == null || node.next == null) {
            return false;
        }

        Node slowPoint = node;
        Node fastPoint = node;
        while (fastPoint != null && fastPoint.next != null) {
            slowPoint = slowPoint.next;
            fastPoint = fastPoint.next.next;
            if (slowPoint == fastPoint) {
                return true;
            }
        }
        return false;
    }

    static class Node {
        private int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
