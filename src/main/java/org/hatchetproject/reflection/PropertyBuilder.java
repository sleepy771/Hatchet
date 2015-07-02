package org.hatchetproject.reflection;

import org.apache.log4j.Logger;
import org.hatchetproject.annotations.Property;
import org.hatchetproject.reflection.constants.AsSelf;
import org.hatchetproject.utils.HatchetInspectionUtils;
import org.hatchetproject.value_management.UndefinedValueCast;
import org.hatchetproject.value_management.ValueCast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyBuilder {

    private static Logger logger = Logger.getLogger(PropertyBuilder.class.getName());

    private PropertyBuilder() {

    }

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
        if (fieldProperty.type() != AsSelf.class) {
            if (fieldProperty.caster() == UndefinedValueCast.class) {
                // TODO try to find one
            } else {
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
