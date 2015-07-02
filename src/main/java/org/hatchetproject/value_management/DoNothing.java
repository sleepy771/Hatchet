package org.hatchetproject.value_management;

import org.hatchetproject.manager.memory.ReleaseManager;

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
    public void free() {

    }

    @Override
    public boolean isReleased() {
        return false;
    }

    @Override
    public void setReleaseManager(ReleaseManager manager) {

    }

    @Override
    public ReleaseManager getReleaseManager() {
        return null;
    }

    @Override
    public boolean isAssigned() {
        return false;
    }
}
