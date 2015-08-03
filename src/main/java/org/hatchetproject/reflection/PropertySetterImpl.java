package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.reflection.accessors.Setter;
import org.hatchetproject.value_management.RegistrableValue.ValueSignature;
import org.hatchetproject.value_management.ValueCast;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.util.Map;

/**
 * Created by filip on 2.8.2015.
 */
public class PropertySetterImpl extends PropertyAccessorBase implements PropertySetter {

    private final Setter setter;

    PropertySetterImpl(Setter setter, ValueCast caster) throws PropertyAccessorException {
        super(caster, AccessorType.SETTER);
        if (null == setter) {
            throw new NullPointerException("setter can not be null");
        }
        this.setter = setter;
    }

    @Override
    public void set(Object destination, Object value) throws PropertySetterException{
        try {
            if (1 == setter.getParametersCount()) {
                setter.set(destination, new Object[]{value});
            } else {
                ParametersBuilder parametersBuilder = ParametersBuilder.createMethodParametersBuilder(
                        Setter.class.getMethod("set", Object.class, Object[].class));
                parametersBuilder.add(value);
            }
        } catch (Exception e) {
            throw new PropertySetterException("Something went wrong", e);
        }
    }

    @Override
    public Map<Integer, ValueSignature> getInjects() {
        return null;
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
    public PropertyMeta getProperty() {
        return null;
    }
}
