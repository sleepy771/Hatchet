package org.hatchetproject.reflection;

import org.hatchetproject.utils.HatchetCollectionsManipulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class PropertyMap {
    private final Scheme scheme;
    private final Map<Signature, Object> values;
    private Map<Signature, Object> fieldMap;
    private Map<Signature, Object> setterMap;
    private Map<Signature, Object> constructorMap;
    private Map<Signature, Object> methodOverrideMap;
    private boolean locked;
    private boolean complete = false;

    public PropertyMap(Scheme scheme) {
        this(scheme, new HashMap<>());
    }

    public PropertyMap(Scheme scheme, Map<Signature, Object> values) {
        this.scheme = scheme;
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public void set(Signature signature, Object value) {
        if (isChangeLocked())
            throw new IllegalArgumentException();
        if (!scheme.canAssign(signature)) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            if (!scheme.canAssignEmpty(signature)) {
                throw new IllegalArgumentException();
            }
        } else {
            if (!scheme.isValidType(signature, value.getClass())) {
                throw new IllegalArgumentException();
            }
            Class valueType = value.getClass();
            if (scheme.needCast(signature, valueType) && scheme.canCast(signature, valueType)) {
                value = scheme.getCast(signature, valueType).cast(value);
            }
        }
        values.put(signature, value);
        setComplete(false);
    }

    public void add(Signature signature, Object value) {
        if (values.containsKey(signature))
            throw new IllegalArgumentException();
        set(signature, value);
    }

    public boolean isComplete() {
        return (complete && isChangeLocked()) || isSchemeValid();
    }

    public boolean isSchemeValid() {
        boolean result = scheme.validateAssigned(values.keySet());
        setComplete(result);
        if (result) {
            lockChanges();
        }
        return result;
    }

    public boolean remove(Signature signature, Object value) {
        if (isChangeLocked()) {
            throw new IllegalArgumentException();
        }
        setComplete(false);
        return values.remove(signature, value);
    }

    public Object remove(Signature signature) {
        if (isChangeLocked())
            throw new IllegalArgumentException();
        setComplete(false);
        return values.remove(signature);
    }

    public void clear() {
        if (isChangeLocked()) {
            throw new IllegalArgumentException();
        }
        values.clear();
    }

    public Set<Signature> getAssigned() {
        return values.keySet();
    }

    public boolean contains(Signature signature) {
        return values.containsKey(signature);
    }

    public boolean contains(Signature signature, Object value) {
        Object assigned = values.get(signature);
        return values.containsKey(signature) && (value == assigned || value.equals(assigned));
    }

    private void validate() {
        if (isComplete())
            return;
        throw new IllegalArgumentException();
    }

    private void dropAllMaps() {
        fieldMap.clear();
        constructorMap.clear();
        setterMap.clear();
        methodOverrideMap.clear();
        fieldMap = null;
        constructorMap = null;
        setterMap = null;
        methodOverrideMap = null;
    }

    public Set<Signature> getConstructionSignatureSet() {
        return scheme.getConstructionProperties();
    }

    public boolean needsConstructionValues() {
        return !getConstructionSignatureSet().isEmpty();
    }

    public Map<Signature, Object> getPreConstructionValues() {
        return HatchetCollectionsManipulation.merge(getConstructorValues(), getOverrideValues());
    }

    private Map<Signature, Object> populateMap(Map<Signature, Object> values, Set<Signature> populateWith) {
        if (HatchetCollectionsManipulation.isEmpty(values)) {
            if (HatchetCollectionsManipulation.isEmpty(populateWith))
                return new HashMap<>();
            values = values == null ? new HashMap<>() : values;
            for (Signature property : populateWith) {
                if (values.containsKey(property))
                    continue;
                values.put(property, this.values.get(property));
            }
        }
        return values;
    }

    public Map<Signature, Object> getOverrideValues() {
        validate();
        return populateMap(methodOverrideMap, scheme.getMethodOverrideProperties());
    }

    public Map<Signature, Object> getConstructorValues() {
        validate();
        return populateMap(constructorMap, scheme.getConstructorProperties());
    }

    public Map<Signature, Object> getSetterValues() {
        return HatchetCollectionsManipulation.merge(getMethodSetterValues(), getFieldValues());
    }

    public Map<Signature, Object> getFieldValues() {
        validate();
        return populateMap(fieldMap, scheme.getFieldProperties());
    }

    public Map<Signature, Object> getMethodSetterValues() {
        validate();
        return populateMap(setterMap, scheme.getMethodSetterProperties());
    }

    public Set<Signature> getSetterSignatureSet() {
        return scheme.getSetterProperties();
    }

    private void lockChanges() {
        this.locked = true;
    }

    private void setComplete(boolean isComplete) {
        this.complete = isComplete;
    }

    public boolean isChangeLocked() {
        return this.locked;
    }

    public void unlockChanges() {
        this.locked = false;
    }
}
