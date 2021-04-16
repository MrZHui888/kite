package com.kite.algorithm.common;

public class Fibonacca {
    public static void main(String[] args) {
        System.out.println(fio1(45));
//        System.out.println(fio(45));

        int[] ints = new int[]{0, 1, 0, 2, 222};

        moveZeroes(ints);

        System.out.println(ints);

    }

    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        // 记录0的
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j++] = nums[i];
            }
        }
        //补充0
        for (int i = j; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    public static int fio1(int n) {

        if (n < 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    public static int fio(int n) {


        int[] dp = new int[n + 1];
        return getFib(dp, n);
    }


    public static int getFib(int[] cache, int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (cache[n] != 0) {
            return cache[n];
        }
        return getFib(cache, n - 1) + getFib(cache, n - 2);
    }
}
