package org.hatchetproject.value_management;

/**
 * Created by filip on 6/27/15.
 */
public class DoNothing implements ValueCast {

    private DoNothing() {
        throw new UnsupportedOperationException("NOPE");
    }

    @Override
    public Object cast(Object o) {
        return o;
    }
}