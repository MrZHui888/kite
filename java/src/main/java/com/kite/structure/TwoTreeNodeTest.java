package com.kite.structure;

public class TwoTreeNodeTest {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);

        System.out.println(true);

        TreeNode treeNode = tree.find(0);
        treeNode.print();
    }
}
