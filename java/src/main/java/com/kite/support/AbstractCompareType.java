package com.kite.support;

import java.util.Objects;

/**
 * 比较器类型
 * @param <E>
 * @see com.kite.common.enums.CompareTypeEnum
 */
public abstract class AbstractCompareType<E extends Object> {


    protected boolean moreThan(E a,E b){
        return false;
    }

    protected boolean moreThanEqualTo(E a,E b){
        return false;
    }

    protected boolean lessThan(E a,E b){
        return false;
    }

    protected boolean lessThanEqualTo(E a,E b){
        return false;
    }

    protected boolean equalTo(E a,E b){
        return Objects.equals(a,b);
    }

    protected boolean notEqualTo(E a,E b){
        return !equalTo(a, b);
    }


}
