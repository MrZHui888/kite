package com.kite.algorithm.str;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * leetcode 49 题目
 * // 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 * //输出:
 * //[
 * //  ["ate","eat","tea"],
 * //  ["nat","tan"],
 * //  ["bat"]
 * //]
 */
public class 字母异位词分组 {


    public static void main(String[] args) {
        String[] a = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};

        List<List<String>> lists = groupAnagrams(a);

        System.out.println(JSONObject.toJSONString(lists));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        if (strs == null || strs.length == 0) {
            return result;
        }
        if (strs.length == 1) {
            List<String> sameOneStr = new ArrayList<>();
            sameOneStr.add(strs[0]);
            result.add(sameOneStr);
            return result;
        }

        Map<String, List<String>> map = new HashMap<>(strs.length);
        for (String str : strs) {
            char[] strArray = str.toCharArray();
            Arrays.sort(strArray);
            String sortStr = String.valueOf(strArray);
            List<String> sameWordStr = map.get(sortStr);
            if (sameWordStr == null || sameWordStr.size() == 0) {
                sameWordStr = new ArrayList<>();
                sameWordStr.add(str);
                map.put(sortStr, sameWordStr);
            } else {
                sameWordStr.add(str);
            }
        }

        result.addAll(map.values());
        return result;
    }

}
