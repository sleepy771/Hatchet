package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.reflection.accessors.Setter;
import org.hatchetproject.reflection.accessors.property.helpers.PropertySetterHelper;

public interface PropertySetter {
    void set(Object destination, Object value) throws PropertySetterException;

    void setHelper(PropertySetterHelper helper);

    PropertySetterHelper getHelper();

    Setter getSetter();
}
