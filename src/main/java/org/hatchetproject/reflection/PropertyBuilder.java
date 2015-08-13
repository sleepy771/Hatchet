package org.hatchetproject.reflection;

import org.apache.log4j.Logger;
import org.hatchetproject.annotations.HasProperties;
import org.hatchetproject.annotations.InjectValue;
import org.hatchetproject.annotations.InjectValues;
import org.hatchetproject.annotations.IsProperty;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.reflection.accessors.property.PropertyGetter;
import org.hatchetproject.reflection.accessors.property.PropertySetter;
import org.hatchetproject.reflection.constants.AsSelf;
import org.hatchetproject.utils.HatchetInspectionUtils;
import org.hatchetproject.value_management.RegistrableValue.ValueSignature;
import org.hatchetproject.constants.UndefinedValueCast;
import org.hatchetproject.value_management.ValueCast;
import org.hatchetproject.value_management.ValueCastManager;
import org.hatchetproject.value_management.ValueCastSignature;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PropertyBuilder {

    private static Logger logger = Logger.getLogger(PropertyBuilder.class.getName());

    private Class propertyType;
    private String propertyName;

    private PropertySetter setter;

    private PropertyGetter getter;

    private Map<Integer, ValueSignature> injects;

    private int index;

    private PropertyBuilder(Class propertyType, String propertyName) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.index = -1; // Unset
    }

    public boolean canAssignSetter() {
        return setter == null;
    }

    public boolean canAssignGetter() {
        return getter == null;
    }

    public PropertyBuilder setType(Class propertyType) {
        if (propertyType != null)
            this.propertyType = propertyType;
        return this;
    }

    public PropertyBuilder setName(String name) {
        if (!HatchetInspectionUtils.isEmpty(name))
            this.propertyName = name;
        return this;
    }

    public PropertyBuilder setInject(int idx, ValueSignature valueSignature) {
        if (injects.containsKey(idx)) {
            throw new IllegalArgumentException();
        }
        injects.put(idx, valueSignature);
        return this;
    }

    public PropertyBuilder setSetter(PropertySetter setter) {
        if (setter != null) {
//            if (setter.getValueClass() != propertyType && setter.getCaster() == null)
//                throw new IllegalArgumentException("Invalid"); // Change to property exception
//            if (setter.getValueClass() != propertyType && setter.getCaster().getOutputType() != propertyType)
//                throw new IllegalArgumentException("Invalid");
            this.setter = setter;
        }
        return this;
    }

    public PropertyBuilder setGetter(PropertyGetter getter) {
        if (getter != null)
            this.getter = getter;
        return this;
    }

    public PropertyBuilder setIndex(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException();
        this.index = index;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public PropertyBuilder merge(PropertyBuilder builder) {
        if (!(propertyType == builder.propertyType && propertyName.equals(builder.propertyName))) {
            throw new IllegalArgumentException();
        }
        if (setter == null && builder.setter!= null || getter == null && builder.getter != null) {
            if (setter == null)
                setter = builder.setter;
            if (getter == null)
                getter = builder.getter;
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    static PropertyBuilder createPropertyBuilder(Field field, IsProperty fieldIsProperty) {
        if (!HatchetInspectionUtils.isValidPropertyName(fieldIsProperty.name())) {
            logger.error("Invalid property name, was set");
            throw new IllegalArgumentException();
        }
        if (fieldIsProperty.index() != -1) {
            logger.warn("Unsupported operating: index in field property was changed from default -1");
        }
        String name = "-".equals(fieldIsProperty.name()) ? field.getName() : fieldIsProperty.name();
        Class type = null;
        ValueCast caster = null;
        if (fieldIsProperty.type() != AsSelf.class) {
            type = fieldIsProperty.type();
            try {
                if (fieldIsProperty.caster() == UndefinedValueCast.class) {
                    caster = ValueCastManager.getInstance().get(new ValueCastSignature(field.getType(), fieldIsProperty.type()));
                } else {
                    caster = ValueCastManager.getInstance().getOrCreate(fieldIsProperty.caster());
                    if (!(caster.getInputType().isAssignableFrom(field.getType()) && fieldIsProperty.type().isAssignableFrom(caster.getOutputType()))) {
                        throw new IllegalArgumentException(); // propertyException
                    }
                }
            } catch (ManagerException e) {
                e.printStackTrace();
                // TODO throw PropertyException
            }
        } else {
            type = field.getType();
        }
        PropertyBuilder builder = new PropertyBuilder(type, name);
        try {
            builder.setGetter(new FieldPropertyGetter(field, caster));
        } catch (PropertyAccessorException e) {
            e.printStackTrace();
        }
        try {
            builder.setSetter(new FieldPropertySetter(field, caster != null ? caster.reverseValueCast() : null));
        } catch (PropertyAccessorException e) {
            e.printStackTrace();
        }
        return builder;
    }

    static PropertyBuilder createPropertyBuilder(Method method, IsProperty isProperty) {
        if (!HatchetInspectionUtils.isValidPropertyName(isProperty.name()))
            throw new IllegalArgumentException("Invalid isProperty name");
        String name = HatchetInspectionUtils.createMethodPropertyName(method, isProperty);
        Class type = null;
        ValueCast caster = null;
        Class methodAttrType = null;
        int propertyType = 0;
        if (HatchetInspectionUtils.isAccessibleGetter(method)) {
            methodAttrType = method.getReturnType();
            propertyType = 1;
        } else if (HatchetInspectionUtils.isAccessibleSetter(method)) {
            int idx = Math.max(isProperty.index(), 0);
            methodAttrType = method.getParameterTypes()[idx];
            propertyType = 2;
        }
        if (isProperty.type() == AsSelf.class) {
            type = methodAttrType;
        } else {
            type = isProperty.type();
            try {
                if (isProperty.caster() == UndefinedValueCast.class) {
                    Class fromClass = null;
                    Class toClass = null;
                    switch (propertyType) {
                        case 1:
                            fromClass = methodAttrType;
                            toClass = isProperty.type();
                            break;
                        case 2:
                            fromClass = isProperty.type();
                            toClass = methodAttrType;
                            break;
                        default:
                            throw new IllegalArgumentException("Method is neither getter, nor setter");
                    }
                    caster = ValueCastManager.getInstance().get(new ValueCastSignature(fromClass, toClass));
                } else {
                    caster = ValueCastManager.getInstance().getOrCreate(isProperty.caster());
                }
            } catch (ManagerException e) {
                e.printStackTrace();
                // TODO throw exception
            }
        }
        PropertyBuilder builder = new PropertyBuilder(type, name);
        try {
            switch (propertyType) {
                case 1:
                    builder.setGetter(new MethodPropertyGetter(method, caster));
                    break;
                case 2:
                    builder.setSetter(new MethodPropertySetter(method, caster));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (PropertyAccessorException e) {
            throw new IllegalArgumentException(e);
        }
        return builder;
    }

    static List<PropertyBuilder> createPropertyBuilders(Constructor constructor, IsProperty isProperty, HasProperties hasProperties, InjectValue injectValue, InjectValues injectValues) {
        if(!HatchetInspectionUtils.isAccessibleConstructor(constructor))
            throw new IllegalArgumentException();
        PositionedValue[] values = new PositionedValue[constructor.getParameterCount()];
        int idx = 0;
        int propSize = 0;
        Set<IsProperty> constructroProperies = new HashSet<>();
        if (isProperty != null) {
            idx = add(idx, values, new PositionedValue(isProperty));
            constructroProperies.add(isProperty);
            propSize++;
        }
        if (hasProperties != null) {
            for (IsProperty prop : hasProperties.value()) {
                idx = add(idx, values, new PositionedValue(prop));
                constructroProperies.add(prop);
                propSize++;
            }
        }
        if (injectValue != null) {
            idx = add(idx, values, new PositionedValue(injectValue));
        }
        if (injectValues != null) {
            for (InjectValue inj : injectValues.value()) {
                idx = add(idx, values, new PositionedValue(inj));
            }
        }
        checkParameters(values);

        return null;
    }

    static PropertyBuilder createPropertyBuilder(Constructor constructor, IsProperty isProperty, int index) {
        if ("".equals(isProperty.name().trim())) {
            throw new IllegalArgumentException();
        }
        ValueCast caster = null;
        Class type = null;
        if (isProperty.type() == AsSelf.class) {
            type = constructor.getParameterTypes()[index];
        } else {
            type = isProperty.type();
            try {
                if (isProperty.caster() == UndefinedValueCast.class) {
                    caster = ValueCastManager.getInstance().get(new ValueCastSignature(type, constructor.getParameterTypes()[index]));
                } else {
                    caster = ValueCastManager.getInstance().getOrCreate(isProperty.caster());
                }
            } catch (ManagerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static List<PropertyBuilder> createPropertyBuilders(Method method, IsProperty isProperty, HasProperties hasProperties, InjectValue inject, InjectValues multipleInject) {
        if (!HatchetInspectionUtils.isAccessibleSetter(method))
            throw new IllegalArgumentException();
        PositionedValue[] values = new PositionedValue[method.getParameterCount()];
        int idx = 0;
        int propSize = 0;
        if (isProperty != null) {
            idx = add(idx, values, new PositionedValue(isProperty));
            propSize++;
        }
        if (hasProperties != null) {
            for (IsProperty prop : hasProperties.value()) {
                idx = add(idx, values, new PositionedValue(prop));
                propSize++;
            }
        }
        if (inject != null) {
            idx = add(idx, values, new PositionedValue(inject));
        }
        if (multipleInject != null) {
            for (InjectValue inj : multipleInject.value()) {
                idx = add(idx, values, new PositionedValue(inj));
            }
        }

        checkParameters(values);

        List<PropertyBuilder> builders = new ArrayList<>(method.getParameterCount());
        for (PositionedValue value : values) {
            if (value.getIsProperty()) {
                builders.add(createPropertyBuilder(method, value.getProperty()));
            }
        }
        return builders;
    }

    private static void checkParameters(PositionedValue[] values) {
        for (int k = 0; k < values.length; k++) {
            if (values[k] == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static int add(int lastIdx, PositionedValue[] values, PositionedValue value) {
        if (value.getIndex() > -1) {
            if (values[value.getIndex()] != null) {
                throw new IllegalArgumentException();
            }
            values[value.getIndex()] = value;
            return lastIdx;
        }
        if (lastIdx > values.length)
            throw new IndexOutOfBoundsException();
        for (int k = lastIdx; k < values.length; k++) {
            if (values[k] != null)
                continue;
            values[k] = value;
            return k;
        }
        throw new IndexOutOfBoundsException();
    }

//    static PropertyBuilder createProperty(Constructor constructor) {
//        return null;
//    }
}
