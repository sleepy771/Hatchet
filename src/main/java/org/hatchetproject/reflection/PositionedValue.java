package org.hatchetproject.reflection;

import org.hatchetproject.annotations.InjectDefault;
import org.hatchetproject.annotations.IsProperty;

public class PositionedValue {

    private InjectDefault injectDefault;

    private IsProperty isProperty;

    private int index;

    public PositionedValue(InjectDefault inject) {
        this(-1 < inject.index() ? inject.index() : -1, inject);
    }

    public PositionedValue(IsProperty isProperty) {
        this(-1 < isProperty.index() ? isProperty.index() : -1, isProperty);
    }

    public PositionedValue(int position, InjectDefault inject) {
        this.index = position;
        this.injectDefault = inject;
    }

    public PositionedValue(int position, IsProperty isProperty) {
        this.index = position;
        this.isProperty = isProperty;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean hasSpecificPosition() {
        return -1 < index;
    }

    public boolean hasInject() {
        return null != this.injectDefault;
    }

    public boolean getIsProperty() {
        return null != this.isProperty;
    }

    public InjectDefault getInject() {
        return injectDefault;
    }

    public IsProperty getProperty() {
        return isProperty;
    }
}
