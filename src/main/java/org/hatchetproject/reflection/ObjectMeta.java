package org.hatchetproject.reflection;

import org.hatchetproject.manager.DefaultAbstractManager;

/**
 * Created by filip on 6/27/15.
 */
public class ObjectMeta extends DefaultAbstractManager<String, IProperty> {
    @Override
    protected String getKeyForElement(IProperty iProperty) {
        return iProperty.getName();
    }

    public Scheme getScheme() {
        return null;
    }
}
