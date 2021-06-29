package com.kite.algorithm;

/**
 * 跳跃游戏
 */
public class Solution45 {
    public static void main(String[] args) {
        int[] height = new int[]{2, 3, 1, 1, 4};
        System.out.println(jump(height));

        height = new int[]{2, 3, 0, 1, 4};
        System.out.println(jump(height));

    }

    public static int jump(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 最远的距离
        int maxPos = 0;
        // 次数
        int count = 0;
        int currentPos = 0;

        int i = 0;
        for (; i < nums.length - 1; i++) {

            currentPos = Math.max(currentPos, nums[i] + i);
            if (currentPos >= nums.length - 1) {
                return count + 1;
            }
            if (maxPos == i) {
                count = count + 1;
                maxPos = currentPos;
            }

        }
        return count;
    }
}
