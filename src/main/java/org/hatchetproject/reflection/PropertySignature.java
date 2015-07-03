package org.hatchetproject.reflection;

public class PropertySignature extends SignatureBase<PropertySignature> implements Signature {

    public PropertySignature(IProperty property) {
        this(property.getName(), property.getPropertyType(), property.getDeclaringClass());
    }

    public PropertySignature(String name, Class type, Class declaringClass) {
        super(name, type, new Class[] {type}, declaringClass);
    }
}
