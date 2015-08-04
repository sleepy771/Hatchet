package org.hatchetproject.value_management;

import org.hatchetproject.reflection.meta.signatures.Classy;

public abstract class HatchetParser<Type> implements Classy {

    private final Class<Type> declaringClass;

    public HatchetParser(Class<Type> declaringClass) {
        this.declaringClass = declaringClass;
    }

    @Override
    public Class getDeclaringClass() {
        return declaringClass;
    }

    public abstract Type parse(String value);

    public abstract String toString(Type value);
}
