package org.hatchetproject.constants;

import org.hatchetproject.value_management.ValueCast;
import org.hatchetproject.value_management.ValueCastSignature;

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

    @Override
    public Class getOutputType() {
        return null;
    }

    @Override
    public Class getInputType() {
        return null;
    }

    @Override
    public ValueCastSignature getSignature() {
        return null;
    }

    @Override
    public ValueCast reverseValueCast() {
        return null;
    }
}
