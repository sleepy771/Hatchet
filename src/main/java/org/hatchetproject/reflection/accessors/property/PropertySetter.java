package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.reflection.accessors.Setter;

public interface PropertySetter {
    void set(Object destination, Object value) throws PropertySetterException;

    int getIndex(); // Maybe setterHelper would be better, lika callback

    void setHelper(PropertySetterHelper helper);

    PropertySetterHelper getHelper();

    Setter getSetter();
}
