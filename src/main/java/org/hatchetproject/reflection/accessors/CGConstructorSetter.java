package org.hatchetproject.reflection.accessors;

import com.sun.istack.internal.NotNull;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.MethodMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class CGConstructorSetter extends AbstractConstructorSetter {

    private final Map<MethodMeta, ObjectLessMethodSetter> setterMap;

    public CGConstructorSetter(@NotNull Constructor constructor) {
        super(constructor);
        setterMap = new HashMap<>();
    }

    @Override
    protected boolean isReady() {
        boolean methodsPrepared = true;
        for (ObjectLessMethodSetter methodSetter : setterMap.values()) {
            methodsPrepared &= methodSetter.isReady();
        }
        return methodsPrepared && isFilled();
    }

    @Override
    public Object invoke(Object object, Object[] values) throws InvocationException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(constructor.getDeclaringClass());
        CallbackHelper helper = new CallbackHelper(constructor.getDeclaringClass(), new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                MethodMeta meta = new MethodMeta(method);
                if (setterMap.containsKey(meta)) {
                    try {
                        return setterMap.get(meta).invoke();
                    } catch (InvocationException e) {
                        return NoOp.INSTANCE;
                    }
                } else {
                    return NoOp.INSTANCE;
                }
            }
        };
        try {
            enhancer.setCallbacks(helper.getCallbacks());
            enhancer.setCallbackFilter(helper);
            return enhancer.create(constructor.getParameterTypes(), values);
        } catch (IllegalArgumentException iae) {
            throw new InvocationException("Instatiation failed", iae);
        }
    }

    public void addMethodSetter(ObjectLessMethodSetter setter) {
        setterMap.put(setter.getSignature(), setter);
    }
}
