package com.kite.algorithm.sort;

//给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
//
// 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
//
//
//
// 示例 1：
//
//
//输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
//输出：[[1,5],[6,9]]
//
//
// 示例 2：
//
//
//输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
//输出：[[1,2],[3,10],[12,16]]
//解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
//
// 示例 3：
//
//
//输入：intervals = [], newInterval = [5,7]
//输出：[[5,7]]
//
//
// 示例 4：
//
//
//输入：intervals = [[1,5]], newInterval = [2,3]
//输出：[[1,5]]
//
//
// 示例 5：
//
//
//输入：intervals = [[1,5]], newInterval = [2,7]
//输出：[[1,7]]
//
//
//
//
// 提示：
//
//
// 0 <= intervals.length <= 104
// intervals[i].length == 2
// 0 <= intervals[i][0] <= intervals[i][1] <= 105
// intervals 根据 intervals[i][0] 按 升序 排列
// newInterval.length == 2
// 0 <= newInterval[0] <= newInterval[1] <= 105
//
// Related Topics 排序 数组
// 👍 393 👎 0


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution57 {

    public static void main(String[] args) {
//        int[][] intervals = new int[][]{{1, 3}, {2, 6}, {15, 18}, {8, 10}};
//        int[][] intervals = new int[][]{{1, 4}, {2, 3}};
//        int[][] intervals = new int[][]{{1, 4}, {4, 5}};
        int[][] intervals = new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval = new int[]{4, 8};


        int[][] ints = insert(intervals, newInterval);
        System.out.println(JSONObject.toJSONString(ints));
        System.out.println(JSONObject.toJSONString(intervals));
    }

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals == null || intervals.length == 0) {
            if (newInterval == null || newInterval.length == 0) {
                return new int[][]{};
            }
            return new int[][]{newInterval};
        }
        intervals = Arrays.copyOf(intervals, intervals.length + 1);
        int size = intervals.length;
        if (newInterval != null && newInterval.length > 0) {
            intervals[size - 1] = newInterval;
        }

        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
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
