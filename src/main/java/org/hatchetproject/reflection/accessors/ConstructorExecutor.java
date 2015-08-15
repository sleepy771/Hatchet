package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.ConstructorMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ConstructorExecutor extends AbstractAccessorExecutor {

    private final Constructor constructor;

    private ConstructorMeta lazyMeta;

    public ConstructorExecutor(@NotNull Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public ParametersBuilder createBuilder() {
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
    public final Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            return constructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InvocationException("Can not create new instance using constructor", e);
        }
    }

    @Override
    protected final boolean isValidBuilder(ParametersBuilder builder) {
        return Type.CONSTRUCTOR == builder.getType() && constructor.equals(builder.getConstructor());
    }
}
