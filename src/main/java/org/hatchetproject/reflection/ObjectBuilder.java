package org.hatchetproject.reflection;

import org.hatchetproject.Builder;
import org.hatchetproject.value_management.InjectableValue;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by filip on 7/4/15.
 */
public interface ObjectBuilder<T> extends Builder<T> {
    void addPropertyValue(PropertySignature signature, Object value);

    void addPropertyValues(Map<PropertySignature, Object> values);

    boolean isAllPropertiesSet();

    boolean isAllInjectsSet();

    boolean isValid();

    boolean isReadyToBuild();

    boolean isInstanceProperty(PropertySignature signature);

    boolean isValidValue(PropertySignature signature, Object value);

    boolean isInstanceInjectable(InjectableValue value);

    boolean isValidInjectableValue(InjectableValue injectableValue, Object value);

    Scheme getScheme();

    Object removePropertyValue(PropertySignature signature);

    Map<PropertySignature, Object> removePropertyValues(Iterable<PropertySignature> signatures);

    Object setPropertySignature(PropertySignature signature, Object value);

    Map<PropertySignature, Object> setAllPropertySignatures(Map<PropertySignature, Object> values);

    void addInjectValue(InjectableValue injectable, Object value);

    void addAllInjectables(Map<InjectableValue, Object> values);

    Object removeInjectableValue(InjectableValue injectableValue);

    Map<InjectableValue, Object> removeAllInjectables(Iterable<InjectableValue> injectableValues);

    Object setInjectableValue(InjectableValue injectable, Object value);

    Map<InjectableValue, Object> setAllInjectableValues(Map<InjectableValue, Object> values);


}
