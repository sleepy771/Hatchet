package org.hatchetproject.value_management.inject_default;

/**
 * Created by filip on 7/30/15.
 */
public abstract class AssignedParametersWithSetter implements AssignedParameters {

    protected abstract Class[] getParamTypes();

    protected abstract Object[] dirctlyAccessedValues();

//    protected Setter createSetter() throws NoSuchMethodException, NoSuchFieldException {
//        return getType().createSetter(getDeclaringClass(), getName(), getParamTypes());
//    }

//    protected Object invokeSetter(Object object) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        return createSetter().set(object, dirctlyAccessedValues());
//    }
}
