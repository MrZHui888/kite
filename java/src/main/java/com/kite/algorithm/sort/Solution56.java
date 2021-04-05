package com.kite.algorithm.sort;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合并区间
 */
public class Solution56 {


    public static void main(String[] args) {
//        int[][] intervals = new int[][]{{1, 3}, {2, 6}, {15, 18}, {8, 10}};
//        int[][] intervals = new int[][]{{1, 4}, {2, 3}};
        int[][] intervals = new int[][]{{1, 4}, {4, 5}};

        int[][] ints = merge(intervals);
        System.out.println(JSONObject.toJSONString(ints));
        System.out.println(JSONObject.toJSONString(intervals));
    }

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[][]{};
        }

        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        int size = intervals.length;
        List<int[]> result = new ArrayList<>(intervals.length);
        for (int i = 0; i < size; i++) {
            int[] tmp = intervals[i];
            int l = tmp[0];
            int r = tmp[1];

            int resultLastIndex = result.size();
            if (resultLastIndex == 0 || (l > result.get(resultLastIndex - 1)[1])) {
                int[] area = new int[2];
                area[0] = l;
                area[1] = r;
                result.add(area);
                continue;
            }

            int[] resultLast = result.get(resultLastIndex - 1);
            if (resultLast[1] > r) {
                continue;
            }

            if (resultLast[1] >= l) {
                resultLast[1] = r;
                result.remove(resultLastIndex - 1);
                result.add(resultLast);
            }
        }

        return result.toArray(new int[][]{});
    }
}
