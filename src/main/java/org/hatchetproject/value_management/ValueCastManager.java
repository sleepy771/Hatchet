package org.hatchetproject.value_management;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.memory.TimeoutManager;

public class ValueCastManager extends TimeoutManager<Class<? extends ValueCast>, ValueCast> {

    private static ValueCastManager INSTANCE;
    private static Logger LOGGER = Logger.getLogger(ValueCastManager.class);

    @Override
    protected Class<? extends ValueCast> getKey(ValueCast releasable) {
        return releasable.getClass();
    }

    @Override
    protected String toStringKey(Class<? extends ValueCast> aClass) {
        return aClass.getName();
    }

    @Override
    protected String toStringElement(ValueCast releasable) {
        return releasable.toString();
    }

    @Override
    protected void postRelease() {
        INSTANCE = null;
    }

    @Override
    protected ValueCast create(Class<? extends ValueCast> aClass) throws ManagerException {
        try{
            return aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(e);
            throw new ManagerException("Instantiation error occurred: ", e);
        }
    }

    public static ValueCastManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValueCastManager();
        }
        return INSTANCE;
    }
}
