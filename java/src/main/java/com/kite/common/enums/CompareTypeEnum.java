package com.kite.common.enums;


import com.kite.support.AbstractCompareType;

/**
 * 比较类型
 *
 * @see AbstractCompareType
 */
public enum CompareTypeEnum {

    MORE_THAN("大于", ">"),
    MORE_THAN_EQUAL_TO("大于等于", ">="),
    LESS_THAN("小于", "<"),
    LESS_THAN_EQUAL_TO("小于等于", "<="),
    EQUAL_TO("等于", "=="),
    NOT_EQUAL_TO("不等于", "!=");

    private String value;
    private String symbol;

    CompareTypeEnum(String value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }}
