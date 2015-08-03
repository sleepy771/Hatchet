package org.hatchetproject.reflection;

import org.hatchetproject.annotations.InjectDefault;
import org.hatchetproject.annotations.IsProperty;

/**
 * Created by filip on 7/5/15.
 */
public class PositionedValue {

    private InjectDefault injectDefault;

    private IsProperty isProperty;

    private int index;

    public PositionedValue(InjectDefault inject) {
        this(inject.index() > -1 ? inject.index() : -1, inject);
    }

    public PositionedValue(IsProperty isProperty) {
        this(isProperty.index() > -1 ? isProperty.index() : -1, isProperty);
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
        return index > -1;
    }

    public boolean hasInject() {
        return this.injectDefault != null;
    }

    public boolean getIsProperty() {
        return this.isProperty != null;
    }

    public InjectDefault getInject() {
        return injectDefault;
    }

    public IsProperty getProperty() {
        return isProperty;
    }
}
