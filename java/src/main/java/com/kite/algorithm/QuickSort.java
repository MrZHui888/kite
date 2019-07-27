package com.kite.algorithm;

import java.util.Arrays;

/**
 * @author : Guzh
 * @since : 2018/11/22
 * 快速排序
 * O（nlog n）
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] num = {2, 123, 43, 54, 654, 76, 2, 4, 33};
        int[] num1 = {2, 123, 43, 54, 654, 76, 2, 4, 33};

        sort1(num, 0, num.length - 1);

        sort(num1, 0, num.length - 1);

        Arrays.stream(num).forEach(x -> System.out.print(x + " "));
        System.out.println( " ");
        Arrays.stream(num1).forEach(x -> System.out.print(x + " "));

    }

    private static void sort1(int[] nums, int left, int right) {
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
        sort1(nums, left, i - 1);
        sort1(nums, i + 1, right);
    }


    private static void sort(int[] numList, int left, int right) {
        if (left >= right) {
            return;
        }
        int tmp = numList[left];
        int i = left;
        int j = right;
        while (i != j) {
            while (i < j && numList[j] >= tmp) {
                j--;
            }
            if (j > i) {
                numList[i] = numList[j];//a[i]已经赋值给temp,所以直接将a[j]赋值给a[i],赋值完之后a[j],有空位
            }
            while (i < j && numList[i] <= tmp) {
                i++;
            }
            if (j > i) {
                numList[j] = numList[i];
            }
        }
        numList[i] = tmp;
        sort(numList, left, i - 1);
        sort(numList, i + 1, right);
    }

}
