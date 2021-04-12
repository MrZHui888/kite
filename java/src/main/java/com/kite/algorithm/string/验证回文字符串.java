package com.kite.algorithm.string;

public class 验证回文字符串 {


    public boolean validPalindrome(String s) {

        int front = 0;
        int end = s.length() - 1;

        while (front < end) {
            if (s.charAt(front) != s.charAt(end)) {
                return validPalindromeCheck(s, front + 1, end) ||
                        validPalindromeCheck(s, front, end - 1
                        );
            }
            front++;
            end--;
        }

        return true;
    }

    private boolean validPalindromeCheck(String s, int front, int end) {
        while (front < end) {
            if (s.charAt(front) != s.charAt(end)) {
                return false;
            }
            front++;
            end--;
        }
        return true;
    }

}
