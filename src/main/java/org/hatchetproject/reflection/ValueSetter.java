package org.hatchetproject.reflection;

import java.util.Map;

/**
 * Created by filip on 7/2/15.
 */
public interface ValueSetter {
    Map<String, Object> setValues(Scheme scheme, Object destination, Map<String, Object> values) throws Exception;

    Object createObject(Scheme scheme, Map<String, Object> values) throws Exception;

    Object initObject(Scheme scheme, Map<String, Object> values) throws Exception;
}
