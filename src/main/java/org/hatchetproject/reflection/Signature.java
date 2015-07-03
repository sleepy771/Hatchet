package org.hatchetproject.reflection;

/**
 * Created by filip on 6/28/15.
 */
public interface Signature {
    String getName();
    Class getReturnType();
    Class[] getParameterTypes();
    Class getParameterType(int idx);
    int getParametersCount();
    Class getDeclaringClass();
}
