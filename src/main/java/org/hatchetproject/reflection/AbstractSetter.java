package org.hatchetproject.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 7/25/15.
 */
public abstract class AbstractSetter implements Setter {

    @Override
    public final Object set(Object object, Object[] values) throws InstantiationException, InvocationTargetException, IllegalAccessException {
        checkValues(values);
        return performSet(object, values);
    }

    protected abstract Object performSet(Object objects, Object[] values) throws InstantiationException, InvocationTargetException, IllegalAccessException;

    protected final void checkValues(Object[] values) throws ClassCastException {
        Class[] paramTypes = getTypes();
        if (values.length != paramTypes.length)
            throw new IllegalArgumentException("Invalid arguments size");
        for (int k = 0; k < paramTypes.length; k++) {
            if (!paramTypes[k].isInstance(values[k]))
                throw new ClassCastException(values[k].getClass().getName() + " is not instance of " + paramTypes[k].getName());
        }
    }
}
