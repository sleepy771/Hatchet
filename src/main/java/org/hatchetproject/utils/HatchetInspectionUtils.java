package org.hatchetproject.utils;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hatchetproject.reflection.ObjectMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static boolean isEmpty(String name) {
        return name == null || name.trim().isEmpty();
    }

    public static String getGSetterName(String name) {
        name = name.trim();
        String trimmedTail = name.substring(4);
        char leadingCharacter = name.charAt(3);
        return Character.toLowerCase(leadingCharacter) + trimmedTail;
    }

    public static boolean isValidPropertyName(String name) {
        return !isEmpty(name) && name.matches("^-|([a-zA-Z][\\w@#$_\\-]*)$");
    }

    public static int getDistanceFromObject(Class clazz) {
        if (clazz.isInterface())
            return -1;
        int k = 0;
        while (clazz != Object.class) {
            clazz = clazz.getSuperclass();
            k++;
        }
        return k;
    }

    public static int getDistanceFromRoot(Class clazz) {
        return null;
    }
}
