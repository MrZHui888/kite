package com.kite.structure;

/**
 * 定义二叉树的基本方法
 * 
 * @author gzh
 */
public interface Tree {

    /**
     * 插入数据
     *
     * @param date
     *            待插入的数据
     * @return
     */
    boolean insert(int date);

    /**
     * 根据 数据查找节点
     * 
     * @param key
     *            查询的数据
     * @return 对应的节点数据
     */
    TreeNode find(int key);

    /**
     * 删除当前节点
     *
     * @param date
     *            节点对应的数据
     * @return
     */
    boolean delete(int date);
}
