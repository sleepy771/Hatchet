package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;

public interface Promise<TYPE> {
    TYPE get() throws PropertyGetterException, InterruptedException;

    boolean isComplete();
}
