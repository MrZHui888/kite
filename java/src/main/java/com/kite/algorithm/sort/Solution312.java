package com.kite.algorithm.sort;

/**
 * 戳气球
 */
public class Solution312 {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 1, 5, 8};
//        int[] nums = new int[]{3};
        int coins = maxCoins(nums);
        System.out.println(String.format("最后的金币 :{%s}", coins));

    }


    public static int maxCoins(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[][] dp = new int[n][n];
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    int leftVal = 1;
                    int rightVal = 1;
                    if (i != 0) {
                        leftVal = nums[i - 1];
                    }
                    if (j != n - 1) {
                        rightVal = nums[j + 1];
                    }

                    int before = 0;
                    int after = 0;
                    if (i != k) {
                        before = dp[i][k - 1];
                    }
                    if (j != k) {
                        after = dp[k + 1][j];
                    }
                    dp[i][j] = Math.max(dp[i][j], after + before + leftVal * nums[k] * rightVal);
                }
            }
        }
        return dp[0][n - 1];
    }

}
