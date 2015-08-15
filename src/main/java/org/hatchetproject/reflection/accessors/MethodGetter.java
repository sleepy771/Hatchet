package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MethodGetter extends MethodExecutor implements Getter {

    private final Set<ReadyListener<Getter>> listeners;

    private Object result;

    private Exception exception;

    public MethodGetter(@NotNull Method method) {
        super(method);
        listeners = new HashSet<>();
    }

    @Override
    public boolean isReady() {
        return getBuilder().isFilled() && hasTarget();
    }

    @Override
    public void addListener(ReadyListener<Getter> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(ReadyListener<Getter> listener) {
        this.listeners.remove(listener);
    }

    @Override
    public Object getResult() {
        return result;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public boolean hasFailed() {
        return null != exception;
    }

    @Override
    public void clear() {
        setTarget(null);
    }

    @Override
    protected void runWipe() {
        this.result = null;
        this.exception = null;
    }

    @Override
    protected void update() {
        try {
            this.result = invoke(getTarget());
            this.exception = null;
        } catch (InvocationException e) {
            this.exception = e;
            this.result = null;
        }
        for(ReadyListener<Getter> getterReadyListener : listeners) {
            getterReadyListener.onReady(this);
        }
    }
}
