package org.hatchetproject.reflection;

import org.hatchetproject.Builder;
import org.hatchetproject.exceptions.BuilderException;
import org.hatchetproject.reflection.accessors.property.Promise;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectBuilder implements Builder<Object>, Map<PropertyMeta, Object> {

    private Scheme scheme;

    private Map<PropertyMeta, Object> values;

    public final void setScheme(Scheme scheme) {
        values.clear();
        this.scheme = scheme;
    }

    public final Scheme getScheme() {
        return this.scheme;
    }

    public final boolean isComplete() {
        return scheme.isAllSet(values.keySet());
    }

    @Override
    public final Object build() throws Exception {
        if (!isComplete()) {
            throw new BuilderException("Can not build object, yet. Missing properties: " + missing());
        }
        Promise promise = scheme.getConstructorSetter().getPromise();
        for (PropertyMeta constructionProperty : scheme.getConstructionProperties()) {
            scheme.getSetter(constructionProperty).set(null, values.get(constructionProperty));
        }
        Object filledInstance = promise.get();
        for (PropertyMeta assignProperty : scheme.getAssignableProperties()) {
            scheme.getSetter(assignProperty).set(filledInstance, values.get(assignProperty));
        }
        return filledInstance;
    }

    private String missing() {
        return null;
    }

    @Override
    public final int size() {
        return values.size();
    }

    @Override
    public final boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public final boolean containsKey(Object key) {
        return values.containsKey(key);
    }

    @Override
    public final boolean containsValue(Object value) {
        return values.containsValue(value);
    }

    @Override
    public final Object get(Object key) {
        return values.get(key);
    }

    @Override
    public final Object put(PropertyMeta key, Object value) {
        if (!scheme.isValid(key, value.getClass())) {
            throw new ClassCastException();
        }
        return values.put(key, value);
    }

    @Override
    public final Object remove(Object key) {
        return values.remove(key);
    }

    @Override
    public final void putAll(Map<? extends PropertyMeta, ?> m) {
        for (Entry<? extends PropertyMeta, ?> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public final void clear() {
        values.clear();
    }

    @Override
    public final Set<PropertyMeta> keySet() {
        return values.keySet();
    }

    @Override
    public final Collection<Object> values() {
        return values.values();
    }

    @Override
    public final Set<Entry<PropertyMeta, Object>> entrySet() {
        return new HashSet<>(values.entrySet());
    }
}
