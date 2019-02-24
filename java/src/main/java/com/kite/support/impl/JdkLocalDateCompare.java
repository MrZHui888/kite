package com.kite.support.impl;

import com.kite.support.AbstractObjectAbstractCompare;

import java.time.LocalDate;


public class JdkLocalDateCompare extends AbstractObjectAbstractCompare<LocalDate> {

    @Override
    protected boolean moreThan(LocalDate a, LocalDate b) {
        return false;
    }

    @Override
    protected boolean moreThanEqualTo(LocalDate a, LocalDate b) {
        return super.moreThanEqualTo(a, b);
    }

    @Override
    protected boolean lessThan(LocalDate a, LocalDate b) {
        return super.lessThan(a, b);
    }

    @Override
    protected boolean lessThanEqualTo(LocalDate a, LocalDate b) {
        return super.lessThanEqualTo(a, b);
    }

    @Override
    protected boolean equalTo(LocalDate a, LocalDate b) {
        return super.equalTo(a, b);
    }

    @Override
    protected boolean notEqualTo(LocalDate a, LocalDate b) {
        return super.notEqualTo(a, b);
    }
}
