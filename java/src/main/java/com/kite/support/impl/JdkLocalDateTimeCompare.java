package com.kite.support.impl;

import com.kite.support.AbstractObjectAbstractCompare;

import java.time.LocalDateTime;

public class JdkLocalDateTimeCompare extends AbstractObjectAbstractCompare<LocalDateTime> {

    @Override
    protected boolean moreThan(LocalDateTime a, LocalDateTime b) {
        return super.moreThan(a, b);
    }

    @Override
    protected boolean moreThanEqualTo(LocalDateTime a, LocalDateTime b) {
        return super.moreThanEqualTo(a, b);
    }

    @Override
    protected boolean lessThan(LocalDateTime a, LocalDateTime b) {
        return super.lessThan(a, b);
    }

    @Override
    protected boolean lessThanEqualTo(LocalDateTime a, LocalDateTime b) {
        return super.lessThanEqualTo(a, b);
    }

    @Override
    protected boolean equalTo(LocalDateTime a, LocalDateTime b) {
        return super.equalTo(a, b);
    }

    @Override
    protected boolean notEqualTo(LocalDateTime a, LocalDateTime b) {
        return super.notEqualTo(a, b);
    }
}
