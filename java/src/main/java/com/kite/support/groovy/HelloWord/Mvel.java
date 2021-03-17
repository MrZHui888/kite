package com.kite.support.groovy.HelloWord;

import org.mvel2.MVEL;

import java.util.HashMap;

public class Mvel {


    public static void main(String[] args) {
        Object a = MVEL.eval("a == 1", new HashMap<String, Object>() {
            {
                put("a", 2);
            }
        });

    }
}
