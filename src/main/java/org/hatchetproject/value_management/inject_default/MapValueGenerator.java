package org.hatchetproject.value_management.inject_default;

import java.util.Map;

/**
 * Created by filip on 7/2/15.
 */
public interface MapValueGenerator {
    void populate(Map<String, Object> valuesMap);
}
