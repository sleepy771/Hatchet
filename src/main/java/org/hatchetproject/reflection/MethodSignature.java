package org.hatchetproject.reflection;

import java.lang.reflect.Method;

public class MethodSignature extends SignatureBase<MethodSignature> implements Signature {

    public MethodSignature(Method method) {
        this(method.getName(), method.getReturnType(), method.getParameterTypes(), method.getDeclaringClass());
    }

    public MethodSignature(String name, Class type, Class[] inputTypes, Class declaringClass) {
        super(name, type, inputTypes, declaringClass);
    }
}
