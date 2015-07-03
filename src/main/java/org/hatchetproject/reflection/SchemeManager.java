package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.memory.TimeoutManager;

/**
 * Created by filip on 6/29/15.
 */
public class SchemeManager extends TimeoutManager<Class, Scheme> {

    private static SchemeManager INSTANCE;

    private SchemeManager() {
    }

    @Override
    protected Class getKey(Scheme releasable) {
        return releasable.getForClass();
    }

    @Override
    protected String toStringKey(Class clazz) {
        return clazz.getName();
    }

    @Override
    protected String toStringElement(Scheme releasable) {
        return releasable.toString();
    }

    @Override
    protected Scheme create(Class clazz) throws ManagerException {
        // TODO make scheme generator
        return null;
    }

    @Override
    protected void postRelease() {
        INSTANCE = null;
    }

    public static SchemeManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchemeManager();
        }
        return INSTANCE;
    }
}
