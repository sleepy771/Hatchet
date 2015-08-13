package org.hatchetproject.reflection.accessors;

import org.hatchetproject.reflection.accessors.property.Promise;
import org.hatchetproject.reflection.properties.Property;

import java.util.Map;

public interface PromiseCollector {

    void addPromise(Promise promise);

    boolean hasCollected();

    boolean failed();

    Map<Property, Object> getValues() throws Exception;
}
