package org.hatchetproject.reflection.accessors.property.helpers;

import java.util.Map;

public class MapPutHelper implements PropertyHelper<Object, Void> {

    private Map map;

    private final Object key;

    public MapPutHelper(Object key) {
        this.key = key;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Void push(Object o) {
        this.map.put(key, o);
        return null;
    }
}
