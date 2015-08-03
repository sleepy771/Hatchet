package org.hatchetproject.reflection;

import org.hatchetproject.value_management.ValueCast;

public class DefaultAccessor implements PropertyAccessor {


    private PropertyMeta property;
    private Class valueClazz;
    private ValueCast caster;

    private DefaultAccessor(PropertyMeta property, Class valueClazz, ValueCast caster) {
        this.caster = caster;
        this.valueClazz = valueClazz;
        this.property = property;
    }

    @Override
    public Class getValueClass() {
        return valueClazz;
    }

    @Override
    public ValueCast getCaster() {
        return caster;
    }

    @Override
    public boolean hasCaster() {
        return false;
    }

    @Override
    public boolean needsCaster() {
        return false;
    }

    @Override
    public PropertyMeta getProperty() {
        return null;
    }

    @Override
    public PropertyGetter getPropertyGetter() {
        return null;
    }

    @Override
    public PropertySetter getPropertySetter() {
        return null;
    }
}
