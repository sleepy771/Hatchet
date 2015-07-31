package org.hatchetproject.reflection;

import org.hatchetproject.value_management.ValueCast;

/**
 * Created by filip on 7/2/15.
 */
public class DefaultAccessor implements PropertyAccessor {


    private IProperty property;
    private Class valueClazz;
    private ValueCast caster;

    private DefaultAccessor(IProperty property, Class valueClazz, ValueCast caster) {
        this.caster = caster;
        this.valueClazz = valueClazz;
        this.property = property;
    }

    @Override
    public Class getDeclaringClass() {
        return property.getDeclaringClass();
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
    public IProperty getProperty() {
        return null;
    }
}
