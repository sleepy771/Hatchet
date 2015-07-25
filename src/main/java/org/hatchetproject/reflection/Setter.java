package org.hatchetproject.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 7/25/15.
 */
public interface Setter {
    Class[] getTypes();

    Object set(Object object, Object[] values) throws InstantiationException, InvocationTargetException, IllegalAccessException;
}
