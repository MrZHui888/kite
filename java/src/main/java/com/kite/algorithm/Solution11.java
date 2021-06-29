package com.kite.algorithm;

/**
 * 盛最多水的容器
 */
public class Solution11 {
    public static void main(String[] args) {

        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(height));
//
//        height = new int[]{1, 1};
//        System.out.println(maxArea(height));
//
//
//        height = new int[]{4, 3, 2, 1, 4};
//        System.out.println(maxArea(height));
    }

    public static int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int max = 0;
        for (int i = 0, j = height.length-1; i < j; ) {
            int minHeight = height[i] < height[j] ? height[i++] : height[j--];
            max = Math.max(max, (j - i + 1) * minHeight);
        }
        return max;


//        int[] tmp = new int[len];
//        int maxArea = 0;
//        for (int i = 0; i < len; i++) {
//            for (int j = i + 1; j < len; j++) {
//                int step = j - i;
//
//                if (height[i] < height[j]) {
//
//                }
//
//                // 步距*
//                int area = step * Math.min(height[i], height[j]);
//                if (tmp[i] < area) {
//                    tmp[i] = area;
//                }
//            }
//            if (tmp[i] > maxArea) {
//                maxArea = tmp[i];
//            }
//        }
//
//        return maxArea;
    }
}
