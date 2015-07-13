package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.ValueCast;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.List;


public class ConstructorPropertySetter extends PropertyAccessorBase implements PropertySetter {

    private final int index;
    private final Class declaringClass;
    private WeakReference<IProperty> propertyWeakReference;


    ConstructorPropertySetter(IProperty constructorProperty, Constructor constructor, ValueCast caster, int index) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
        this.index = index;
        declaringClass = constructor.getDeclaringClass();
        propertyWeakReference = new WeakReference<>(constructorProperty);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        if (!(destination instanceof List)) {
            throw new PropertySetterException("List destination expected, but " + destination.getClass().getName() + " was given.");
        }
        List<Object> valuesArray = (List<Object>) destination;
        try {
            valuesArray.set(getIndex(), cast(value));
        } catch (PropertyAccessorException e) {
            throw new PropertySetterException("Unsucessfull cast", e);
        }
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Class getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public Class getValueClass() {
        return null;
    }

    @Override
    public IProperty getProperty() {
        return propertyWeakReference.get();
    }
}
