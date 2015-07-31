package org.hatchetproject;

import java.util.Map;

/**
 * Created by filip on 7/31/15.
 */
public class TypeValueEntry implements Map.Entry<Class, Object> {

    private final Class type;
    private Object value;

    public TypeValueEntry(Class key, Object value) {
        if (null != value && key.isInstance(value)) {
            throw new ClassCastException("Invalid value type");
        }
        this.type = key;
        this.value = value;
    }

    public TypeValueEntry(Map.Entry<Class, Object> valueEntry) {
        this(valueEntry.getKey(), valueEntry.getValue());
    }

    public TypeValueEntry(Class key) {
        this(key, null);
    }

    @Override
    public Class getKey() {
        return this.type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object setValue(Object value) {
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Invalid Value type");
        }
        Object oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
