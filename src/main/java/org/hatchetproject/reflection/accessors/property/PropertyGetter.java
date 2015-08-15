package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.reflection.accessors.Getter;
import org.hatchetproject.reflection.accessors.Targetable;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyHelper;

public interface PropertyGetter extends Targetable {

    PropertyPromise getPromise(Object source);

    void setHelper(PropertyHelper helper);

    PropertyHelper getHelper();

    Getter getGetter();
}
