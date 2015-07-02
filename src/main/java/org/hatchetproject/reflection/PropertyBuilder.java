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

    private PropertyBuilder() {

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
        return null;
    }

    static PropertyBuilder createProperty(Method method) {
        return null;
    }

//    static PropertyBuilder createProperty(Constructor constructor) {
//        return null;
//    }
}
