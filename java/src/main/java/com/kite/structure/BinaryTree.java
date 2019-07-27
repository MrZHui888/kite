package com.kite.structure;

/**
 * @author gzh
 */
public class BinaryTree implements Tree {

    private TreeNode root;

    @Override
    public boolean insert(int date) {
        // 1.构建当前节点
        TreeNode node = new TreeNode(date);
        // 2. 根节点为空
        if (root == null) {
            root = node;
            return true;
        } else {
            // 3. 根节点不为空
            TreeNode current = root;
            TreeNode parent = null;

            while (current != null) {
                parent = current;
                // 比当前节点的数据小 结果放在左子树
                if (current.getCurrentDate() > date) {
                    current = current.getLeft();
                    if (current == null) {
                        parent.setLeft(node);
                        return true;
                    }
                } else {
                    current = current.getRight();
                    if (current == null) {
                        parent.setRight(node);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TreeNode find(int key) {
        TreeNode current = root;
        while (current != null) {
            if (current.getCurrentDate() > key) {
                current = current.getLeft();
            } else if (current.getCurrentDate() < key) {
                current = current.getRight();
            } else {
                return current;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int date) {

        return false;
    }
}
