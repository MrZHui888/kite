package com.kite.algorithm;

public class MySort {
    public static void main(String[] args) {
        int[] a = {21, 3, 44, 1, 53, 22, 1, 2, 3, 7};
        quitSort(a, 0, a.length-1);
        System.out.println(a);
    }

    private static void quitSort(int[] a, int left, int right) {
        if (left < right) {
            int mode = getModer(a, left, right);
            quitSort(a, left, mode - 1);
            quitSort(a, mode + 1, right);
        }
    }

    private static int getModer(int[] a, int left, int right) {
        int tmp = a[left];
        while (left < right) {
            while (left < right && a[right] >= tmp) {
                right--;
            }
            a[left] = a[right];

            while (left < right && a[left] <= tmp) {
                left++;
            }

            a[right] = a[left];
        }
        a[left] = tmp;
        return left;
    }


}
