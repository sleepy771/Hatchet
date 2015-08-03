package org.hatchetproject.reflection;

import java.lang.reflect.Field;

public class FieldSignature extends SignatureBase<FieldSignature> {

    public FieldSignature(Field field) {
        this(field.getName(), field.getType(), field.getDeclaringClass());
    }

    public FieldSignature(String name, Class type, Class declaringClass) {
        super(name, type, new Class[] {type,}, declaringClass);
    }

    @Override
    public Class getType() {
        return getReturnType();
    }

    @Override
    protected String propertyTypeName() {
        return "Field";
    }
}
