package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public class ConstructorSetter extends ConstructorExecutor implements Setter {

    private final Set<ReadyListener<ConstructorSetter>> listenerSet;

    public ConstructorSetter(@NotNull Constructor constructor) {
        super(constructor);
        listenerSet = new HashSet<>();
    }

    @Override
    protected void update() {
        for (ReadyListener<ConstructorSetter> setterReadyListener : this.listenerSet) {
            setterReadyListener.onReady(this);
        }
    }

    @Override
    protected boolean isReady() {
        return getBuilder().isFilled();
    }

    public void addReadyListener(ReadyListener<ConstructorSetter> setterReadyListener) {
        this.listenerSet.add(setterReadyListener);
    }

    public void removeReadyListener(ReadyListener<ConstructorSetter> setterReadyListener) {
        this.listenerSet.remove(setterReadyListener);
    }
}
