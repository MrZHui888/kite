package com.kite.algorithm;

import java.util.Arrays;

/**
 * @author : Guzh
 * @since : 2018/11/22
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] num = { 2, 123, 43, 54, 654, 76, 2, 4, 33 };

        sort(num, 0, num.length - 1);

       Arrays.stream(num).forEach(x->System.out.print(x+" "));

    }

    private static void sort(int[] numList, int left, int right) {
        if (left >= right) {
            return;
        }
        int tmp = numList[left];
        int i = left;
        int j = right;
        while (i != j) {
            while (i < j && numList[i] <= tmp) {
                i++;
                if (i < j) {
                    numList[j] = numList[i];
                }
            }

            while (i < j && numList[j] > tmp) {
                j--;
                if (j > i) {
                    numList[i] = numList[j];
                }
            }
        }
        numList[i] = tmp;
        sort(numList, left, i - 1);
        sort(numList, i+1, right);
    }

}
