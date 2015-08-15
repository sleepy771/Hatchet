package org.hatchetproject.reflection.accessors;

import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.reflection.meta.signatures.FieldMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Field;
import java.util.List;

public class FieldSetter extends AbstractAccessorExecutor implements Setter {

    private static final Logger LOGGER = Logger.getLogger(FieldSetter.class);

    private final Field field;

    private FieldMeta lazyMeta;

    private Object target;

    public FieldSetter(Field field) {
        this.field = field;
    }

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return builder.getField().equals(this.field);
    }

    @Override
    protected ParametersBuilder createBuilder() {
        return ParametersBuilder.createFieldParametersBuilder(field);
    }

    @Override
    protected void update() {
        try {
            invoke();
        } catch (InvocationException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected boolean isReady() {
        return getBuilder().isFilled() && hasTarget();
    }

    @Override
    public FieldMeta getSignature() {
        if (null == lazyMeta) {
            lazyMeta = new FieldMeta(field);
        }
        return lazyMeta;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            Object value = field.get(object);
            field.set(object, values[0]);
            return value;
        } catch (IllegalAccessException e) {
            throw new InvocationException("Can not access field", e);
        }
    }

    @Override
    public void setTarget(Object target) {
        if (!field.getDeclaringClass().isInstance(target)) {
            throw new IllegalArgumentException("target is not instance of " + field.getDeclaringClass().getName());
        }
        if (this.target == target) {
            return;
        }
        this.target = target;
        checkAssigned();
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
