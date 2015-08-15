package org.hatchetproject.reflection.accessors.property.helpers;

import java.util.List;


public class ListSetHelper<TYPE> implements PropertyHelper<TYPE, Void> {

    private List<TYPE> list;

    private final int index;

    public ListSetHelper(int index) {
        this.index = index;
    }

    public void setList(List<TYPE> list) {
        this.list = list;
    }

    @Override
    public Void push(TYPE type) {
        list.set(index, type);
        return null;
    }
}
