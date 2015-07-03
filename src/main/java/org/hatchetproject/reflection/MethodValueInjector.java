package org.hatchetproject.reflection;

import java.util.List;

/**
 * Created by filip on 7/4/15.
 */
public class MethodValueInjector {

    private Class declaringClass;

    private List<PropertySetter> methodSetters;

    public void addSetter(MethodPropertySetter setter) {
        this.methodSetters.add(setter);
    }
}
