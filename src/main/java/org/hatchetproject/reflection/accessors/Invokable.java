package org.hatchetproject.reflection.accessors;

import org.hatchetproject.exceptions.InvocationException;

/**
 * Created by filip on 3.8.2015.
 */
public interface Invokable extends ImmediatelyInvokable {
    Object invoke(Object destination) throws InvocationException;

    boolean canInvoke();
}
