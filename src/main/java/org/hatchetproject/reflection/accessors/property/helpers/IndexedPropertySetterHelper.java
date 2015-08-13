package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.reflection.accessors.Setter;

public class IndexedPropertySetterHelper<TYPE> implements PropertySetterHelper<TYPE> {

    private final int index;

    public IndexedPropertySetterHelper(int index) {
        this.index = index;
    }

    @Override
    public void assign(Setter setter, TYPE insert) {
        setter.getBuilder().set(index, insert);
    }
}
