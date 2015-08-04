package org.hatchetproject.reflection.meta.signatures;

import org.hatchetproject.exceptions.HatchetSignatureException;

import java.lang.reflect.Field;

public class FieldMeta extends AccessorMetaBase<FieldMeta> {

    public FieldMeta(Field field) {
        this(field.getName(), field.getType(), field.getDeclaringClass());
    }

    public FieldMeta(String name, Class type, Class declaringClass) {
        super(name, type, new Class[] {type,}, declaringClass);
    }

    public Field getField() throws HatchetSignatureException {
        try {
            return getDeclaringClass().getField(getName());
        }catch (NoSuchFieldException e) {
            throw new HatchetSignatureException("Invalid signature, can not find field", e);
        }
    }

    @Override
    public MetaType getAccessorMetaType() {
        return MetaType.FIELD;
    }
}
