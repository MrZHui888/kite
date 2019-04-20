package com.kite.algorithm;

/**
 * @author : Guzh
 * @since : 2019-02-13
 * 斐波那契数列的解法
 * 1,1,2,3,4,5
 * F(1)=1 F(2)=1
 */
public class FibonacciTest {

    public static void main(String[] args) {

        System.out.println(test1(7));
    }

    private static int test1(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return test1(n - 1) + test1(n - 2);
    }
}
