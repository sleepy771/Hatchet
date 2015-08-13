package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MethodGetter extends MethodExecutor implements Getter {

    private final Set<ReadyListener<Getter>> listeners;

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
    protected void update() {
        for(ReadyListener<Getter> getterReadyListener : listeners) {
            getterReadyListener.onReady(this);
        }
    }
}
