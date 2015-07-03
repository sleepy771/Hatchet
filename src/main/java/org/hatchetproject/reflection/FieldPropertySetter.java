package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.Field;

public class FieldPropertySetter extends PropertyAccessorBase implements PropertySetter {

    private Field field;
    private ValueCast caster;

    FieldPropertySetter(Field field, ValueCast caster) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
        if (field == null)
            throw new NullPointerException("Field can not be null");
        this.field = field;
        this.caster = caster;
    }

    public FieldPropertySetter(Field field) throws PropertyAccessorException {
        this(field, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        if ((getCaster() != null && !getCaster().getInputType().isInstance(value)) || (!getValueClass().isInstance(value))) {
                throw new PropertySetterException("Unassignable type");
        }
        try {
            field.set(destination, caster != null ? caster.cast(value) : value);
        } catch (IllegalAccessException e) {
            throw new PropertySetterException("Invalid access rights", e);
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
