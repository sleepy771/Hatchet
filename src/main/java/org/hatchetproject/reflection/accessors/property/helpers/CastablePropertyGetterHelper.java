package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.value_management.ValueCast;

public abstract class CastablePropertyGetterHelper<PROPERTY_TYPE, PROPERTY_OUTPUT_TYPE, OUTPUT_TYPE> implements PropertyGetterHelper<PROPERTY_TYPE, OUTPUT_TYPE> {

    private final ValueCast<PROPERTY_OUTPUT_TYPE, PROPERTY_TYPE> valueCast;

    public CastablePropertyGetterHelper(ValueCast<PROPERTY_OUTPUT_TYPE, PROPERTY_TYPE> cast) {
        this.valueCast = cast;
    }

    public final ValueCast<PROPERTY_OUTPUT_TYPE, PROPERTY_TYPE> getValueCast() {
        return this.valueCast;
    }

    @Override
    public final PROPERTY_TYPE extract(OUTPUT_TYPE output) {
        return this.valueCast.cast(performExtraction(output));
    }

    protected abstract PROPERTY_OUTPUT_TYPE performExtraction(OUTPUT_TYPE type);
}
