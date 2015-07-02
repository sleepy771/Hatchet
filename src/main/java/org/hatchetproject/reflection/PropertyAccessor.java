package org.hatchetproject.reflection;

import org.hatchetproject.value_management.ValueCast;

/**
 * Created by filip on 7/2/15.
 */
public interface PropertyAccessor {

    Class getDeclaringClass();

    Class getValueClass();

    ValueCast getCaster();

    IProperty getProperty();
}
