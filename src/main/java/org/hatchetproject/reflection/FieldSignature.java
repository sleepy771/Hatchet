package org.hatchetproject.reflection;

import java.lang.reflect.Field;

public class FieldSignature extends SignatureBase<FieldSignature> implements Signature {

    public FieldSignature(Field field) {
        this(field.getName(), field.getType(), field.getDeclaringClass());
    }

    public FieldSignature(String name, Class type, Class declaringClass) {
        super(name, type, declaringClass);
    }

    @Override
    protected String propertyTypeName() {
        return "Field";
    }
}
