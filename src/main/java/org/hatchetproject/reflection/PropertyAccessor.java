package org.hatchetproject.reflection;

import org.hatchetproject.value_management.ValueCast;

public interface PropertyAccessor {
    Class getValueClass();

    ValueCast getCaster();

    boolean hasCaster();

    boolean needsCaster();

    PropertyMeta getProperty();

    PropertyGetter getPropertyGetter();

    PropertySetter getPropertySetter();
}
