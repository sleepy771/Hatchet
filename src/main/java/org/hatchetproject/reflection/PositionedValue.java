package org.hatchetproject.reflection;

import org.hatchetproject.annotations.InjectDefault;
import org.hatchetproject.annotations.Property;

/**
 * Created by filip on 7/5/15.
 */
public class PositionedValue {

    private InjectDefault injectDefault;

    private Property property;

    private int index;

    public PositionedValue(InjectDefault inject) {
        this(inject.index() > -1 ? inject.index() : -1, inject);
    }

    public PositionedValue(Property property) {
        this(property.index() > -1 ? property.index() : -1, property);
    }

    public PositionedValue(int position, InjectDefault inject) {
        this.index = position;
        this.injectDefault = inject;
    }

    public PositionedValue(int position, Property property) {
        this.index = position;
        this.property = property;
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

    public boolean isProperty() {
        return this.property != null;
    }

    public InjectDefault getInject() {
        return injectDefault;
    }

    public Property getProperty() {
        return property;
    }
}
