package com.kite.generater;

public class IdGenTest {

    public static void main(String[] args) {
        UniqueIdGen idGen = UniqueIdGen.getInstance(5);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1024000; ++i) {
            idGen.nextId();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start));

    }
}
