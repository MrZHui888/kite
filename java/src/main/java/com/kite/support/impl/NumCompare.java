package com.kite.support.impl;

import com.kite.support.AbstractObjectAbstractCompare;

public class NumCompare extends AbstractObjectAbstractCompare<Number> {
    @Override
    protected boolean moreThan(Number a, Number b) {
        return a.doubleValue() > b.doubleValue();
    }

    @Override
    protected boolean moreThanEqualTo(Number a, Number b) {
        return a.doubleValue() >= b.doubleValue();
    }

    @Override
    protected boolean lessThan(Number a, Number b) {
        return a.doubleValue() < b.doubleValue();
    }

    @Override
    protected boolean lessThanEqualTo(Number a, Number b) {
        return a.doubleValue() <= b.doubleValue();
    }

    @Override
    protected boolean equalTo(Number a, Number b) {
        return a.doubleValue() == b.doubleValue();
    }

    @Override
    protected boolean notEqualTo(Number a, Number b) {
        return a.doubleValue() != b.doubleValue();
    }
}
