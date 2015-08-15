package org.hatchetproject.reflection.accessors.property.helpers;

import java.util.List;

public class ListGetHelper implements PropertyHelper<List, Object> {

    private final int index;

    public ListGetHelper(int index) {
        this.index = index;
    }

    @Override
    public Object push(List list) {
        return list.get(index);
    }
}
