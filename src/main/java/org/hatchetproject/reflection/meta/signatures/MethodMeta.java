package org.hatchetproject.reflection.meta.signatures;

import org.hatchetproject.exceptions.HatchetSignatureException;

import java.lang.reflect.Method;

public class MethodMeta extends AccessorMetaBase<MethodMeta> {

    public MethodMeta(Method method) {
        this(method.getName(), method.getReturnType(), method.getParameterTypes(), method.getDeclaringClass());
    }

    public MethodMeta(String name, Class type, Class[] inputTypes, Class declaringClass) {
        super(name, type, inputTypes, declaringClass);
    }

    @SuppressWarnings("unchecked")
    public Method getMethod() throws HatchetSignatureException {
        try {
            return getDeclaringClass().getMethod(getName(), getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new HatchetSignatureException("Invalid signature, can not find method", e);
        }
    }

    @Override
    public MetaType getAccessorMetaType() {
        return MetaType.METHOD;
    }
}
