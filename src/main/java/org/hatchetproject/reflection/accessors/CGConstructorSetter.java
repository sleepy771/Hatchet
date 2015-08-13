package org.hatchetproject.reflection.accessors;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.reflection.meta.signatures.ConstructorMeta;
import org.hatchetproject.reflection.meta.signatures.MethodMeta;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

public class CGConstructorSetter extends AbstractAccessorExecutor implements Setter {

    private Constructor constructor;

    private Map<MethodMeta, ObjectLessMethodSetter> setterMap;

    private ConstructorMeta lazyMeta;

    @Override
    protected boolean isValidBuilder(ParametersBuilder builder) {
        return builder.getConstructor().equals(constructor);
    }

    @Override
    protected ParametersBuilder createBuilder() {
        return ParametersBuilder.createConstructorParametersBuilder(constructor);
    }

    @Override
    protected void update() {

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
    public ConstructorMeta getSignature() {
        if (null == lazyMeta) {
            lazyMeta = new ConstructorMeta(constructor);
        }
        return lazyMeta;
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
                        return setterMap.get(meta).invoke(null);
                    } catch (InvocationException e) {
                        return NoOp.INSTANCE;
                    }
                } else {
                    return NoOp.INSTANCE;
                }
            }
        };
        enhancer.setCallbacks(helper.getCallbacks());
        enhancer.setCallbackFilter(helper);
        return enhancer.create(constructor.getParameterTypes(), values);
    }

    public void addMethodSetter(ObjectLessMethodSetter setter) {
        setterMap.put(setter.getSignature(), setter);
    }
}
