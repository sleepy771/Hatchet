package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.Field;

public class FieldPropertyGetter implements PropertyGetter {

    private Field field;
    private ValueCast caster;

    FieldPropertyGetter(Field field, ValueCast cast) {
        if (field == null)
            throw new NullPointerException("Field can not be null");
        this.field = field;
        this.caster = cast;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object get(Object source) throws PropertyGetterException {
        if (!getDeclaringClass().isInstance(source))
            throw new PropertyGetterException("Invalid instance");
        Object output = null;
        try {
            output = field.get(source);
        } catch (IllegalAccessException e) {
            throw new PropertyGetterException("Unacessible property", e);
        }
        if (getCaster() != null) {
            output = getCaster().cast(output);
        }
        return output;
    }

    @Override
    public Class getDeclaringClass() {
        return field.getDeclaringClass();
    }

    @Override
    public Class getValueClass() {
        return field.getType();
    }

    @Override
    public ValueCast getCaster() {
        return caster;
    }
}
