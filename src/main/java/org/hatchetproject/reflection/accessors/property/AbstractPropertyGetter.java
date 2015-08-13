package org.hatchetproject.reflection.accessors.property;

import org.hatchetproject.reflection.accessors.Getter;
import org.hatchetproject.reflection.accessors.property.helpers.PropertyGetterHelper;

public class AbstractPropertyGetter implements PropertyGetter {

    private Getter getter;
    private PropertyGetterHelper helper;

    @Override
    public Promise getPromise(Object source) {
        return null;
    }

    @Override
    // TODO maybe remove this method
    public void setHelper(PropertyGetterHelper helper) {
        this.helper = helper;
    }

    @Override
    public PropertyGetterHelper getHelper() {
        return helper;
    }

    @Override
    public Getter getGetter() {
        return getter;
    }
}
