package com.kite.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 去掉字符串中连续出现k个0的字符串
 * 比如 str="A00B" k=2  返回 "A00B"
 * String ="A000B000" k=3 返回 "A000B"
 */
public class RemoveKZeros {

    public static void main(String[] args) {

        System.out.println(removeKZeros("A000B000C000", 3));
        List list=new ArrayList();
        list.remove(12);

        List list1=new LinkedList();
        list1.remove(12);

    }

    private static String removeKZeros(String str, int k) {
        if (str == null || k < 1) {
            return "";
        }

        char[] chars = str.toCharArray();

        int count = 0;
        int start = -1;
        boolean isFirst = false;
        for (int i = 0; i != chars.length; i++) {
            if (chars[i] == '0') {
                count++;
                start = start == -1 ? i : start;
            } else {
                if (count == k) {

                    while (count-- != 0 && isFirst) {
                        chars[start++] = 0;
                    }
                    count = 0;
                    start = -1;
                    isFirst = true;
                }
            }
        }

//        if (count == k) {
//            while (count-- != 0&&isFirst) {
//                chars[start++] = 0;
//            }
//        }


        return String.valueOf(chars);
    }
}
