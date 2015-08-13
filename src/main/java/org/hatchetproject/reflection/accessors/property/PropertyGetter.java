package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.exceptions.PropertyGetterException;
import org.hatchetproject.reflection.accessors.Getter;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyGetterHelper;

public interface PropertyGetter {

    Promise<Object, Object> getPromise(Object source);

    void setHelper(PropertyGetterHelper helper);

    PropertyGetterHelper getHelper();

    Getter getGetter();
}
