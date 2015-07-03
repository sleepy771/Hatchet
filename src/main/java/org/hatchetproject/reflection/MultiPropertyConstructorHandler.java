package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertySetterException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MultiPropertyConstructorHandler<T> extends MultiPropertySetterBase implements ObjectBuilder<T> {

    private Constructor constructor;

    protected MultiPropertyConstructorHandler(Constructor constructor) {
        super(constructor.getParameterCount());
        this.constructor = constructor;
    }

    @Override
    protected Class getParameterType(int idx) {
        return constructor.getParameterTypes()[idx];
    }

    @Override
    public Object setAll(Object destination, Map<Signature, Object> values) throws PropertySetterException {
        try {
            return constructor.newInstance(createListOfArgs(values));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new PropertySetterException("Can not instantiate object", e);
        }
    }

    @Override
    public ObjectBuilder<T> setConstructorValue(int idx, Object value) {
        return null;
    }

    @Override
    public ObjectBuilder<T> setConstructorValues(Object[] values) {
        return null;
    }

    @Override
    public ObjectBuilder<T> clear() {
        return null;
    }

    @Override
    public T build() {
        return null;
    }
}
