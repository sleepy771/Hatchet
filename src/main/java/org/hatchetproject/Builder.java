package org.hatchetproject;

import org.hatchetproject.exceptions.PropertyGetterException;

public interface Builder<Type> {
    Type build() throws Exception, PropertyGetterException;
}
