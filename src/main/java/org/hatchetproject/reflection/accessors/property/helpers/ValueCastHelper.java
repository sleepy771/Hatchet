package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.value_management.ValueCast;

public class ValueCastHelper implements PropertyHelper {
    private final ValueCast cast;

    private final PropertyHelper parentHelper;

    public ValueCastHelper(ValueCast cast, PropertyHelper parentHelper) {
        this.cast = cast;
        this.parentHelper = parentHelper;
    }

    public ValueCastHelper(ValueCast cast) {
        this(cast, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object push(Object o) {
        return cast.cast(null != parentHelper ? parentHelper.push(o) : o);
    }
}
