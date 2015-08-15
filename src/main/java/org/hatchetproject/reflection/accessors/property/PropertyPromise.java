package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.ReadyListener;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyHelper;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

/**
 * Created by filip on 14.8.2015.
 */
public interface PropertyPromise<TYPE, RAW_TYPE> extends Promise<TYPE> {
    RAW_TYPE getRaw() throws PropertyGetterException, InterruptedException;

    void setHelper(PropertyHelper<RAW_TYPE, TYPE> helper);

    PropertyHelper<RAW_TYPE, TYPE> getHelper();

    PropertyMeta getProperty();

    void addListener(ReadyListener<Promise> listener);

    void removeListener(ReadyListener<Promise> listener);
}
