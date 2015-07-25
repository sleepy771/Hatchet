package org.hatchetproject.value_management.inject_default;

import org.hatchetproject.reflection.ConstructorSetter;
import org.hatchetproject.reflection.FieldSetter;
import org.hatchetproject.reflection.MethodSetter;
import org.hatchetproject.reflection.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by filip on 7/25/15.
 */
public interface AssignedParameters extends Parameters {

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
    }

    Class forClass();

    String getName();

    Type getType();
}
