package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.reflection.accessors.Setter;
import org.hatchetproject.reflection.accessors.property.helpers.PropertySetterHelper;

public class PropertySetterImpl implements PropertySetter {

    private final Setter setter;

    private PropertySetterHelper helper;

    public PropertySetterImpl(Setter setter, PropertySetterHelper helper) {
        this.setter = setter;
        setHelper(helper);
    }

    public PropertySetterImpl(Setter setter) {
        this(setter, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        try {
            this.setter.setTarget(destination);
            if (null == helper) {
                setter.add(value);
                return;
            }
            this.helper.push(value);
        } catch (ClassCastException cce) {
            throw new PropertySetterException("Can not set propery value" + value.toString() + " to destination " + destination.toString(), cce);
        }
    }

    @Override
    public void setHelper(PropertySetterHelper helper) {
        this.helper = helper;
        if (null != helper) {
            this.helper.setSetter(setter);
        }
    }

    @Override
    public PropertySetterHelper getHelper() {
        return helper;
    }

    @Override
    public Setter getSetter() {
        return setter;
    }
}
