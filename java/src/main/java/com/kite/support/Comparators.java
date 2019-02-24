package com.kite.support;

import com.kite.common.enums.CompareTypeEnum;

public interface Comparators<E> {

    boolean compare(E a, E b, CompareTypeEnum compareTypeEnum);

}
