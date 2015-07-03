package org.hatchetproject.reflection;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 7/3/15.
 */
public class CGObjectBuilder {

    private Class objectClass;

    private Map<Signature, Object> values;

    private Object[] constructorValues;

    private Class[] argumentTypes;

    public CGObjectBuilder(Class objectClass, int constructorParamLength) {
        this.objectClass = objectClass;
        this.constructorValues = new Object[constructorParamLength];
        this.argumentTypes = new Class[constructorParamLength];
    }

    public CGObjectBuilder(Class objectClass, Class[] argumentTypes) {
        this(objectClass, argumentTypes.length);
        System.arraycopy(argumentTypes, 0, this.argumentTypes, 0, argumentTypes.length);
    }

    public CGObjectBuilder(Constructor constructor) {
        this(constructor.getDeclaringClass(), constructor.getParameterTypes());
    }

    public CGObjectBuilder setConstructorValue(int idx, Object value) {
        if (!argumentTypes[idx].isInstance(value))
            throw new IllegalArgumentException("Invalid value type");
        constructorValues[idx] = value;
        return this;
    }

    public Object getConstructorValue(int idx) {
        return constructorValues[idx];
    }

    public Class getConstructorValueType(int idx) {
        return argumentTypes[idx];
    }

    // TODO create value assign

    public Object build() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objectClass);
        CallbackHelper callbackHelper = new CallbackHelper(objectClass, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                Signature methodSgn = new MethodSignature(method);
                if (values.containsKey(methodSgn)) {
                    return new FixedValue() {
                        Object value = values.get(methodSgn);
                        @Override
                        public Object loadObject() throws Exception {
                            return value;
                        }
                    };
                }
                return NoOp.INSTANCE;
            }
        };
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        return enhancer.create(argumentTypes, constructorValues);
    }
}
