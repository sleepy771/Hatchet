package org.hatchetproject.utils;

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
        return (void.class != method.getReturnType() || Void.class != method.getReturnType()) && method.getParameterCount() == 0;
    }

    public static boolean isSetter(Method method) {
        return method.getParameterCount() > 0;
    }

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
}
