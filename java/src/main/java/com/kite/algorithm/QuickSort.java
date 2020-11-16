package com.kite.algorithm;

import java.util.Arrays;

/**
 * @author : Guzh
 * @since : 2018/11/22 快速排序 O（nlog n）
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] num = {2, 123, 43, 54, 654, 76, 2, 4, 33};
        sort(num, 0, num.length - 1);
        Arrays.stream(num).forEach(x -> System.out.print(x + " "));

    }

    private static void sort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = nums[right];

        int i = left;
        int j = right;

        while (i != j) {

            while (i < j && nums[i] <= pivot) {
                i++;
            }
            if (j > i) {
                nums[j] = nums[i];
            }

            while (i < j && nums[j] >= pivot) {
                j--;
            }
            if (j > i) {
                nums[i] = nums[j];
            }
        }

        nums[i] = pivot;
        sort(nums, left, i - 1);
        sort(nums, i + 1, right);
    }

}
