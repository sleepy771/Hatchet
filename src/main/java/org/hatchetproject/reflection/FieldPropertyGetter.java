package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.Field;

public class FieldPropertyGetter extends PropertyAccessorBase implements PropertyGetter {

    private Field field;
    private ValueCast caster;

    FieldPropertyGetter(Field field, ValueCast cast) throws PropertyAccessorException {
        super(cast, AccessorType.GETTER);
        if (field == null)
            throw new NullPointerException("Field can not be null");
        this.field = field;
    }

    FieldPropertyGetter(Field field) throws PropertyAccessorException {
        this(field, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object get(Object source) throws PropertyGetterException {
        Object output = getRaw(source);
        if (getCaster() != null) {
            output = getCaster().cast(output);
        }
        return output;
    }

    @Override
    public Object getRaw(Object source) throws PropertyGetterException {
        if (!getDeclaringClass().isInstance(source))
            throw new PropertyGetterException("Invalid instance");
        try {
            return field.get(source);
        } catch (IllegalAccessException e) {
            throw new PropertyGetterException("Unacessible property", e);
        }
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
    public IProperty getProperty() {
        return null;
    }
}
