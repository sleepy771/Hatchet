package org.hatchetproject.utils;

import org.hatchetproject.annotations.IsProperty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class HatchetInspectionUtils {

    public static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    public static boolean isPublic(Constructor constructor) {
        return Modifier.isPublic(constructor.getModifiers());
    }

    public static boolean isProtected(Method method) {
        return Modifier.isProtected(method.getModifiers());
    }

    public static boolean isProtected(Constructor constructor) {
        return Modifier.isProtected(constructor.getModifiers());
    }

    public static boolean isGetter(Method method) {
        return (void.class != method.getReturnType() || Void.class != method.getReturnType())
                && method.getParameterCount() == 0;
    }

//    public static boolean isGetter(Signature signature) {
//        return (signature.getReturnType() != void.class || Void.class != signature.getReturnType())
//                && signature.getParametersCount() == 0;
//    }

    public static boolean isSetter(Method method) {
        return method.getParameterCount() > 0;
    }

//    public static boolean isSetter(Signature signature) {
//        return signature.getParametersCount() > 0;
//    }

    public static boolean isAccessibleGetter(Method method) {
        return isPublic(method) && isGetter(method);
    }

    public static boolean isAccessibleSetter(Method method) {
        return isPublic(method) && isSetter(method);
    }

    public static boolean isEmpty(String name) {
        return name == null || name.trim().isEmpty();
    }

    public static boolean isValidPropertyName(String name) {
        return !isEmpty(name) && name.matches("^-|([a-zA-Z][\\w@#$_\\-]*)$");
    }

    public static String createPropertyName(String methodName, String propertyName) {
        if ("-".equals(propertyName)) {
            if ((methodName.startsWith("set") || methodName.startsWith("get")) && methodName.length() > 3) {
                return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
            }
            return Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
        } else if (isValidPropertyName(propertyName)) {
            return propertyName;
        }
        throw new IllegalArgumentException("Wrong property name");
    }

    public static String createMethodPropertyName(Method method, IsProperty isProperty) {
        if (isProperty == null)
            throw new NullPointerException();
        return createPropertyName(method.getName(), isProperty.name());
    }

    public static boolean isAccessibleConstructor(Constructor constructor) {
        return isPublic(constructor) || isProtected(constructor);
    }

    public static boolean safeEquals(Object o1, Object o2) {
        return (o1 != null && o2 != null && o1.equals(o2)) || o1 == o2;
    }
}
