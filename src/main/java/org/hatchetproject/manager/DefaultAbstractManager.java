package org.hatchetproject.manager;

import org.hatchetproject.exceptions.ManagerException;

/**
 * Created by filip on 6/27/15.
 */
public abstract class DefaultAbstractManager<KEY, ELEMENT> extends AbstractManager<KEY, ELEMENT> {

    @Override
    public final boolean isRegistered(ELEMENT element) {
        return element != null && contains(getKeyForElement(element), element);
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
            if (element == null) {
                throw new ManagerException("Undefined key");
            }
            return element;
        } catch (NullPointerException e) {
            throw new ManagerException("Invalid key", e);
        }
    }

    @Override
    protected void postRegister(KEY key, ELEMENT element) {
    }

    @Override
    protected void postUnregister(KEY key, ELEMENT element) {
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
