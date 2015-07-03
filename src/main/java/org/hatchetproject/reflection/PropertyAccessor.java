package org.hatchetproject.reflection;

import org.hatchetproject.value_management.ValueCast;

public interface PropertyAccessor {

    Class getDeclaringClass();

    Class getValueClass();

    ValueCast getCaster();

    boolean hasCaster();

    boolean needsCaster();

    IProperty getProperty();
}
