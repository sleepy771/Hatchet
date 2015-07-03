package org.hatchetproject.reflection;

import org.hatchetproject.manager.memory.Releasable;
import org.hatchetproject.value_management.ValueCast;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface Scheme extends Releasable, Classy {
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

    Map<Signature, IProperty> getPropertyMap();

    List<Class> getConstructorArguments();

    Map<Integer, Object> getDefaultConstructorValues();

    boolean hasDefaultConstructorValues();

    ValueGetter getGetter();

    ValueSetter getSetter();

    void setGetter(ValueGetter getter);

    void setSetter(ValueSetter setter);

    Map<String, Object> getValuesFrom(Object source);

    void setValuesTo(Object destination, Map<String, Object> values);

    Object createObject(Map<String, Object> values);
}
