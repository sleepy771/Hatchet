package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class ConstructorSetter extends AbstractConstructorSetter {

    public ConstructorSetter(@NotNull Constructor constructor) {
        super(constructor);

    }

    @Override
    protected boolean isReady() {
        return isFilled();
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            return constructor.newInstance(values);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new InvocationException("Can not create object", e);
        }
    }
}
