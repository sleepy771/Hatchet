package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.reflection.accessors.Setter;
import org.hatchetproject.value_management.ValueCast;

public abstract class CastablePropertySetterHelper<TYPE, PROPERTY_TYPE> implements PropertySetterHelper<PROPERTY_TYPE> {

    private final ValueCast<PROPERTY_TYPE, TYPE> valueCast;

    public CastablePropertySetterHelper(ValueCast<PROPERTY_TYPE, TYPE> valueCast) {
        this.valueCast = valueCast;
    }

    @Override
    public final void assign(Setter setter, PROPERTY_TYPE insert) {
        performAssign(setter, valueCast.cast(insert));
    }

    protected abstract void performAssign(Setter setter, TYPE property);
}
