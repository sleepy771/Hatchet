package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.ReadyListener;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyHelper;

public interface PropertyPromise<TYPE, RAW_TYPE> extends Promise<TYPE> {
    RAW_TYPE getRaw() throws PropertyGetterException, InterruptedException;

    PropertyHelper<RAW_TYPE, TYPE> getHelper();

    void addListener(ReadyListener<PropertyPromise> listener);

    void removeListener(ReadyListener<PropertyPromise> listener);
}
