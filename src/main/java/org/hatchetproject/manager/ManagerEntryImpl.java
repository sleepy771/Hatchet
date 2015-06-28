package org.hatchetproject.manager;

import java.util.Map;

/**
 * Created by filip on 6/27/15.
 */
public class ManagerEntryImpl<KEY, ELEMENT> implements ManagerEntry<KEY, ELEMENT> {

    private KEY key;
    private ELEMENT element;
    private volatile int hashCode;

    public ManagerEntryImpl(KEY key, ELEMENT element) {
        this.element = element;
        this.key = key;
    }

    public ManagerEntryImpl(Map.Entry<KEY, ELEMENT> entry) {
        this(entry.getKey(), entry.getValue());
    }

    @Override
    public KEY getKey() {
        return key;
    }

    @Override
    public ELEMENT getValue() {
        return element;
    }

    @Override
    public ELEMENT setValue(ELEMENT value) {
        if (value == null)
            return null;
        ELEMENT tmp = this.element;
        this.element = value;
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.hashCode() != hashCode() || !ManagerEntry.class.isInstance(o))
            return false;
        ManagerEntry entry = (ManagerEntry) o;
        return entry.getValue().equals(getValue()) && entry.getKey().equals(getKey());
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            int hash = 17;
            hash = 31 * hash + key.hashCode();
            hash = 31 * hash + element.hashCode();
            hashCode = hash;
        }
        return hashCode;
    }
}
