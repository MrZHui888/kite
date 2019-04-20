package com.kite.support.impl;

import com.kite.common.enums.CompareTypeEnum;
import com.kite.support.Comparators;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        Comparators v= new ObjectAbstractCompare();
        System.out.println(v.compare("21","21", CompareTypeEnum.EQUAL_TO));


        Comparators localDate=new JdkLocalDateCompare();
        System.out.println(localDate.compare(LocalDate.now(),LocalDate.now(),CompareTypeEnum.LESS_THAN_EQUAL_TO));

        Comparators num=new NumCompare();
        System.out.println(num.compare(1,2,CompareTypeEnum.LESS_THAN_EQUAL_TO));
    }
}
