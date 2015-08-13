package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.reflection.meta.signatures.MethodMeta;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class MethodExecutor extends AbstractAccessorExecutor implements Targetable {

    private final Method method;

    private MethodMeta lazyMeta;

    private Object target;

    public MethodExecutor(@NotNull Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        try {
            setTarget(object);
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
    public MethodMeta getSignature() {
        if (null == lazyMeta) {
            lazyMeta = new MethodMeta(method);
        }
        return lazyMeta;
    }

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return Type.METHOD == builder.getType() && method.equals(builder.getMethod());
    }

    @Override
    public final void setTarget(Object target) {
        if (null != target && !getBuilder().getDeclaringClass().isInstance(target)) {
            throw new IllegalArgumentException("Invalid instance");
        }
        this.target = target;
        checkAssigned();
    }

    @Override
    public final Object getTarget() {
        return target;
    }

    @Override
    public final boolean hasTarget() {
        return null != target;
    }
}
