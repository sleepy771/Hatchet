package org.hatchetproject.reflection.accessors;

import org.hatchetproject.exceptions.InvocationException;

public interface ImmediatelyInvokable {
    Object invoke(Object object, Object[] values) throws InvocationException;
}
