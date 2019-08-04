package com.kite.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class CombinerLogic {

    public static void main(String[] args) {
        List list = Lists.newArrayList("A", "B", "C", "D");

        List list1 = getAllCombinerDun(list);

        System.out.println(list1);
    }

    public static List<List<String>> getAllCombinerDun(List<String> data) {
        List<List<String>> combinerResults = new ArrayList<>();

        combinerSelect(combinerResults, data, new ArrayList<String>(), data.size(), 2);

        // logger.info("组合结果：{}", results.toString());
        return combinerResults;
    }

    /***
     * C(n,k) 从n个中找出k个组合
     * 
     * @param data
     * @param workSpace
     * @param n
     * @param k
     * @return
     */
    public static List<List<String>> combinerSelect(List<List<String>> combinerResults, List<String> data,
        List<String> workSpace, int n, int k) {
        List<String> copyData;
        List<String> copyWorkSpace;

        if (workSpace.size() == k) {
            List<String> dunTiles = new ArrayList<>();
            for (String c : workSpace) {
                dunTiles.add(c);
            }

            combinerResults.add(dunTiles);
        }

        for (int i = 0; i < data.size(); i++) {
            copyData = new ArrayList(data);
            copyWorkSpace = new ArrayList(workSpace);

            copyWorkSpace.add(copyData.get(i));
            for (int j = i; j >= 0; j--)
                copyData.remove(j);
            combinerSelect(combinerResults, copyData, copyWorkSpace, n, k);
        }

        return combinerResults;
    }

}
