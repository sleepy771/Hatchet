package org.hatchetproject.value_management.inject_default;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValueInjectionMap {

    private static ValueInjectionMap INSTANCE;

    private List<MapValueGenerator> enhancerList = new LinkedList<>();
    private Map<String, Object> valueMap = new HashMap<>();
    private boolean overrideExisting = true;

    private void executeGenerators() {
        for (MapValueGenerator generator : enhancerList) {
            Map<String, Object> values = new HashMap<>();
            generator.populate(values);
            if (overrideExisting)
                valueMap.putAll(values);
            else {
                Set<String> nonColliding = values.keySet();
                nonColliding.removeAll(valueMap.keySet());
                for (String key : nonColliding) {
                    valueMap.put(key, values.get(key));
                }
            }
        }
    }

    public void setOverride(boolean override) {
        this.overrideExisting = override;
    }

    public boolean getOverride() {
        return this.overrideExisting;
    }

    public void register(MapValueGenerator generator) {
        this.enhancerList.add(generator);
    }

    public void unregister(MapValueGenerator generator) {
        this.enhancerList.remove(generator);
    }

    public void clearValues() {
        valueMap.clear();
    }

    public void clearGenerators() {
        enhancerList.clear();
    }

    public Object getValue(String uid) {
        return valueMap.get(uid);
    }

    public static ValueInjectionMap getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValueInjectionMap();
        }
        return INSTANCE;
    }
}
