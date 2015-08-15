package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.reflection.accessors.Setter;

public interface PropertySetterHelper<INPUT, OUTPUT> extends PropertyHelper<INPUT, OUTPUT> {
    void setSetter(Setter setter);

    boolean canAssignType(Class type);
}
