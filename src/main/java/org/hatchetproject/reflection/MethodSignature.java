package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.HatchetSignatureException;

import java.lang.reflect.Method;

public class MethodSignature extends SignatureBase<MethodSignature> implements Signature {

    public MethodSignature(Method method) {
        this(method.getName(), method.getReturnType(), method.getParameterTypes(), method.getDeclaringClass());
    }

    public MethodSignature(String name, Class type, Class[] inputTypes, Class declaringClass) {
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
}
