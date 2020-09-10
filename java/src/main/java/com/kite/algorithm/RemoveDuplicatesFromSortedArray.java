package com.kite.algorithm;

/**
 * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RemoveDuplicatesFromSortedArray {


    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};// 5
        System.out.println(removeDuplicates(nums));

    }

    public static int removeDuplicates(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        int index = 0;
        int indexValue = nums[0];

        int t = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != indexValue) {
                index = index + 1;
                nums[index] = nums[i];
                indexValue = nums[i];
            }


        }
        return index + 1;
    }
}
