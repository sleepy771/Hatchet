package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.Getter;
import org.hatchetproject.reflection.accessors.ReadyListener;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyHelper;

import java.util.HashSet;
import java.util.Set;

public class PropertyGetterImpl implements PropertyGetter {

    private final Getter getter;

    private PropertyHelper helper;

    public PropertyGetterImpl(Getter getter, PropertyHelper helper) {
        this.helper = helper;
        this.getter = getter;
    }

    public PropertyGetterImpl(Getter getter) {
        this(getter, null);
    }


    @Override
    public PropertyPromise getPromise(Object source) {
        return new PropertyPromiseImpl(this);
    }

    @Override
    public void setHelper(PropertyHelper helper) {
        this.helper = helper;
    }

    @Override
    public PropertyHelper getHelper() {
        return helper;
    }

    @Override
    public Getter getGetter() {
        return getter;
    }

    @Override
    public void setTarget(Object target) {
        getter.setTarget(target);
    }

    @Override
    public Object getTarget() {
        return getter.getTarget();
    }

    @Override
    public boolean hasTarget() {
        return getter.hasTarget();
    }

    private static class PropertyPromiseImpl implements PropertyPromise, ReadyListener<Getter> {

        private final PropertyGetterImpl impl;

        private Set<ReadyListener<PropertyPromise>> promiseListeners;

        private boolean isComplete;

        private Object result;

        private Exception exception;

        public PropertyPromiseImpl(PropertyGetterImpl getterImpl) {
            this.promiseListeners = new HashSet<>();
            isComplete = false;
            this.impl = getterImpl;
            this.impl.getGetter().addListener(this);
        }

        @Override
        public Object getRaw() throws PropertyGetterException, InterruptedException {
            if(!isComplete()) {
                throw new PropertyGetterException("Getter was not invoked yet");
            }
            if (null != exception) {
                throw new PropertyGetterException("Getter failed with exception", exception);
            }
            return result;
        }

        @Override
        public PropertyHelper getHelper() {
            return impl.getHelper();
        }

        @Override
        public void removeListener(ReadyListener listener) {
            this.promiseListeners.remove(listener);
        }

        @Override
        public void addListener(ReadyListener listener) {
            // TODO solve this fcking shit of generick
            this.promiseListeners.add(listener);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object get() throws PropertyGetterException, InterruptedException {
            return null != getHelper() ? getHelper().push(getRaw()) : getRaw();
        }

        @Override
        public boolean isComplete() {
            return isComplete;
        }

        @Override
        public void onReady(Getter object) {
            this.exception = object.getException();
            this.result = object.getResult();
            isComplete = true;
            for(ReadyListener<PropertyPromise> promiseListener : promiseListeners) {
                promiseListener.onReady(this);
            }
            object.removeListener(this);
        }
    }
}
