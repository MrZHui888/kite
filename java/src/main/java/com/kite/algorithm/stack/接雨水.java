package com.kite.algorithm.stack;

class Soluttion {

    public static void main(String[] args) {
        Soluttion soluttion = new Soluttion();
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(soluttion.trap(height));

    }

    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int ans = 0;
        int heightLength = height.length;
        int[] leftMax = new int[heightLength], rightMax = new int[heightLength];
        leftMax[0] = height[0];
        for (int i = 1; i < heightLength; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }

        rightMax[heightLength - 1] = height[heightLength - 1];
        for (int i = heightLength - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }

        for (int i = 1; i < heightLength - 1; i++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;

    }
}
