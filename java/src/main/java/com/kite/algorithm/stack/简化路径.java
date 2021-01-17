package com.kite.algorithm.stack;

import java.util.Stack;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/simplify-path/
 * leetcode 71题目
 * <p>
 * 输入："/home/"
 * 输出："/home"
 * 解释：注意，最后一个目录名后面没有斜杠。
 * <p>
 * 输入："/../"
 * 输出："/"
 * 解释：从根目录向上一级是不可行的，因为根是你可以到达的最高级。
 * <p>
 * 输入："/home//foo/"
 * 输出："/home/foo"
 * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
 */
class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String path = "/home//foo/";
        System.out.println(solution.simplifyPath(path));

        path = "/../";
        System.out.println(solution.simplifyPath(path));

        path = "/home//foo/";
        System.out.println(solution.simplifyPath(path));

        path = "/a/./b/../../c/";
        System.out.println(solution.simplifyPath(path));


    }

    public String simplifyPath(String path) {
        if (path == null || path.length() == 0) {
            return "/";
        }

        String[] splits = path.split("/");
        int nums = splits.length;

        Stack<String> stack = new Stack<>();


        for (int i = 0; i < nums; i++) {
            String str = splits[i];
            if (str.equals("") || str.equals(".")) {
                continue;
            }

            if ("..".equals(str)) {
                if (stack.isEmpty()) {
                    continue;
                }
                stack.pop();
            } else {
                stack.push(str);
            }
        }

        return "/" + String.join("/", stack.stream().collect(Collectors.toList()));
    }
}
