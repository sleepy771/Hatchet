package org.hatchetproject.reflection.properties;

public interface MutableProperty  extends Property {
    boolean setValue(Object newValue);
}
