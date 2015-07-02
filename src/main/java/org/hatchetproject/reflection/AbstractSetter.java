package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.ValueCast;

public abstract class AbstractSetter implements PropertySetter {

    private PropertyAccessor accessor;

    @Override
    @SuppressWarnings("unchecked")
    public final void set(Object destination, Object value) throws PropertySetterException {
        if (!destination.getClass().equals(getDeclaringClass()))
            throw new PropertySetterException("Wrong declaring class instance");
        if (!getValueClass().isInstance(value))
            throw new PropertySetterException("Invalid value type");
        if (!performSet(destination, getCaster() != null? getCaster().cast(value) : value)) {
            throw new PropertySetterException("Set was not successful");
            // todo create other exception type
        }
    }

    protected abstract boolean performSet(Object destination, Object value);

    @Override
    public final Class getDeclaringClass() {
        return accessor.getDeclaringClass();
    }

    @Override
    public final Class getValueClass() {
        return accessor.getValueClass();
    }

    @Override
    public final ValueCast getCaster() {
        return accessor.getCaster();
    }

    @Override
    public final IProperty getProperty() {
        return accessor.getProperty();
    }
}
