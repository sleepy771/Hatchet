package org.hatchetproject.reflection;

import java.util.Map;

/**
 * Created by filip on 7/4/15.
 */
public interface ObjectBuilderWithMethodInterception<T> extends ObjectBuilder<T> {
    ObjectBuilderWithMethodInterception<T> putValue(MethodSignature valueSignature, Object value);
    ObjectBuilderWithMethodInterception<T> putValues(Map<MethodSignature, Object> values);
}
