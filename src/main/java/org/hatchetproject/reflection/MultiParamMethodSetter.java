package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 7/3/15.
 */
public class MultiParamMethodSetter extends PropertyAccessorBase implements PropertySetter {

    private int index;
    private IProperty property;

    protected MultiParamMethodSetter(ValueCast caster) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        if (!List.class.isInstance(destination))
            throw new PropertySetterException("List is expected as destination");
        List destList = (List) destination;
        if (!getValueClass().isInstance(value))
            throw new PropertySetterException("Invalid property instance");
        if (hasCaster()) {
            value = getCaster().cast(value);
        }
        destList.set(index, value);
    }

    @Override
    public Class getDeclaringClass() {
        return property.getDeclaringClass();
    }

    @Override
    public Class getValueClass() {
        return hasCaster() ? getCaster().getInputType() : property.getPropertyType();
    }

    @Override
    public IProperty getProperty() {
        return property;
    }
}
