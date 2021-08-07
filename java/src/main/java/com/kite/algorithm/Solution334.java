package com.kite.algorithm;

public class Solution334 {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 4, 3, 2, 1};

        Solution334 solution334 = new Solution334();
        boolean b = solution334.increasingTriplet(nums);
        System.out.println(b);


    }

    public boolean increasingTriplet(int[] nums) {
        int min = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int n : nums) {
            if (min > n) {
                min = n;
                continue;
            }

            if (n > min && n < second) {
                second = n;
                continue;
            }

            if (n > second) {
                return true;
            }
        }
        return false;
    }
}
