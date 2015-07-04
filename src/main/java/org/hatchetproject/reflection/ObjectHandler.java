package org.hatchetproject.reflection;

import java.util.Collection;
import java.util.Map;

public interface ObjectHandler<T> {

    void addPropertySetter(PropertySetter setter);

    void addAllPropertySetters(Collection<PropertySetter> setters);

    void addPropertyGetter(PropertyGetter getter);

    void addAllPropertyGetters(Collection<PropertyGetter> getters);

    boolean isObjectProperty(IProperty property);

    void setObjectBuilder(ObjectBuilder<T> builder);

    Map<PropertySignature, Object> readObject(T object);

    T createObject(Map<PropertySignature, Object> values);
}
