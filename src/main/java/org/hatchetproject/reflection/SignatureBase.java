package org.hatchetproject.reflection;

public abstract class SignatureBase<T extends Signature> implements Signature {

    private final String name;
    private final Class type;
    private final Class declaringClass;
    private volatile int hashCode;

    protected SignatureBase(String name, Class type, Class declaringClass) {
        this.name = name;
        this.type = type;
        this.declaringClass = declaringClass;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Class getType() {
        return type;
    }

    @Override
    public final Class getDeclaringClass() {
        return declaringClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean equals(Object o) {
        if (o == null || o.hashCode() != hashCode() || !getClass().equals(o.getClass()))
            return false;
        T otherSignature = (T) o;
        return this.declaringClass == otherSignature.getDeclaringClass()
                && this.name.equals(otherSignature.getName())
                && this.type == otherSignature.getType()
                && equals(otherSignature);
    }

    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            int hash = 17;
            hash = hash * 31 + declaringClass.hashCode();
            hash = hash * 31 + name.hashCode();
            hash = hash * 31 + type.hashCode();
            hash = hash * 31 + additionalHash();
            hashCode = hash;
        }
        return hashCode;
    }

    @Override
    public final String toString() {
        return propertyTypeName() + ": {\n\tname = " + name + ";\n\ttype = "
                + type.getName() + ";\n\tdeclaringClass = "
                + declaringClass.getName() + ";\n"
                + additionalDescription() + "}";
    }

    protected int additionalHash() {
        return 0;
    }

    protected boolean equals(T signature) {
        return true;
    }


    protected String additionalDescription() {
        return "";
    }

    protected String propertyTypeName() {
        return "Property";
    }
}
