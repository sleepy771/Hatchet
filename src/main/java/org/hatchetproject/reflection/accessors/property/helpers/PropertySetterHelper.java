package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.reflection.accessors.Setter;

public interface PropertySetterHelper<TYPE> {
    void assign(Setter setter, TYPE insert);
}
