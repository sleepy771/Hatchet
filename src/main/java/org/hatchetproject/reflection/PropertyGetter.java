package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyGetterException;

public interface PropertyGetter extends PropertyAccessor {
    Object get(Object source) throws PropertyGetterException;
}
