package org.hatchetproject.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 7/25/15.
 */
public class ConstructorSetter extends AbstractSetter {

    private Constructor constructor;

    public ConstructorSetter(Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    protected final Object performSet(Object objects, Object[] values) throws InstantiationException, InvocationTargetException, IllegalAccessException {
        return constructor.newInstance(values);
    }

    @Override
    public final Class[] getTypes() {
        return constructor.getParameterTypes();
    }
}
