package com.kite.algorithm;

/**
 * 符合下列属性的数组 arr 称为 山脉数组 ：
 * arr.length >= 3
 * 存在 i（0 < i < arr.length - 1）使得：
 * arr[0] < arr[1] < ... arr[i-1] < arr[i]
 * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
 * 给你由整数组成的山脉数组 arr ，返回任何满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i 。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/peak-index-in-a-mountain-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution852 {

    public static void main(String[] args) {
        int[] arr = new int[]{24, 69, 100, 99, 79, 78, 67, 36, 26, 19};

//        int[] arr = new int[]{0, 1, 0};
        System.out.println(peakIndexInMountainArray1(arr));
    }

    public static int peakIndexInMountainArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int tmp = -1;
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                tmp = i;
                break;
            }
        }
        return tmp;
    }

    public static int peakIndexInMountainArray1(int[] arr) {

        int tmp = -1;
        int len = arr.length;
        int left = 1;
        int right = len - 2;

        while (left <= right) {
            int mid = (left + right) >> 1;
            if (arr[mid] > arr[mid + 1]) {
                tmp = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }

        }
        return tmp;
    }
}
