package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorExecutor extends AbstractAccessorExecutor {

    private final Constructor constructor;

    public ConstructorExecutor(@NotNull Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public ParametersBuilder createBuilder() {
        setBuilder(ParametersBuilder.createConstructorParametersBuilder(constructor));
        return getBuilder();
    }

    @Override
    public AccessorMeta getSignature() {
        return null;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            return constructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InvocationException("Can not create new instance using constructor", e);
        }
    }

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return Type.CONSTRUCTOR == builder.getType() && constructor.equals(builder.getConstructor());
    }
}
