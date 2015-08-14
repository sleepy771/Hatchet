package org.hatchetproject.reflection;

import org.hatchetproject.manager.memory.Releasable;
import org.hatchetproject.reflection.accessors.CGConstructorSetter;
import org.hatchetproject.reflection.accessors.AbstractConstructorSetter;
import org.hatchetproject.reflection.accessors.property.PropertyGetter;
import org.hatchetproject.reflection.accessors.property.PropertySetter;
import org.hatchetproject.reflection.meta.signatures.Classy;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

import java.util.Set;


public interface Scheme extends Releasable, Classy {
    boolean canAssign(PropertyMeta meta);

    boolean isValid(PropertyMeta meta, Object type);

    Set<PropertyGetter> getGetters();

    Set<PropertySetter> getSetters();

    Set<PropertyMeta> getProperties();

    PropertyGetter getGetter(PropertyMeta meta);

    PropertySetter getSetter(PropertyMeta meta);

    Set<PropertyMeta> getConstructionProperties();

    Set<PropertyMeta> getAssignableProperties();

    AbstractConstructorSetter getConstructorSetter();

    boolean isAllSet(Set<PropertyMeta> metas);
}
