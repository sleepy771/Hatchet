package org.hatchetproject.reflection.accessors;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.property.Promise;
import org.hatchetproject.reflection.accessors.property.PropertyPromise;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyGetterHelper;
import org.hatchetproject.reflection.meta.signatures.PropertyMeta;

import java.util.HashSet;
import java.util.Set;

public class PromiseImpl<TYPE, RAW_TYPE> implements PropertyPromise<TYPE, RAW_TYPE>, ReadyListener<Getter> {

    private static final Logger LOGGER = Logger.getLogger(PromiseImpl.class);

    private PropertyGetterHelper<TYPE, RAW_TYPE> helper;
    private final Set<ReadyListener<Promise>> listeners;
    private final PropertyMeta meta;
    private final Object source;
    private RAW_TYPE result;
    private TYPE type;
    private boolean failed;
    private boolean isComplete;

    public PromiseImpl(PropertyMeta meta, Object source, PropertyGetterHelper<TYPE, RAW_TYPE> helper) {
        this.meta = meta;
        this.source = source;
        this.helper = helper;
        this.listeners = new HashSet<>();
        this.failed = false;
        this.isComplete = false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TYPE get() throws PropertyGetterException, InterruptedException {
        if (null == type) {
            if (null != helper) {
                type = helper.extract(getRaw());
            } else {
                type = (TYPE) getRaw();
            }
        }
        return type;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public RAW_TYPE getRaw() throws PropertyGetterException, InterruptedException {
        if (!isComplete) {
            throw new PropertyGetterException("Can not obtain property now");
        }
        if (failed) {
            throw new PropertyGetterException("Obtaining property failed");
        }
        return result;
    }

    @Override
    public void setHelper(PropertyGetterHelper<TYPE, RAW_TYPE> helper) {
        this.helper = helper;
    }

    @Override
    public PropertyGetterHelper<TYPE, RAW_TYPE> getHelper() {
        return this.helper;
    }

    @Override
    public PropertyMeta getProperty() {
        return meta;
    }

    @Override
    public void addListener(ReadyListener<Promise> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(ReadyListener<Promise> listener) {
        this.listeners.remove(listener);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onReady(Getter object) {
        try {
            this.isComplete = true;
            this.result = (RAW_TYPE) object.invoke(source);
        } catch (InvocationException e) {
            this.failed = true;
            LOGGER.error("Invocation failed", e);
        }
        this.updateListeners();
        object.removeListener(this);
    }

    private void updateListeners() {
        for (ReadyListener<Promise> promiseListener : this.listeners) {
            promiseListener.onReady(this);
        }
    }
}
