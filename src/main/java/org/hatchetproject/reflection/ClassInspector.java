package org.hatchetproject.reflection;

import org.hatchetproject.annotations.InjectDefault;
import org.hatchetproject.annotations.InjectMultiple;
import org.hatchetproject.annotations.Prefer;
import org.hatchetproject.annotations.Properties;
import org.hatchetproject.annotations.Property;
import org.hatchetproject.utils.HatchetInspectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ClassInspector {

    private final Class clazz;

    public List<Method> getters;
    public List<Method> setters;
    public List<Field> fields;
    public List<Field> injectValueFields;
    public List<Constructor> constructors;
    private Map<Signature, PropertyBuilder> propertyBuilderMap;

    private final static HashSet<Class<? extends Annotation>> constructorAnnotations = new HashSet<Class<? extends Annotation>>() {
        {
            add(InjectDefault.class);
            add(InjectMultiple.class);
            add(Prefer.class);
            add(Properties.class);
            add(Property.class);
        }
    };

    private ClassInspector(Class clazz) {
        this.clazz = clazz;
    }

    public void runInspection() {

    }

    public List<Method> getGetters() {
        if (getters == null) {
            getters = new ArrayList<>();
            for (Method method : clazz.getMethods()) {
                if (HatchetInspectionUtils.isAccessibleGetter(method)) {
                    Property methodProperty = method.getAnnotation(Property.class);
                    InjectMultiple defaults = method.getAnnotation(InjectMultiple.class);
                    InjectDefault def = method.getAnnotation(InjectDefault.class);
                    if (defaults != null || def != null)
                        throw new IllegalArgumentException();
                    if (methodProperty != null) {
                        getters.add(method);
                    }
                }
            }
        }
        return getters;
    }

    public List<Method> getSetters() {
        if (setters == null) {
            setters = new ArrayList<>();
            for (Method method : clazz.getMethods()) {
                if (HatchetInspectionUtils.isAccessibleSetter(method)) {
                    Property methodProperty = method.getAnnotation(Property.class);
                    if (methodProperty != null) {
                        setters.add(method);
                    }
                }
            }
        }
        return setters;
    }

    public List<Field> getFields() {
        if (fields == null) {
            fields = new ArrayList<>();
            for (Field field : clazz.getFields()) {
                if (HatchetInspectionUtils.isPublic(field)
                        && field.getAnnotation(Property.class) != null) {
                    if (field.getAnnotation(InjectDefault.class) != null)
                        throw new IllegalArgumentException();
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    public List<Field> getInjectValueFields() {
        if (injectValueFields == null) {
            injectValueFields = new ArrayList<>();
            for (Field field : clazz.getFields()) {
                if (HatchetInspectionUtils.isPublic(field)
                        && field.getAnnotation(InjectDefault.class) != null) {
                    if (field.getAnnotation(Property.class) != null)
                        throw new IllegalArgumentException();
                    injectValueFields.add(field);
                }
            }
        }
        return injectValueFields;
    }

    public List<Constructor> getConstructors() {
        if (constructors == null) {
            constructors = new ArrayList<>();
            for (Constructor constructor : clazz.getConstructors()) {
                if (HatchetInspectionUtils.isPublic(constructor)
                        && isInteresting(constructor)) {
                    constructors.add(constructor);
                }
            }
        }
        return constructors;
    }

    private boolean isInteresting(Constructor constructor) {
        return constructor.getParameterCount() == 0
                || hasAnnotation(constructorAnnotations, constructor);
    }

    private boolean hasAnnotation(Collection<Class<? extends Annotation>> annotations, Constructor constructor) {
        for (Class<? extends Annotation> annotationClass : annotations) {
            if (constructor.getAnnotation(annotationClass) != null)
                return true;
        }
        return false;
    }
}
