package com.kite.algorithm;

import java.util.Arrays;

/**
 * 动态规划  解决斐波拉切 数列
 * 采用记录法 减少递归
 */
public class Fibonacci {

    public static void main(String[] args) {

        int num = 100;

        System.out.println(fib3(10));

//        long[] nums = new long[num + 1];
//        nums[0] = 0;
//        nums[1] = 1;
//        long total = 0;
//
//        for (int n = 2; n <= num; n++) {
//            nums[n] = nums[n - 1] + nums[n - 2];
//            total = total + nums[n];
//        }
//        System.out.println(Arrays.toString(nums));
//        System.out.println(total);

    }

    /**
     * 自订先下的备忘录法
     *
     * @param n
     * @return
     */
    public static int fibonacci1(int n) {
        if (n <= 0) {
            return n;
        }
        int[] memo = new int[n + 1];

        for (int i = 0; i < n; i++) {
            int a = fib(n, memo);
        }
        return fib(n, memo);

    }

    private static int fib3(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int pre = 2;//记录上一个
        int pre1 = 1;//记录上上一个
        int tmp = 0;//记录本次
        for (int i = 3; i < n + 1; i++) {
            tmp = pre;
            pre = tmp + pre1;
            pre1 = tmp;
        }
        return tmp;
    }

    private static int fib(int n, int[] memo) {
        if (n == 0) {
            memo[0] = 0;
            return 0;
        }
        if (n == 1 || n == 2) {
            memo[n] = 1;
            return 1;
        }

        if (memo[n] != 0) {
            return memo[n];
        } else {
            return memo[n] = fib(n - 2, memo) + fib(n - 1, memo);
        }
    }
}
