package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertySetterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class MultiPropertyMethodHandler extends MultiPropertySetterBase {
    private Method method;

    MultiPropertyMethodHandler(Method method) {
        super(method.getParameterCount());
        this.method = method;
    }

    @Override
    protected Class getParameterType(int idx) {
        return method.getParameterTypes()[idx];
    }

    @Override
    public Object setAll(Object destination, Map<Signature, Object> values) throws PropertySetterException {
        try {
            method.invoke(destination, this.createListOfArgs(values));
            return destination;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new PropertySetterException("Can not access method", e);
        }
    }
}
