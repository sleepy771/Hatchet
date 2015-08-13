package org.hatchetproject.reflection.accessors;

public interface Targetable {

    void setTarget(Object target);

    Object getTarget();

    boolean hasTarget();
}
