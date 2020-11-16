package com.kite.algorithm;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 选择排序
 * 从头开始遍历  找剩下最小的
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {20, 20, 9, 3, 7, 5, 1, 0, 20, 3, 6};

        System.out.println("before:");
        print(arr);

        sort(arr);

        System.out.println("after:");
        print(arr);


    }

    private static void sort(int[] arr) {
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            maxIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[maxIndex] <= arr[j]) {
                    maxIndex = j;
                }
            }

            //交换
            if (maxIndex != i) {
                int tmp = arr[maxIndex];
                arr[maxIndex] = arr[i];
                arr[i] = tmp;
            }
        }
    }


    private static void print(int[] arr) {

        List list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());

        String lineLog = Joiner.on(",").join(list);
        System.out.println(lineLog);
    }
}
