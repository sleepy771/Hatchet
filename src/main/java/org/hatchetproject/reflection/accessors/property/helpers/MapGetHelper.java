package org.hatchetproject.reflection.accessors.property.helpers;

import java.util.Map;

public class MapGetHelper implements PropertyHelper<Map, Object> {

    private final Object key;

    public MapGetHelper(Object key) {
        this.key = key;
    }

    @Override
    public Object push(Map map) {
        return map.get(key);
    }
}
