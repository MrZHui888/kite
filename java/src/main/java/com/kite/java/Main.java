package com.kite.java;

import lombok.Data;

import java.math.BigDecimal;

public class Main {

    public static void main1(String[] args) {
        Foo foo = new Foo("A");

        System.out.println(foo.toString());
        modifyReference(foo);
        System.out.println(foo.toString());
        changeReference(foo);
        System.out.println(foo.toString());
    }


    public static void main(String[] args) {
        BigDecimal decimal = BigDecimal.valueOf(108.12);
        BigDecimal decimal1 = new BigDecimal(108.12);
        BigDecimal decimal2 = new BigDecimal(108.12);

        BigDecimal decimal3 = BigDecimal.valueOf(decimal1.doubleValue());


        System.out.println(decimal.compareTo(decimal1));
        System.out.println(decimal1.compareTo(decimal2));

    }

    public static void modifyReference(Foo foo) {
        foo.setName(" 修改过了A");
    }

    public static void changeReference(Foo foo) {
        Foo foo1 = new Foo("B");
        foo = foo1;
    }


}

@Data
class Foo {
    private String name;

    public Foo(String name) {
        this.name = name;
    }
}
