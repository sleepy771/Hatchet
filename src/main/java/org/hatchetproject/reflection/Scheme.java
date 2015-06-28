package org.hatchetproject.reflection;

import org.hatchetproject.value_management.ValueCast;

import java.util.Set;

/**
 * Created by filip on 6/28/15.
 */
public interface Scheme {
    boolean canAssign(Signature signature);

    boolean isValidType(Signature signature, Class value);

    boolean validateAssigned(Set<Signature> signatureSet);

    boolean canAssignEmpty(Signature signature);

    boolean needCast(Signature signature, Class valueType);

    boolean canCast(Signature signature, Class valueType);

    ValueCast getCast(Signature property, Class valueType);

    Set<Signature> getConstructionProperties();

    Set<Signature> getConstructorProperties();

    Set<Signature> getMethodOverrideProperties();

    Set<Signature> getSetterProperties();

    Set<Signature> getMethodSetterProperties();

    Set<Signature> getFieldProperties();
}