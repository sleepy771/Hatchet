package org.hatchetproject.value_management;

/**
 * Created by filip on 7/2/15.
 */
public abstract class CastCallback<INPUT, OUTPUT> implements ValueCast<INPUT, OUTPUT> {

    @Override
    public ValueCastSignature getSignature() {
        return null;
    }

    @Override
    public Class<? extends INPUT> getInputType() {
        return null;
    }

    @Override
    public Class<? extends OUTPUT> getOutputType() {
        return null;
    }
}
