package org.hatchetproject.reflection.accessors;

import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.reflection.meta.signatures.FieldMeta;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FieldGetter implements Targetable, Getter {

    private final Field field;

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private Object target;

    private FieldMeta lazyMeta;

    private final Set<ReadyListener<Getter>> listeners;

    public FieldGetter(Field field) {
        this.field = field;
        listeners = new HashSet<>();
    }

    @Override
    public void addListener(ReadyListener<Getter> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ReadyListener<Getter> listener) {
        listeners.remove(listener);
    }

    @Override
    public AccessorMeta getSignature() {
        if (null == lazyMeta) {
            lazyMeta = new FieldMeta(field);
        }
        return lazyMeta;
    }

    private void immutable() {
        throw new UnsupportedOperationException("Can not add arguments in field getter");
    }

    @Override
    public void setParameter(int index, Object value) {
        immutable();
    }

    @Override
    public void setAllParameters(Object[] values) {
        immutable();
    }

    @Override
    public void setAllParameters(List<Object> values) {
        immutable();
    }

    @Override
    public void add(Object value) {
        immutable();
    }

    @Override
    public void addAllParameters(Object[] values) {
        immutable();
    }

    @Override
    public void addAllParameters(List<Object> values) {
        immutable();
    }

    @Override
    public Object invoke(Object destination) throws InvocationException {
        return invoke(destination, EMPTY_ARRAY);
    }

    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new InvocationException("Can not invoke field", e);
        }
    }

    @Override
    public void setTarget(Object target) {
        if (!field.getDeclaringClass().isInstance(target)) {
            throw new ClassCastException("Can not cast " + target.getClass().getName() + " to " + field.getDeclaringClass().getName());
        }
        this.target = target;
        checkAssigned();
    }

    private void checkAssigned() {
        if (hasTarget()) {
            update();
        }
    }

    private void update() {
        for (ReadyListener<Getter> getterReadyListener : listeners) {
            getterReadyListener.onReady(this);
        }
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public boolean hasTarget() {
        return null != target;
    }
}
