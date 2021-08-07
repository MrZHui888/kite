package com.kite.algorithm;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author gzh
 */
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
        int[] lastIndex = new int[26];
        for (int i = 0; i < length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }

        Deque<Character> stack = new ArrayDeque();
        boolean[] visited = new boolean[26];
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            // 1. 先看没有的
            // 1.查看栈里边不为空
            //2.栈头里边的数小于c
            if (!visited[c - 'a']) {
                //若栈为空，或当前元素大于栈顶元素，或栈顶元素在当前字符后不再出现，当前字符压栈
                while (!stack.isEmpty() && c < stack.peek() &&
                        lastIndex[stack.peek() - 'a'] > i) {
                    visited[stack.pop() - 'a'] = false;
                }
                visited[c - 'a'] = true;
                stack.push(c);
            }

        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
}
