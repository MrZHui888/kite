package com.kite.algorithm.common;

public class CuttingRope {
    public static void main(String[] args) {

//        System.out.printlnΩln(cuttingRope(1));
        System.out.println(cuttingRope1(10));

    }

    public static int cuttingRope(int n) {
        if (n <= 3) {
            return n - 1;
        }

        int res = 1;
        while (n > 4) {
            res = res * 3;
            n = n - 3;
        }
        return res * n;
    }

    public static int cuttingRope1(int n) {
        if (n <= 3) {
            return n - 1;
        }
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i < n + 1; i++) {
            for (int j = 2; j < i; j++) {
                dp[i] = Math.max(dp[i],
                        Math.max(j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }
}
