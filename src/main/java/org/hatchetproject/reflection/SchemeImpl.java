package org.hatchetproject.reflection;

import org.hatchetproject.Builder;
import org.hatchetproject.exceptions.BuilderException;
import org.hatchetproject.reflection.accessors.AbstractConstructorSetter;
import org.hatchetproject.reflection.accessors.property.PropertyGetter;
import org.hatchetproject.reflection.accessors.property.PropertySetter;
import org.hatchetproject.reflection.accessors.property.helpers.PropertySetterHelper;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SchemeImpl implements Scheme {

    private final Map<PropertyMeta, PropertyGetter> getters;

    private final Map<PropertyMeta, PropertySetter> setters;

    private final Set<PropertyMeta> constructionProperties;

    private final Set<PropertyMeta> assignableProperties;

    private final Class declaringClass;

    private final AbstractConstructorSetter constructorSetter;

    private SchemeImpl(Class declaringClass, Map<PropertyMeta, PropertyGetter> getters, Map<PropertyMeta, PropertySetter> setters, Set<PropertyMeta> constructionProperties, Set<PropertyMeta> assignableProperties, AbstractConstructorSetter setter) {
        this.setters = Collections.unmodifiableMap(setters);
        this.getters = Collections.unmodifiableMap(getters);
        this.constructionProperties = Collections.unmodifiableSet(constructionProperties);
        this.assignableProperties = Collections.unmodifiableSet(assignableProperties);
        this.declaringClass = declaringClass;
        this.constructorSetter = setter;
    }

    @Override
    public boolean canAssign(PropertyMeta meta) {
        return setters.containsKey(meta);
    }

    @Override
    public boolean isValid(PropertyMeta meta, Object type) {
        if (!canAssign(meta)) {
            return false;
        }
        if (meta.getType().isInstance(type)) {
            return true;
        }
        PropertySetterHelper helper = setters.get(meta).getHelper();
        return null != helper && helper.canAssignType(type.getClass());
    }

    @Override
    public Set<PropertyGetter> getGetters() {
        return Collections.unmodifiableSet(new HashSet<>(getters.values()));
    }

    @Override
    public Set<PropertySetter> getSetters() {
        return Collections.unmodifiableSet(new HashSet<>(setters.values()));
    }

    @Override
    public Set<PropertyMeta> getProperties() {
        return Collections.unmodifiableSet(getters.keySet());
    }

    @Override
    public PropertyGetter getGetter(PropertyMeta meta) {
        return getters.get(meta);
    }

    @Override
    public PropertySetter getSetter(PropertyMeta meta) {
        return setters.get(meta);
    }

    @Override
    public Set<PropertyMeta> getConstructionProperties() {
        return constructionProperties;
    }

    @Override
    public Set<PropertyMeta> getAssignableProperties() {
        return assignableProperties;
    }

    @Override
    public AbstractConstructorSetter getConstructorSetter() {
        return constructorSetter;
    }

    @Override
    public boolean isAllSet(Set<PropertyMeta> metas) {
        return metas.containsAll(getters.keySet());
    }

    @Override
    public Class getDeclaringClass() {
        return declaringClass;
    }

    public static class SchemeBuilder implements Builder<Scheme> {

        private Class declaringClass;

        public SchemeBuilder(Class declaringClass) {
            this.declaringClass = declaringClass;
        }

        @Override
        public Scheme build() throws BuilderException {
            return null;
        }
    }
}
