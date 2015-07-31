package org.hatchetproject.reflection;

import com.sun.javaws.exceptions.InvalidArgumentException;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.transform.impl.AccessFieldTransformer.Callback;
import org.hatchetproject.Builder;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by filip on 7/31/15.
 */
public class CGProxifier<Type> implements Builder<Type>{

    private Map<MethodSignature, ProxyCallback> callbackMap;

    private final Class<Type> proxyClass;

    private Object instance;

    public CGProxifier(Class<Type> proxyClass) {
        this.proxyClass = proxyClass;
    }

    public CGProxifier(Class<Type> proxyClass, Type instance) {
        this(proxyClass);
        setInstance(instance);
    }

    public boolean setMethodOverride(ProxyCallback callback) {
        if (callbackMap.containsKey(callback.getSignature())) {
            return false;
        }
        callbackMap.put(callback.getSignature(), callback);
        return true;
    }

    public CGProxifier<Type> setInstance(Type instance) {
        if (!proxyClass.isInstance(instance)) {
            throw new IllegalArgumentException("Instance is not assignable from class " + proxyClass.getName());
        }
        this.instance = instance;
        return this;
    }

    private CallbackHelper createInterfaceCallbackHelper() {
        CallbackHelper helper = new CallbackHelper(Object.class, new Class[]{proxyClass}) {
            @Override
            protected Object getCallback(Method method) {
                MethodSignature signature = new MethodSignature(method);
                if (callbackMap.containsKey(signature)) {
                    return callbackMap.get(signature).byType(instance);
                }
                return NoOp.INSTANCE;
            }
        };
        return helper;
    }

    private Enhancer createInterfaceEnhancer() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setInterfaces(new Class[] {proxyClass});
        return enhancer;
    }

    private Enhancer createClassEnhancer() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxyClass);
        return enhancer;
    }

    private Enhancer createEnhancer() {
        if (proxyClass.isInterface()) {
            return createInterfaceEnhancer();
        }
        return createClassEnhancer();
    }

    @Override
    public Type build() {
        return null;
    }
}
