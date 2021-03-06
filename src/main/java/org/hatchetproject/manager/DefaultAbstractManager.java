package org.hatchetproject.manager;

import org.hatchetproject.exceptions.ManagerException;

import java.util.Map;

public abstract class DefaultAbstractManager<KEY, ELEMENT> extends AbstractManager<KEY, ELEMENT> {

    @Override
    public final boolean isRegistered(ELEMENT element) {
        return null != element && contains(getKeyForElement(element), element);
    }

    @Override
    public final boolean isRegistered(KEY key, ELEMENT element) {
        return contains(key, element);
    }

    @Override
    public final boolean isKeyRegistered(KEY key) {
        return containsKey(key);
    }

    @Override
    public final ELEMENT get(KEY key) throws ManagerException {
        try {
            ELEMENT element = getDirectly(key);
            if (null == element) {
                throw new ManagerException("Undefined uid");
            }
            return element;
        } catch (NullPointerException e) {
            throw new ManagerException("Invalid uid", e);
        }
    }

    @Override
    protected boolean postRegister(KEY key, ELEMENT element) {
        return true;
    }

    @Override
    protected void postUnregister(KEY key, ELEMENT element) {
    }

    @Override
    protected void populate(Map<KEY, ELEMENT> originalMap){
    }

    @Override
    protected String verboseKey(KEY key) {
        return key.toString();
    }

    @Override
    protected String verboseElement(ELEMENT element) {
        return element.toString();
    }
}
