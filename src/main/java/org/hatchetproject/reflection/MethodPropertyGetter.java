package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodPropertyGetter extends PropertyAccessorBase implements PropertyGetter {

    public Method getterMethod;

    MethodPropertyGetter(Method method, ValueCast caster) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
        this.getterMethod = method;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object get(Object source) throws PropertyGetterException {
        Object output = getRaw(source);
        if (hasCaster()) {
            output = getCaster().cast(output);
        }
        return output;
    }

    @Override
    public Object getRaw(Object source) throws PropertyGetterException {
        if (!getDeclaringClass().isInstance(source))
            throw new PropertyGetterException("Source is not instance of " + getDeclaringClass().getName());
        try {
            return getterMethod.invoke(source);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new PropertyGetterException("Can not obtain value", e);
        }
    }

    @Override
    public Class getDeclaringClass() {
        return getterMethod.getDeclaringClass();
    }

    @Override
    public Class getValueClass() {
        return getterMethod.getReturnType();
    }

    @Override
    public IProperty getProperty() {
        return null;
    }
}
