package org.hatchetproject.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 7/25/15.
 */
public class FieldSetter extends AbstractSetter {

    private Field field;
    private Class[] fieldType;

    public FieldSetter(Field field) {
        this.field = field;
        this.fieldType = new Class[]{field.getType()};
    }

    @Override
    protected final Object performSet(Object objects, Object[] values) throws InstantiationException, InvocationTargetException, IllegalAccessException {
        Object fieldValue = field.get(objects);
        field.set(objects, values);
        return fieldValue;
    }

    @Override
    public final Class[] getTypes() {
        return fieldType;
    }
}
