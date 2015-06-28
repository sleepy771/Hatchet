package org.hatchetproject.reflection;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by filip on 6/27/15.
 */
public class ClassInspector {

    private final Class clazz;

    public List<Method> getters;

    private ClassInspector(Class clazz) {
        this.clazz = clazz;
    }


}
