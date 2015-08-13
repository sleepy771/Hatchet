package org.hatchetproject.reflection.accessors;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.property.Promise;
import org.hatchetproject.reflection.properties.Property;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PromiseCollectorImpl implements PromiseCollector, PromiseListener {

    private static final Logger LOGGER = Logger.getLogger(PromiseCollectorImpl.class);

    private final Set<Promise> promises;
    private final Map<Property, Object> values;
    private boolean hasFailed;

    public PromiseCollectorImpl() {
        promises = new HashSet<>();
        values = new HashMap<>();
        hasFailed = false;
    }

    @Override
    public void addPromise(Promise promise) {
        this.promises.add(promise);
        promise.addListener(this);
    }

    @Override
    public boolean hasCollected() {
        return this.promises.isEmpty();
    }

    @Override
    public boolean failed() {
        return hasFailed;
    }

    @Override
    public Map<Property, Object> getValues() throws Exception {
        if (!hasCollected()) {
            throw new Exception("Incomplete");
        }
        if (failed()) {
            throw new Exception("Obtaining has failed");
        }
        return Collections.unmodifiableMap(values);
    }

    @Override
    public void onReady(Promise promise) {
        try {
            if (promises.contains(promise)) {
                Object result = promise.get();
                values.put(promise.getProperty(), result);
                promises.remove(promise);
            }
        } catch (InterruptedException | PropertyGetterException e) {
            hasFailed = true;
            LOGGER.error("promise didnt obtain property", e);
        }
    }
}
