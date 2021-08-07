package com.kite.algorithm;


import java.util.ArrayDeque;
import java.util.Deque;

public class Solution316 {
    public static void main(String[] args) {
        String s = "bcabc";
        Solution316 solution = new Solution316();
        String s1 = solution.removeDuplicateLetters(s);
        System.out.println(s1);
    }

    public String removeDuplicateLetters(String s) {
        char[] chars = s.toCharArray();
        int length = s.length();
        Deque<Character> stack = new ArrayDeque<>();
        boolean[] vis = new boolean[26];
        int[] lastIndex = new int[26];
        for (int i = 0; i < length; i++) {
            if (stack.isEmpty()) {
                stack.add(chars[i]);
            }
            Character peek = stack.peek();
        }
        return "";
    }


}
