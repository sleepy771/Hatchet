package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.value_management.ValueCast;

/**
 * Created by filip on 7/3/15.
 */
public class CGMethodSetter extends PropertyAccessorBase implements PropertySetter {

    protected CGMethodSetter(ValueCast caster) throws PropertyAccessorException {
        super(caster, AccessorType.GETTER);
    }

    @Override
    public void set(Object destination, Object value) throws PropertySetterException {

    }

    @Override
    public Class getDeclaringClass() {
        return null;
    }

    @Override
    public Class getValueClass() {
        return null;
    }

    @Override
    public IProperty getProperty() {
        return null;
    }
}
