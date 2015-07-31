package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.RegistrableValue.ValueSignature;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class MultiPropertySetter extends PropertyAccessorBase implements PropertySetter {

    private int index;
    private IProperty property;
    private Class valueClass;

    protected MultiPropertySetter(ValueCast caster, Class valueClass, IProperty property) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
        this.valueClass = valueClass;
        this.property = property;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        if (!List.class.isInstance(destination))
            throw new PropertySetterException("List is expected as destination");
        List destList = (List) destination;
        if (!getValueClass().isInstance(value))
            throw new PropertySetterException("Invalid property instance");
        try {
            destList.set(index, cast(value));
        } catch (PropertyAccessorException e) {
            throw new PropertySetterException("can not cast", e);
        }
    }

    @Override
    public Map<Integer, ValueSignature> getInjects() {
        return null;
    }

    @Override
    public Class getDeclaringClass() {
        return property.getDeclaringClass();
    }

    @Override
    public Class getValueClass() {
        return valueClass;
    }

    @Override
    public IProperty getProperty() {
        return property;
    }
}
