package org.hatchetproject.reflection;

public interface PropertyGetter extends PropertyAccessor {
    Object get(Object source);
}
