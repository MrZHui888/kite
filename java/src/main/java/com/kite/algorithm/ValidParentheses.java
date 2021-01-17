package com.kite.algorithm;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * leetcode 20
 * 有效括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ValidParentheses {

    public static void main(String[] args) {

//        Assert.check(isValid("({[)"));


    }

    public static boolean isValid(String s) {
        Map<Character, Character> bucketMap = new HashMap<>();
        bucketMap.put('(', ')');
        bucketMap.put('[', ']');

        char[] chars = s.toCharArray();

        Stack<Character> stack = new Stack<Character>();

        for (Character c : chars) {
            if (stack.empty()) {
                stack.push(c);
                continue;
            }

            Character head = stack.peek();
            Character cc = bucketMap.get(head);
            if (c.equals(cc)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.empty();
    }

    public static boolean isValidV1(String s) {
        short[] bucket = new short[128];

        bucket['('] = '1';
        bucket['['] = '1';
        bucket['{'] = '1';

        bucket[')'] = '(';
        bucket[']'] = '[';
        bucket['}'] = '{';


        Stack stack = new Stack();

        for (char c : s.toCharArray()) {
            if (c == '1') {
                stack.push(c);
            } else if (!stack.empty() && (char) stack.peek() == bucket[c]) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.empty();
    }

}
