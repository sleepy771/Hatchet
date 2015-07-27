package org.hatchetproject.value_management.inject_default;

import org.hatchetproject.reflection.Classy;
import org.hatchetproject.reflection.ConstructorSetter;
import org.hatchetproject.reflection.FieldSetter;
import org.hatchetproject.reflection.MethodSetter;
import org.hatchetproject.reflection.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface AssignedParameters extends Parameters, Classy {

    enum Type {
        CONSTRUCTOR, METHOD, FIELD;

        Setter createSetter(Constructor constructor, Method method, Field field) {
            switch (this) {
                case METHOD:
                    return new MethodSetter(method);
                case CONSTRUCTOR:
                    return new ConstructorSetter(constructor);
                case FIELD:
                    return new FieldSetter(field);
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        Setter createSetter(Class setterClass, String name, Class[] paramTypes) throws NoSuchMethodException, NoSuchFieldException {
            switch (this) {
                case METHOD:
                    Method method = setterClass.getMethod(name, paramTypes);
                    return new MethodSetter(method);
                case CONSTRUCTOR:
                    Constructor constructor = setterClass.getConstructor(paramTypes);
                    return new ConstructorSetter(constructor);
                case FIELD:
                    Field field = setterClass.getField(name);
                    return new FieldSetter(field);
            }
            return null;
        }

        String getName(Method method, Constructor constructor, Field field) {
            switch (this) {
                case METHOD:
                    return method.getName();
                case FIELD:
                    return field.getName();
                case CONSTRUCTOR:
                    return constructor.getName();
            }
            return null;
        }

        Class getDeclaringClass(Method method, Constructor constructor, Field field) {
            switch (this) {
                case METHOD:
                    return method.getDeclaringClass();
                case CONSTRUCTOR:
                    return constructor.getDeclaringClass();
                case FIELD:
                    return field.getDeclaringClass();
            }
            return null;
        }

        int getParameterCount(Method method, Constructor constructor, Field field) {
            switch (this) {
                case METHOD:
                    return method.getParameterCount();
                case CONSTRUCTOR:
                    return constructor.getParameterCount();
                case FIELD:
                    return 1;
            }
            return -1;
        }

        Class[] getParameterTypes(Method method, Constructor constructor, Field field) {
            switch (this) {
                case METHOD:
                    return method.getParameterTypes();
                case CONSTRUCTOR:
                    return constructor.getParameterTypes();
                case FIELD:
                    return new Class[] {field.getType()};
            }
            return null;
        }

        Class getReturnType(Method method, Constructor constructor, Field field) {
            switch (this) {
                case METHOD:
                    return method.getReturnType();
                case CONSTRUCTOR:
                    return constructor.getDeclaringClass();
                case FIELD:
                    return field.getType();
            }
            return null;
        }
    }

    String getName();

    Type getType();
}
