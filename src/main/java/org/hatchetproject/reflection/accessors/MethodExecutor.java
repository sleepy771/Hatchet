package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodExecutor extends AbstractAccessorExecutor {

    private final Method method;

    public MethodExecutor(@NotNull Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            return method.invoke(object, values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new InvocationException("Can not invoke method", e);
        }
    }

    @Override
    public ParametersBuilder createBuilder() {
        setBuilder(ParametersBuilder.createMethodParametersBuilder(method));
        return getBuilder();
    }

    @Override
    public AccessorMeta getSignature() {
        return null;
    }

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return Type.METHOD == builder.getType() && method.equals(builder.getMethod());
    }
}
