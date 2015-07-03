package org.hatchetproject.reflection;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CGObjectBuilder<T> implements ObjectBuilderWithMethodInterception<T>{
    private Class<T> objectClass;
    private Set<MethodSignature> expected;
    private Map<MethodSignature, Object> values;
    private Object[] constructorValues;
    private Class[] argumentTypes;

    public CGObjectBuilder(Class<T> objectClass, Class[] argumentTypes, Set<MethodSignature> expectedInterceptedMethods) {
        this.objectClass = objectClass;
        this.constructorValues = new Object[argumentTypes.length];
        this.argumentTypes = new Class[argumentTypes.length];
        System.arraycopy(argumentTypes, 0, this.argumentTypes, 0, argumentTypes.length);
        this.expected = new HashSet<>(expectedInterceptedMethods);
    }

    public CGObjectBuilder(Constructor<T> constructor, Set<MethodSignature> expectedInterceptedMethods) {
        this(constructor.getDeclaringClass(), constructor.getParameterTypes(), expectedInterceptedMethods);
    }

    public CGObjectBuilder(Scheme scheme) {
        // TODO implement
        throw new UnsupportedOperationException("Not implemented");
    }

    public CGObjectBuilder<T> setConstructorValue(int idx, Object value) {
        if (!argumentTypes[idx].isInstance(value))
            throw new IllegalArgumentException("Invalid value type");
        constructorValues[idx] = value;
        return this;
    }

    @Override
    public CGObjectBuilder<T> setConstructorValues(Object[] values) {
        for (int k = 0; k < values.length; k++) {
            setConstructorValue(k, values[k]);
        }
        return this;
    }

    @Override
    public CGObjectBuilder<T> clear() {
        values.clear();
        Arrays.fill(constructorValues, null);
        return this;
    }

    public Map<MethodSignature, Object> getMethodValues() {
        return Collections.unmodifiableMap(this.values);
    }

    public Object getConstructorValue(int idx) {
        return constructorValues[idx];
    }

    public Class getConstructorValueType(int idx) {
        return argumentTypes[idx];
    }

    private void checkMethodInterceptionValues() {
        for (MethodSignature signature : expected) {
            if (!values.containsKey(signature))
                throw new IllegalArgumentException("Incomplete");
        }
    }

    private void checkConstructorValues() {
        for (Object constructorValue : constructorValues)
            if (constructorValue == null)
                throw new IllegalArgumentException("Incomplete");
    }

    @SuppressWarnings("unchecked")
    public T build() {
        checkConstructorValues();
        checkMethodInterceptionValues();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objectClass);
        CallbackHelper callbackHelper = new CallbackHelper(objectClass, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                MethodSignature methodSgn = new MethodSignature(method);
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
        return (T) enhancer.create(argumentTypes, constructorValues);
    }

    @Override
    public CGObjectBuilder<T> putValue(MethodSignature valueSignature, Object value) {
        if (valueSignature.getParametersCount() != 0)
            throw new IllegalArgumentException();
        if (objectClass != valueSignature.getDeclaringClass())
            throw new IllegalArgumentException();
        if (!valueSignature.getReturnType().isInstance(value))
            throw new IllegalArgumentException();
        if (values.containsKey(valueSignature))
            throw new IllegalArgumentException();
        if (!expected.contains(valueSignature))
            throw new IllegalArgumentException();
        values.put(valueSignature, value);
        return this;
    }

    @Override
    public CGObjectBuilder<T> putValues(Map<MethodSignature, Object> values) {
        for (Map.Entry<MethodSignature, Object> entry : values.entrySet()) {
            putValue(entry.getKey(), entry.getValue());
        }
        return this;
    }
}
