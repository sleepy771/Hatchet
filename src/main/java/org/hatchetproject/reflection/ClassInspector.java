package org.hatchetproject.reflection;

import org.hatchetproject.annotations.HasProperties;
import org.hatchetproject.annotations.InjectValue;
import org.hatchetproject.annotations.InjectValues;
import org.hatchetproject.annotations.IsProperty;
import org.hatchetproject.annotations.Prefer;
import org.hatchetproject.exceptions.InspectionException;
import org.hatchetproject.reflection.meta.signatures.Signature;
import org.hatchetproject.utils.HatchetInspectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassInspector {

    private final Class clazz;

    private Set<Flag> flags;

    private EnumSet<Flag> allValid = EnumSet.of(Flag.INSPECTED_CONSTRUCTORS, Flag.INSPECTED_FIELDS, Flag.INSPECTED_METHODS);

    public List<Method> getters;
    public List<Method> setters;
    public List<Field> propertyFields;
    public List<Field> injectDefaultFields;
    public List<Constructor> constructors;
    private Map<Signature, PropertyBuilder> propertyBuilderMap;

    private final static HashSet<Class<? extends Annotation>> INTERESTING_ANNOTATIONS = new HashSet<Class<? extends Annotation>>() {
        {
            add(InjectValue.class);
            add(InjectValues.class);
            add(Prefer.class);
            add(HasProperties.class);
            add(IsProperty.class);
        }
    };

    private ClassInspector(Class clazz) {
        this.clazz = clazz;
        flags = new HashSet<>();
        flags.add(Flag.NOT_INSPECTED);
    }

    private void addFlag(Flag flag) {
        if (flags.contains(Flag.NOT_INSPECTED))
            flags.remove(Flag.NOT_INSPECTED);
        flags.add(flag);
        if (isInspected() && !flags.contains(Flag.INVALID))
            flags.add(Flag.VALID);
    }

    public void runInspection() throws InspectionException {
        inspectFields();
        inspectMethods();
        getConstructors();
    }

    public List<Method> getGetters() throws InspectionException {
        if (getters == null) {
            inspectMethods();
        }
        return getters;
    }

    public List<Method> getSetters() throws InspectionException {
        if (setters == null) {
            inspectMethods();
        }
        return setters;
    }

    public boolean isInspected() {
        return !flags.contains(Flag.NOT_INSPECTED) && flags.containsAll(allValid);
    }

    public boolean isValid() {
        return flags.contains(Flag.VALID);
    }

    private void inspectMethods() throws InspectionException {
        setters = new ArrayList<>();
        getters = new ArrayList<>();
        addFlag(Flag.INSPECTED_METHODS);
        for (Method method : clazz.getMethods()) {
            IsProperty methodIsProperty = method.getAnnotation(IsProperty.class);
            if (!HatchetInspectionUtils.isPublic(method)) {
                addFlag(Flag.INVALID);
                throw new InspectionException("Method defines IsProperty annotation, but is not public");
            }
            if (HatchetInspectionUtils.isSetter(method)) {
                setters.add(method);
            } else if (HatchetInspectionUtils.isGetter(method)) {
                if (method.getAnnotation(InjectValues.class) != null
                        || method.getAnnotation(InjectValue.class) != null) {
                    addFlag(Flag.INVALID);
                    throw new UnsupportedOperationException("Getter has deffined one or more InjectValue annotations, but they are not supported yet");
                }
                getters.add(method);
            } else {
                addFlag(Flag.INVALID);
                throw new InspectionException("Method have deffined Propperty annotation but is neither getter, nor setter");
            }
        }
    }

    public List<Field> getPropertyFields() throws InspectionException {
        if (propertyFields == null) {
            inspectFields();
        }
        return propertyFields;
    }

    public List<Field> getInjectDefaultFields() throws InspectionException {
        if (injectDefaultFields == null) {
            inspectFields();
        }
        return injectDefaultFields;
    }

    public void inspectFields() throws InspectionException {
        propertyFields = new ArrayList<>();
        injectDefaultFields = new ArrayList<>();
        addFlag(Flag.INSPECTED_FIELDS);
        for (Field field : clazz.getFields()) {
            IsProperty fieldIsProperty = field.getAnnotation(IsProperty.class);
            InjectValue injectValue = field.getAnnotation(InjectValue.class);
            if (fieldIsProperty != null || injectValue != null) {
                if (!HatchetInspectionUtils.isPublic(field)) {
                    addFlag(Flag.INVALID);
                    throw new InspectionException("Field is not public");
                }
                if (fieldIsProperty != null ^ injectValue != null) {
                    if (fieldIsProperty != null) {
                        propertyFields.add(field);
                        continue;
                    }
                    injectDefaultFields.add(field);
                } else {
                    addFlag(Flag.INVALID);
                    throw new InspectionException("Field can not define both InjectValue and IsProperty annotation.");
                }
            }
        }
    }

    public List<Constructor> getConstructors() throws InspectionException {
        if (constructors == null) {
            addFlag(Flag.INSPECTED_CONSTRUCTORS);
            constructors = new ArrayList<>();
            for (Constructor constructor : clazz.getConstructors()) {
                if (HatchetInspectionUtils.isPublic(constructor)
                        || HatchetInspectionUtils.isProtected(constructor) // TODO check it this works
                        && isInteresting(constructor)) {
                    constructors.add(constructor);
                }
            }
            if (constructors.isEmpty()) {
                addFlag(Flag.INVALID);
                throw new InspectionException("No constructor was deffined");
            }
        }
        return constructors;
    }

    private boolean isInteresting(Constructor constructor) {
        return constructor.getParameterCount() == 0
                || hasAnnotation(INTERESTING_ANNOTATIONS, constructor);
    }

    private boolean hasAnnotation(Set<Class<? extends Annotation>> annotations, Constructor constructor) {
        for (Annotation annotation : constructor.getAnnotations()) {
            if (annotations.contains(annotation.annotationType()))
                return true;
        }
        return false;
    }

    enum Flag {
        VALID, INVALID, NOT_INSPECTED, INSPECTED_FIELDS, INSPECTED_METHODS, INSPECTED_CONSTRUCTORS;
    }
}
