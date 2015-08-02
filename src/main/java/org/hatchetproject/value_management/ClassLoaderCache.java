package org.hatchetproject.value_management;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.AbstractManager;

import java.util.Map;

public class ClassLoaderCache extends AbstractManager<String, Class> {

    private static ClassLoaderCache INSTANCE;

    @Override
    protected String getKeyForElement(Class aClass) {
        return aClass.getName();
    }

    @Override
    protected boolean postRegister(String s, Class aClass) {
        return true;
    }

    @Override
    protected void postUnregister(String s, Class aClass) {
    }

    @Override
    protected String verboseKey(String s) {
        return s;
    }

    @Override
    protected String verboseElement(Class aClass) {
        return aClass.toString();
    }

    @Override
    protected void populate(Map<String, Class> originalMap) {
    }

    @Override
    public boolean isRegistered(Class aClass) {
        return containsKey(aClass.getName());
    }

    @Override
    public boolean isRegistered(String s, Class aClass) {
        return aClass.getName().equals(s) && containsKey(s);
    }

    @Override
    public boolean isKeyRegistered(String s) {
        return containsKey(s);
    }

    @Override
    public Class get(String s) throws ManagerException {
        Class clazz = null;
        if (!containsKey(s)) {
            try {
                clazz = Class.forName(s);
            } catch (ClassNotFoundException cnf) {
                throw new ManagerException("Invalid class name", cnf);
            }
            register(clazz);
        } else {
            clazz = getDirectly(s);
        }
        return clazz;
    }

    public static ClassLoaderCache getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ClassLoaderCache();
        }
        return INSTANCE;
    }

    public static void free() {
        INSTANCE = null;
    }
}
