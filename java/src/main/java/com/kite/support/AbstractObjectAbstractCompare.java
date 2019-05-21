package com.kite.support;

import com.kite.common.enums.CompareTypeEnum;

/**
 * 对象类比较器
 *
 * @param <E>
 */
public abstract class AbstractObjectAbstractCompare<E> extends AbstractCompareType<E>
        implements Comparators<E> {
    @Override
    public boolean compare(E a, E b, CompareTypeEnum compareTypeEnum) {
        if (a == null || b == null) {
            return false;
        }
        if (compareTypeEnum == null) {
            return false;
        }
        switch (compareTypeEnum) {
            case MORE_THAN:
                return moreThan(a, b);
            case MORE_THAN_EQUAL_TO:
                return moreThanEqualTo(a, b);
            case LESS_THAN:
                return lessThan(a, b);
            case LESS_THAN_EQUAL_TO:
                return lessThanEqualTo(a, b);
            case EQUAL_TO:
                return equalTo(a, b);
            case NOT_EQUAL_TO:
                return notEqualTo(a, b);
        }
        return false;
    }


}
