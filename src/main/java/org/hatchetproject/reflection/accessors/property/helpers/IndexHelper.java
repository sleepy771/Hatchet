package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.reflection.accessors.Setter;

public class IndexHelper implements PropertyHelper<Object, Void> {

    private final Setter setter;

    private final int index;

    private final PropertyHelper parentHelper;

    public IndexHelper(Setter setter, int index, PropertyHelper parentHelper) {
        this.parentHelper = parentHelper;
        this.setter = setter;
        this.index = index;
    }

    public IndexHelper(Setter setter, int index) {
        this(setter, index, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Void push(Object o) {
        this.setter.setParameter(index, null != parentHelper ? parentHelper.push(o) : o);
        return null;
    }
}
