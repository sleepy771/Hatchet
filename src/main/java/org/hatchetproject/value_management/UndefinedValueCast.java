package org.hatchetproject.value_management;

import org.hatchetproject.manager.memory.ReleaseManager;

/**
 * Created by filip on 7/2/15.
 */
public class UndefinedValueCast implements ValueCast {

    private UndefinedValueCast() {
        throw new UnsupportedOperationException("NOPE");
    }

    @Override
    public Object cast(Object o) throws ClassCastException {
        return null;
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
