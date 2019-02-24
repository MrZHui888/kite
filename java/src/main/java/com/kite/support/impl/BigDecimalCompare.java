package com.kite.support.impl;

import com.kite.support.AbstractObjectAbstractCompare;

import java.math.BigDecimal;

public class BigDecimalCompare extends AbstractObjectAbstractCompare<BigDecimal> {
    @Override
    protected boolean moreThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    @Override
    protected boolean moreThanEqualTo(BigDecimal a, BigDecimal b) {
        return super.moreThanEqualTo(a, b);
    }

    @Override
    protected boolean lessThan(BigDecimal a, BigDecimal b) {
        return super.lessThan(a, b);
    }

    @Override
    protected boolean lessThanEqualTo(BigDecimal a, BigDecimal b) {
        return super.lessThanEqualTo(a, b);
    }

    @Override
    protected boolean equalTo(BigDecimal a, BigDecimal b) {
        return super.equalTo(a, b);
    }

    @Override
    protected boolean notEqualTo(BigDecimal a, BigDecimal b) {
        return super.notEqualTo(a, b);
    }
}
