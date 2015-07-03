package org.hatchetproject.reflection;

import org.hatchetproject.annotations.Property;
import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodPropertySetter extends PropertyAccessorBase implements PropertySetter {

    private Method method;
    private ValueCast caster;
    private IProperty property;

    MethodPropertySetter(Method method, ValueCast caster) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
        this.method = method;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        if (!(getDeclaringClass().isInstance(destination) && getValueClass().isInstance(value))) {
            throw new PropertySetterException("Invalid types passed");
        }
        if (hasCaster()) {
            value = getCaster().cast(value);
        }
        try {
            method.invoke(destination, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PropertySetterException("Can not execute method", e);
        }
    }

    @Override
    public Class getDeclaringClass() {
        return method.getDeclaringClass();
    }

    @Override
    public Class getValueClass() {
        return hasCaster() ? caster.getInputType() : method.getParameterTypes()[0];
    }

    @Override
    public IProperty getProperty() {
        return property;
    }
}
