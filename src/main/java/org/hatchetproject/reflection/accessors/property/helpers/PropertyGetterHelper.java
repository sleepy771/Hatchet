package org.hatchetproject.reflection.accessors.property.helpers;

public interface PropertyGetterHelper<PROPERTY_TYPE, OUTPUT_TYPE> {
    PROPERTY_TYPE extract(OUTPUT_TYPE output);
}
