package org.hatchetproject.reflection;

/**
 * Created by filip on 2.8.2015.
 */
public interface AccessorSignature extends Signature {
    int getParameterCount();

    Class[] getParameterTypes();

    Class getParameterType(int k);

    Class getReturnType();
}
