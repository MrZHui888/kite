package com.kite.structure;

import lombok.Data;

@Data
public class TreeNode {
    /**
     * 当前数
     */
    private int currentDate;
    /**
     * 左子树
     */
    private TreeNode left;
    /**
     * 右子数
     */
    private TreeNode right;

    /**
     * 打印当前节点数据
     */
    public void print() {
        System.out.println(this.currentDate);
    }

    public TreeNode() {

    }

    public TreeNode(int currentDate) {

        this.currentDate = currentDate;
    }

    public TreeNode(int currentDate, TreeNode left, TreeNode right) {
        this.currentDate = currentDate;
        this.left = left;
        this.right = right;
    }
}
