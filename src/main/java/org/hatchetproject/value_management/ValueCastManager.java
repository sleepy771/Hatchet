package org.hatchetproject.value_management;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.AbstractManager;
import org.hatchetproject.manager.DefaultAbstractManager;
import org.hatchetproject.manager.memory.TimeoutManager;

public class ValueCastManager extends DefaultAbstractManager<ValueCastSignature, ValueCast> {

    @Override
    protected ValueCastSignature getKeyForElement(ValueCast valueCast) {
        return valueCast.getSignature();
    }

    @Override
    protected void postRegister(ValueCastSignature valueCastSignature, ValueCast valueCast) {
    }

    @Override
    protected void postUnregister(ValueCastSignature valueCastSignature, ValueCast valueCast) {
    }

    @Override
    protected String verboseKey(ValueCastSignature valueCastSignature) {
        return valueCastSignature.toString();
    }

    @Override
    protected String verboseElement(ValueCast valueCast) {
        return valueCast.toString();
    }
}
