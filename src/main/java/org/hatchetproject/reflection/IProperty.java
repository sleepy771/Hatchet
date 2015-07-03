package org.hatchetproject.reflection;

/**
 * Created by filip on 6/27/15.
 */
public interface IProperty {
    String getName();

    Class getDeclaringClass();

    Class getPropertyType();

    Signature getSignature();
}
