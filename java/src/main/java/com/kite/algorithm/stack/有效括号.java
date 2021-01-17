package com.kite.algorithm.stack;

import java.util.Stack;

class SolutionKuohao {


    public static void main(String[] args) {
        String str = "()[]{}";

        SolutionKuohao solutionKuohao = new SolutionKuohao();


        System.out.println(solutionKuohao.isValid(str));


    }

    public boolean isValid(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        char[] characters = s.toCharArray();
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < characters.length; i++) {
            String characterc = String.valueOf(characters[i]);
            if (stack.empty()) {
                stack.push(characterc);
                continue;
            }

            String top = stack.peek();
            if ("(".equals(top) && characterc.equals(")")) {
                stack.pop();
            } else if ("[".equals(top) && characterc.equals("]")) {
                stack.pop();
            } else if ("{".equals(top) && characterc.equals("}")) {
                stack.pop();
            } else {
                stack.push(characterc);
            }
        }
        return stack.empty();
    }


}
