package org.hatchetproject.utils;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by filip on 6/28/15.
 */
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
        return void.class == method.getReturnType() && method.getParameterCount() > 0;
    }

    public static boolean isAccessibleGetter(Method method) {
        return isPublic(method) && isGetter(method);
    }

    public static boolean isAccessibleSetter(Method method) {
        return isPublic(method) && isSetter(method);
    }
}
