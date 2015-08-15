package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.reflection.accessors.Setter;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyHelper;
import org.hatchetproject.reflection.accessors.property.helpers.PropertySetterHelper;

public class PropertySetterImpl implements PropertySetter {

    private Setter setter;

    private PropertySetterHelper helper;

    public PropertySetterImpl(Setter setter) {
        this.setter = setter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object destination, Object value) throws PropertySetterException {
        this.setter.setTarget(destination);
        if (null == helper) {
            setter.add(value); // TODO catch exception and throw PropertySetterException
            return;
        }
        this.helper.push(value); // TODO improve exception handling
    }

    @Override
    public void setHelper(PropertySetterHelper helper) {
        this.helper = helper;
        this.helper.setSetter(setter);
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
