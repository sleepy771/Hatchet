package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.reflection.accessors.Getter;
import org.hatchetproject.reflection.accessors.Targetable;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyGetterHelper;

public interface PropertyGetter extends Targetable {

    PropertyPromise getPromise(Object source);

    void setHelper(PropertyGetterHelper helper);

    PropertyGetterHelper getHelper();

    Getter getGetter();
}
