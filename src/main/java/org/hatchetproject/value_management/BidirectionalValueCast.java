package org.hatchetproject.value_management;

/**
 * Created by filip on 6/28/15.
 */
interface BidirectionalValueCast<FIRST_TYPE, SECOND_TYPE> extends ValueCast<FIRST_TYPE, SECOND_TYPE> {
    FIRST_TYPE reverseCast(SECOND_TYPE output) throws ClassCastException;
}
