package org.hatchetproject.value_management;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.DefaultAbstractManager;

import java.util.Map;

public final class ValueCastManager extends DefaultAbstractManager<ValueCastSignature, ValueCast> {

    private static final Logger LOGGER = Logger.getLogger(ValueCastManager.class);

    private static ValueCastManager INSTANCE;

    private Map<Class<? extends ValueCast>, ValueCast> castMap;

    private ValueCastManager() {
        super();
    }

    @Override
    protected ValueCastSignature getKeyForElement(ValueCast valueCast) {
        return valueCast.getSignature();
    }

    @Override
    protected final boolean postRegister(ValueCastSignature signature, ValueCast cast) {
        if (castMap.containsKey(cast.getClass())) {
            return false;
        }
        castMap.put(cast.getClass(), cast);
        return true;
    }

    @Override
    public final void postUnregister(ValueCastSignature signature, ValueCast cast) {
        castMap.remove(cast.getClass(), cast);
    }

    public final ValueCast getOrCreate(Class<? extends ValueCast> clazz) throws ManagerException {
        if (castMap.containsKey(clazz)) {
            return castMap.get(clazz);
        }
        try {
            ValueCast caster = clazz.newInstance();
            register(caster);
            return caster;
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Caste has not default constructor", e);
            throw new ManagerException("Caster class has not default accessible constructor!", e);
        }
    }

    public final boolean isRegisteredForKey(Class<? extends ValueCast> clazz) {
        return null != clazz && castMap.containsKey(clazz);
    }

    public final boolean isRegistered(Class<? extends ValueCast> casterClass, ValueCast caster) {
        if (null == casterClass || null == caster) {
            return false;
        }
        ValueCast registered = castMap.get(casterClass);
        return registered.equals(caster);
    }

    public static ValueCastManager getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ValueCastManager();
        }
        return INSTANCE;
    }
}
