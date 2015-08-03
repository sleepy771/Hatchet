package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.Getter;

public interface PropertyGetter {
    Object get(Object source) throws PropertyGetterException;

    Object getRaw(Object source) throws PropertyGetterException;

    Getter getGetter();
}
