package com.kite.algorithm;


/**
 * @author gzh
 * <p>
 * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 摆动序列 。第一个差（如果存在的话）可能是正数或负数。
 * 仅有一个元素或者含两个不等元素的序列也视作摆动序列。
 * <p>
 * 例如， [1, 7, 4, 9, 2, 5] 是一个 摆动序列 ，因为差值 (6, -3, 5, -7, 3) 是正负交替出现的。
 * <p>
 * 相反，[1, 4, 7, 2, 5] 和 [1, 7, 4, 5, 5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，
 * 第二个序列是因为它的最后一个差值为零。
 * 子序列 可以通过从原始序列中删除一些（也可以不删除）元素来获得，剩下的元素保持其原始顺序。
 * <p>
 * 给你一个整数数组 nums ，返回 nums 中作为 摆动序列 的 最长子序列的长度 。
 * <p>
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wiggle-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution376 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 7, 4, 9, 2, 5};
//        nums = new int[]{1, 17, 5, 10, 13, 15, 10, 5, 16, 8};
//        nums = new int[]{0,0,0,0};
        Solution376 solution376 = new Solution376();
        int i = solution376.wiggleMaxLength(nums);
        System.out.println(i);
    }


    public int wiggleMaxLength(int[] nums) {
        int length = nums.length;
        int[] downs = new int[length];
        downs[0] = 1;
        int[] ups = new int[length];
        ups[0] = 1;
        int max = 1;
        for (int i = 1; i < length; i++) {
            int start = nums[i - 1];
            int end = nums[i];
            // i 小于 i+1 升
            if (start < end) {
                ups[i] = downs[i - 1] + 1;
                downs[i] = downs[i - 1];
            }
            // 降
            if (start > end) {
                downs[i] = ups[i - 1] + 1;
                ups[i] = ups[i - 1];
            }
            if (start == end) {
                downs[i] = downs[i - 1];
                ups[i] = ups[i - 1];
            }
            max = Math.max(max, Math.max(ups[i], downs[i]));
        }
        return max;
    }
}
