package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Field;

public class FieldExecutor extends AbstractAccessorExecutor {

    private final Field field;

    public FieldExecutor(@NotNull Field field) {
        this.field = field;
    }

    @Override
    public ParametersBuilder createBuilder() {
        setBuilder(ParametersBuilder.createFieldParametersBuilder(field));
        return getBuilder();
    }

    @Override
    public AccessorMeta getSignature() {
        return null;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            Object oldValue = field.get(object);
            field.set(object, values[0]);
            return oldValue;
        } catch (IllegalAccessException e) {
            throw new InvocationException("Can not assign value in field", e);
        }
    }

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return Type.FIELD == builder.getType() && this.field.equals(builder.getField());
    }
}
