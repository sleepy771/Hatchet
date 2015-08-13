package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.ReadyListener;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyGetterHelper;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

public interface Promise<TYPE, RAW_TYPE> {
    TYPE get() throws PropertyGetterException, InterruptedException;

    RAW_TYPE getRaw() throws PropertyGetterException, InterruptedException;

    void setHelper(PropertyGetterHelper<TYPE, RAW_TYPE> helper);

    PropertyGetterHelper<TYPE, RAW_TYPE> getHelper();

    PropertyMeta getProperty();

    void addListener(ReadyListener<Promise> listener);

    void removeListener(ReadyListener<Promise> listener);
}
