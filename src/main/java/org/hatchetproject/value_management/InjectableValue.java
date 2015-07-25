package org.hatchetproject.value_management;

import org.hatchetproject.value_management.RegistrableValue.ValueSignature;

/**
 * Created by filip on 7/14/15.
 */
public interface InjectableValue {
    ValueSignature getSignature();

    Object getValue();

    String getName();

    Class getType();
}
