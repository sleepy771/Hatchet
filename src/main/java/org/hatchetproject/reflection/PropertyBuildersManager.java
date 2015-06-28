package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.AbstractManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by filip on 6/28/15.
 */
public class PropertyBuildersManager extends AbstractManager<Signature, PropertyBuilder> {
    @Override
    protected Signature getKeyForElement(PropertyBuilder propertyBuilder) {
        return null;
    }

    @Override
    protected void postRegister(Signature signature, PropertyBuilder propertyBuilder) {
    }

    @Override
    protected void postUnregister(Signature signature, PropertyBuilder propertyBuilder) {
    }

    @Override
    protected String verboseKey(Signature signature) {
        return signature.toString();
    }

    @Override
    protected String verboseElement(PropertyBuilder propertyBuilder) {
        return propertyBuilder.toString();
    }

    @Override
    public boolean isRegistered(PropertyBuilder propertyBuilder) {
        return false;
    }

    @Override
    public boolean isRegistered(Signature signature, PropertyBuilder propertyBuilder) {
        return false;
    }

    @Override
    public boolean isKeyRegistered(Signature signature) {
        return false;
    }

    @Override
    public PropertyBuilder get(Signature signature) throws ManagerException {
        return null;
    }

    public PropertyBuilder getOrCreate(Field field) {
        FieldSignature fieldSignature = new FieldSignature(field);
        if (!containsKey(fieldSignature)) {
            silentRegister(fieldSignature, new PropertyBuilder());
        }
        return getDirectly(fieldSignature);
    }

    public PropertyBuilder getOrCreate(Method method) {
        return null;
    }

    public Map<Signature, PropertyBuilder> getOrCreateMultiple(Constructor constructor) {
        return null;
    }
}
