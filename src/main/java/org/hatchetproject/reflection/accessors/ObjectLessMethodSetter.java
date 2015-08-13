package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import net.sf.cglib.proxy.FixedValue;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.MethodMeta;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Method;

public class ObjectLessMethodSetter extends AbstractAccessorExecutor implements Setter {

    private final Method method;

    private MethodMeta lazyMeta;

    public ObjectLessMethodSetter(@NotNull Method method) {
        this.method = method;
    }

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return this.method.equals(builder.getMethod());
    }

    @Override
    protected ParametersBuilder createBuilder() {
        return ParametersBuilder.createCGMethodParametersBuilder(method);
    }

    @Override
    protected void update() {
    }

    @Override
    protected boolean isReady() {
        return isFilled();
    }

    @Override
    public MethodMeta getSignature() {
        if (null == lazyMeta) {
            lazyMeta = new MethodMeta(method);
        }
        return lazyMeta;
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        return (FixedValue) () -> values[0];
    }
}
