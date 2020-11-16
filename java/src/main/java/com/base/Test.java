package com.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        List<Object> list = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        Set set = Sets.newHashSet();

        list.parallelStream().forEach(x->{
            Singleton singleton = Singleton.getInstance();
            System.out.println(Thread.currentThread().getName() + ":" + singleton.hashCode());
            set.add(singleton.hashCode());
        });


        System.out.println(set);





    }
}
