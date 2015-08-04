package org.hatchetproject.reflection.meta.signatures;

import org.hatchetproject.exceptions.HatchetSignatureException;

import java.lang.reflect.Constructor;

public class ConstructorMeta extends AccessorMetaBase<ConstructorMeta> {

    public ConstructorMeta(Constructor constructor) {
        this(constructor.getParameterTypes(), constructor.getDeclaringClass());
    }

    public ConstructorMeta(Class[] paramTypes, Class declaringClass) {
        super("CONSTRUCTOR", declaringClass, paramTypes, declaringClass);
    }

    @SuppressWarnings("unchecked")
    public Constructor getConstructor() throws HatchetSignatureException {
        try {
            return getDeclaringClass().getConstructor(getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new HatchetSignatureException("Invalid signature, can not find constructor", e);
        }
    }

    @Override
    public MetaType getAccessorMetaType() {
        return MetaType.CONSTRUCTOR;
    }
}
