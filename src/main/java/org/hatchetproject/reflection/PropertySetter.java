package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertySetterException;

public interface PropertySetter extends PropertyAccessor {
    void set(Object destination, Object value) throws PropertySetterException;
}
