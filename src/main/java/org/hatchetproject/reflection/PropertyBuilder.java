package org.hatchetproject.reflection;

import org.apache.log4j.Logger;
import org.hatchetproject.annotations.Property;
import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.reflection.constants.AsSelf;
import org.hatchetproject.utils.HatchetInspectionUtils;
import org.hatchetproject.value_management.UndefinedValueCast;
import org.hatchetproject.value_management.ValueCast;
import org.hatchetproject.value_management.ValueCastManager;
import org.hatchetproject.value_management.ValueCastSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyBuilder {

    private static Logger logger = Logger.getLogger(PropertyBuilder.class.getName());

    private Class propertyType;
    private String propertyName;

    private PropertySetter setter;

    private PropertyGetter getter;

    private PropertyBuilder(Class propertyType, String propertyName) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
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
    static PropertyBuilder createProperty(Field field) {
        Property fieldProperty = field.getAnnotation(Property.class);
        if (!HatchetInspectionUtils.isValidPropertyName(fieldProperty.name())) {
            logger.error("Invalid property name, was set");
            throw new IllegalArgumentException();
        }
        if (fieldProperty.index() != -1) {
            logger.warn("Unsupported operating: index in field property was changed from default -1");
        }
        String name = "-".equals(fieldProperty.name()) ? field.getName() : fieldProperty.name();
        Class type = null;
        ValueCast caster = null;
        if (fieldProperty.type() != AsSelf.class) {
            type = fieldProperty.type();
            try {
                if (fieldProperty.caster() == UndefinedValueCast.class) {
                    caster = ValueCastManager.getInstance().get(new ValueCastSignature(field.getType(), fieldProperty.type()));
                } else {
                    caster = ValueCastManager.getInstance().getOrCreate(fieldProperty.caster());
                    if (!(caster.getInputType().isAssignableFrom(field.getType()) && fieldProperty.type().isAssignableFrom(caster.getOutputType()))) {
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
        builder.setGetter(new FieldPropertyGetter(field, caster));
        builder.setSetter(new FieldPropertySetter(field, caster != null ? caster.reverseValueCast() : null));
        return builder;
    }

    static PropertyBuilder createProperty(Method method) {
        Property property = method.getAnnotation(Property.class);
        if (!HatchetInspectionUtils.isValidPropertyName(property.name()))
            throw new IllegalArgumentException("Invalid property name");
        return null;
    }

//    static PropertyBuilder createProperty(Constructor constructor) {
//        return null;
//    }
}
