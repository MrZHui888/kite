package com.kite.common.enums;

/**
 * 逻辑运算符号
 */
public enum LogicalOperatorsEnum {

    OR("||"),
    AND("&&");
    private String value;

    LogicalOperatorsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }}
