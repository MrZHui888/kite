package com.kite.algorithm;

import java.util.Arrays;

/**
 * 移动零 操作
 *
 * 给定一个数组  将0 移动到最后去， 同时保持非0的相对性
 * 多练一下
 *
 */
public class MoveZeros {

    public static void main(String[] args) {

        int[] num = new int[]{1, 0, 3, 5, 0, 12, 2, 0, 0, 2};


        moveZeros(num);

        Arrays.asList(num)
                .stream()
                .flatMap(x -> Arrays.stream(x).boxed())
                .forEach(x -> System.out.print(x + ","));

    }

    public static void moveZeros(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                if (i != index) {
                    nums[i] = 0;
                }
                index++;
            }
        }

    }
}
