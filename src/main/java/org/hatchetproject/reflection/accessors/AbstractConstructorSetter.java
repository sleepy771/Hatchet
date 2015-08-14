package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.property.Promise;
import org.hatchetproject.reflection.meta.signatures.ConstructorMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractConstructorSetter extends AbstractAccessorExecutor implements Setter {

    protected final Constructor constructor;

    private ConstructorMeta lazyMeta;

    private final Set<ReadyListener<AbstractConstructorSetter>> setterReadyListeners;

    private Object instance;

    private Exception exception;

    public AbstractConstructorSetter(@NotNull Constructor constructor) {
        this.constructor = constructor;
        this.setterReadyListeners = new HashSet<>();
    }

    @Override
    public final ParametersBuilder createBuilder() {
        setBuilder(ParametersBuilder.createConstructorParametersBuilder(constructor));
        return getBuilder();
    }

    @Override
    public final ConstructorMeta getSignature() {
        if (null == lazyMeta) {
            lazyMeta = new ConstructorMeta(constructor);
        }
        return lazyMeta;
    }

    @Override
    protected final boolean isValidBuilder(ParametersBuilder builder) {
        return Type.CONSTRUCTOR == builder.getType() && constructor.equals(builder.getConstructor());
    }

    public final Promise getPromise() {
        ConstructorPromise promise = new ConstructorPromise();
        this.addReadyListener(promise);
        return promise;
    }

    public final Object getInstance() throws Exception {
        if (null != exception) {
            throw exception;
        }
        return instance;
    }

    public final Exception getException() {
        return exception;
    }

    public void clear() {
        exception = null;
        instance = null;
        setterReadyListeners.clear();
        getBuilder().clear();
    }

    @Override
    protected final void update() {
        try {
            instance = invoke(null);
            exception = null;
        } catch (InvocationException e) {
            exception = e;
            instance = null;
        }
        for (ReadyListener<AbstractConstructorSetter> setterReadyListener : setterReadyListeners) {
            setterReadyListener.onReady(this);
        }
        setterReadyListeners.clear();
    }

    public boolean hasFailed() {
        return null != getException();
    }

    public final void addReadyListener(ReadyListener<AbstractConstructorSetter> objectReadyListener) {
        this.setterReadyListeners.add(objectReadyListener);
    }

    public final void removeReadyListener(ReadyListener<AbstractConstructorSetter> objectReadyListener) {
        this.setterReadyListeners.remove(objectReadyListener);
    }

    protected final static class ConstructorPromise implements Promise, ReadyListener<AbstractConstructorSetter> {

        private Object constructedObject;
        private Exception exception;
        private boolean isComplete;

        @Override
        public Object get() throws PropertyGetterException, InterruptedException {
            if (!isComplete) {
                throw new PropertyGetterException("Object not instantiated");
            }
            if (hasFailed()) {
                throw new PropertyGetterException("Instantiation failed", getException());
            }
            return constructedObject;
        }

        @Override
        public boolean isComplete() {
            return isComplete;
        }

        public boolean hasFailed() {
            return null != exception;
        }

        public Exception getException() {
            return exception;
        }

        @Override
        public void onReady(AbstractConstructorSetter constructorSetter) {
            try {
                constructedObject = constructorSetter.getInstance();
                exception = null;
            } catch (Exception e) {
                exception = e;
                constructedObject = null;
            }
            isComplete = true;
            constructorSetter.removeReadyListener(this);
        }
    }
}
