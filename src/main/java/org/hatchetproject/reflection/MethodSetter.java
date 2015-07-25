package org.hatchetproject.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodSetter extends AbstractSetter {

    private Method method;

    public MethodSetter(Method method) {
        this.method = method;
    }

    @Override
    public final Class[] getTypes() {
        return method.getParameterTypes();
    }

    @Override
    protected final Object performSet(Object object, Object[] values) throws InvocationTargetException, IllegalAccessException {
        Object ret = method.invoke(object, values);
        if (method.getReturnType() == void.class) {
            return null;
        }
        return ret;
    }
}
