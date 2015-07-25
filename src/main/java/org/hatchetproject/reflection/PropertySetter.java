package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.RegistrableValue.ValueSignature;

import java.util.Map;

public interface PropertySetter extends PropertyAccessor {
    void set(Object destination, Object value) throws PropertySetterException;

    Map<Integer, ValueSignature> getInjects();
}
