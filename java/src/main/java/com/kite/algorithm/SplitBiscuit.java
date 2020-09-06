package com.kite.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 分饼干
 */
public class SplitBiscuit {


    public static void main(String[] args) {

        List<List<Integer>> result = new ArrayList<>();

        List<Integer> one = new ArrayList<>();
        one.add(7);
        one.add(8);
        one.add(9);
        result.add(one);

        List<Integer> two = new ArrayList<>();
        two.add(9);
        two.add(8);
        result.add(two);


        int num = numberWays(result);
        System.out.println(num);

    }

    public static int numberWays(List<List<Integer>> candies) {

        int n = candies.size();
        // 初始化20 个糖果
        List<Integer>[] cTop = new List[20];
        for (int p = 0; p < n; p++) {//人
            for (int candy : candies.get(p)) {
                if (cTop[candy] == null) {
                    cTop[candy] = new ArrayList<>();
                }
                cTop[candy].add(p);
            }
        }

        int marked = 0;
        int allMark = (1 << n) - 1;
        Integer[][] dp = new Integer[20][allMark + 1];
        return dfs(cTop, marked, allMark, 1, dp);
    }

    private static int dfs(List<Integer>[] cToP, int marked, int allMark, int candy, Integer[][] dp) {

        if (candy == 20) {
            //所有人都已经戴上了帽子
            if (marked == allMark) {
                return 1;
            }
            return 0;
        }

        //已经计算过
        if (dp[candy][marked] != null) {
            return dp[candy][marked];
        }

        int res = 0;

        //糖果不分配给任何人
        res += dfs(cToP, marked, allMark, candy + 1, dp);

        if (cToP[candy] == null) {
            return res;
        }
        ;

        for (int p : cToP[candy]) {
            if (((marked >> p) & 1) == 1) {
                continue;
            }
            res += dfs(cToP, marked | (1 << p), allMark, candy + 1, dp);
        }
        dp[candy][marked] = res;
        return res;
    }
}
