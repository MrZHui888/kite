package com.kite.algorithm;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {9, 3, 7, 5, 1, 0, 20, 3, 6};

        System.out.println("before:");
        print(arr);

        sort(arr);

        System.out.println("after:");
        print(arr);

    }


    private static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int tmp = arr[j];
                if (arr[i] > tmp) {
                    arr[j] = arr[i];
                    arr[i] = tmp;
                }
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
