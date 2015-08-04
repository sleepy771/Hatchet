package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.Getter;

public interface PropertyGetter {
    Object get(Object source) throws PropertyGetterException;

    Object getRaw(Object source) throws PropertyGetterException;

    void setAssignHelper(PropertyGetterHelper helper);

    PropertyGetterHelper getAssignHelper();

    Getter getGetter();
}
