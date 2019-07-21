package com.kite.algorithm;

/**
 * 简单版本的topN
 */
public class SampleTopN {
    public static void main(String[] args) {
        int[] nums = new int[]{3213, 8909, 1, 2, 5, 78, 13, 678, 2, 22, 544, 121};


        print(findTopN(nums, 3));
    }

    private static int[] findTopN(int[] nums, int n) {
        if (nums == null || nums.length < n) {
            throw new IllegalStateException(" ");
        }
        int[] result = new int[n];

        for (int num : nums) {
            for (int j = 0; j < n; j++) {
                // 为空的话 赋值
                if (result[j] == 0) {
                    result[j] = num;
                    break;
                } else if (result[j] <= num) {// 结果小于 后来的值
                    // 再遍历 result 中的数据做排序
                    for (int i = n - 1; j < i; i--) {
                        result[i] = result[i - 1];
                    }
                    result[j] = num;
                    break;

                }
            }
        }


        return result;
    }


    private static void print(int[] nums) {
        for (int num : nums) {
            System.out.print(num + ",");
        }
        System.out.println();
    }
}
