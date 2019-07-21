package com.kite.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 冒泡算法
 * 时间复杂度
 * 最好是 O(n)
 * 最差O(n 的2次方)
 */
public class MaoPao {
    public static void main(String[] args) {
        int[] lines = new int[]{232, 88, 2, 43, 1, 98};
        int[] lines1 = new int[]{232, 88, 2, 43, 1, 98};

        sort(lines);
        print(lines);

        sort1(lines1);
        print(lines1);




    }

    private static void sort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (nums[j + 1] < nums[j]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                }
            }
        }
    }

    private static void sort1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (nums[j] < nums[j + 1]) {
                    int tmp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = tmp;

                }
            }
        }
    }

    private static void print(int[] nums) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int num : nums) {
            stringBuffer.append(num);
            stringBuffer.append(",");
        }
        System.out.println(stringBuffer);
    }


    private static void sortPrint(int[] nums) {


    }
}
