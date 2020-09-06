package com.kite.algorithm;

import com.google.common.base.Joiner;

import java.util.LinkedList;
import java.util.List;

public class Lamp {


    public static void main(String[] args) {

        int num = 200;
        // 记录本
        boolean[] numPrime = new boolean[num + 1];
        List<Integer> totalLamps = new LinkedList();
        for (int i = 1; i <= num; i++) {
            numPrime[i] = checkNumIsPrime(i);
            totalLamps.add(i);
        }

        boolean numsAnyPrime = true;

        while (numsAnyPrime) {
            int currentSize = totalLamps.size();
            // 从大到小 删除

            for (int i = currentSize - 1; i >= 0; i--) {
                if (numPrime[i]) {
                    totalLamps.remove(i);
                }
            }


            System.out.println(Joiner.on(",").join(totalLamps));
            int endSize = totalLamps.size();

            if (currentSize == endSize) {
                numsAnyPrime = false;
            }
            System.out.println(totalLamps.size());

        }

        System.out.println(totalLamps);


    }


    /**
     * 素数检查
     *
     * @param n
     * @return
     */
    private static boolean checkNumIsPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
