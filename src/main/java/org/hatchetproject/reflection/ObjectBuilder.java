package org.hatchetproject.reflection;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by filip on 7/4/15.
 */
public interface ObjectBuilder<T> {
    ObjectBuilder<T> setConstructorValue(int idx, Object value);
    ObjectBuilder<T> setConstructorValues(Object[] values);
    ObjectBuilder<T> clear();
    T build();
}
