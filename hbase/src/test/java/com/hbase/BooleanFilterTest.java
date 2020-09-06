package com.hbase;

import com.google.common.base.Stopwatch;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.concurrent.TimeUnit;

/**
 * 布隆过滤器使用
 */


public class BooleanFilterTest {


    public static void main(String[] args) {
    }

    public static void executeTime() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                1000,
                0.01);


        filter.put(121);


        filter.put(12211);



        filter.mightContain(121);


        long cost =  stopwatch.elapsed(TimeUnit.MICROSECONDS);





    }
}
