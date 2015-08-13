package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import org.hatchetproject.exceptions.InvocationException;

import java.lang.reflect.Method;

public class MethodSetter extends MethodExecutor implements Setter {

    private static final Logger LOGGER = Logger.getLogger(MethodSetter.class);

    public MethodSetter(@NotNull Method method) {
        super(method);
    }

    @Override
    protected void update() {
        try {
            invoke(getTarget());
        } catch (InvocationException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected boolean isReady() {
        return hasTarget() && isFilled();
    }
}
