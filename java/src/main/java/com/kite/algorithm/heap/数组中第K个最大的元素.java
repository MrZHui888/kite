package com.kite.algorithm.heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class Soluttion215 {
    public static void main(String[] args) {
        Soluttion215 soluttion215 = new Soluttion215();
        int[] arr = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        int result = soluttion215.findKthLargest(arr, 4);

        System.out.println(result);

    }

    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        Queue queue = new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return (int) o2 - (int) o1;
            }
        });

        for (int num : nums) {
            queue.offer(num);
        }

        int tmp = 0;
        int result = 0;
        while (tmp < k && k <= nums.length) {
            tmp = tmp + 1;
            result = (int) queue.poll();
        }

        return result;
    }
}
