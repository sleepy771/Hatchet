package org.hatchetproject.reflection.cglib;

import net.sf.cglib.proxy.FixedValue;
import org.hatchetproject.reflection.meta.signatures.MethodMeta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public interface ProxyCallback {

    enum ProxyType {
        FIXED_VALUE_FORCED_RETURN, FIXED_VALUE_PROXY, INVOCATION_HANDLER_PROXY, INVOCATION_HANDLER_CALLBACK;

        Object createCallback(final Object instance, final Object forcedReturn, final ProxyCallback callback, final Method originalMethod) {
            switch (this) {
                case FIXED_VALUE_FORCED_RETURN:
                    return (FixedValue) () -> forcedReturn;
                case FIXED_VALUE_PROXY:
                    return (FixedValue) () -> originalMethod.invoke(instance);
                case INVOCATION_HANDLER_PROXY:
                    return (InvocationHandler) (proxy, method, args) -> method.invoke(instance, args);
                case INVOCATION_HANDLER_CALLBACK:
                    return (InvocationHandler) (proxy, method, args) -> callback.execute(instance, args);
            }
            return null;
        }
    }

    MethodMeta getSignature();

    Object execute(Object instance, Object... parameters) throws Exception;

    Object byType(Object instance);
}
