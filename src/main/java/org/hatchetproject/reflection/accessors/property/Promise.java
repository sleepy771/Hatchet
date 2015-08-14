package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.ReadyListener;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyGetterHelper;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

public interface Promise<TYPE> {
    TYPE get() throws PropertyGetterException, InterruptedException;

    boolean isComplete();
}
