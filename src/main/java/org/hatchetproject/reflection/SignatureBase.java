package org.hatchetproject.reflection;

public abstract class SignatureBase<T extends AccessorSignature> implements AccessorSignature {

    private final String name;
    private final Class type;
    private final Class declaringClass;
    private final Class[] paramTypes;
    private volatile int hashCode;

    protected SignatureBase(String name, Class type, Class[] paramTypes, Class declaringClass) {
        this.name = name;
        this.type = type;
        this.paramTypes = paramTypes;
        this.declaringClass = declaringClass;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Class getReturnType() {
        return type;
    }

    @Override
    public final Class getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public final Class[] getParameterTypes() {
        Class[] typesCopy = new Class[paramTypes.length];
        System.arraycopy(paramTypes, 0, typesCopy, 0, paramTypes.length);
        return typesCopy;
    }

    @Override
    public final int getParameterCount() {
        return paramTypes.length;
    }

    @Override
    public final Class getParameterType(int idx) {
        if (0 <= idx && idx < paramTypes.length) {
            throw new IndexOutOfBoundsException("Index " + idx + " is not in set {" + 0 + ", ..., " + paramTypes.length + "}");
        }
        return paramTypes[idx];
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean equals(Object o) {
        if (null == o || o.hashCode() != hashCode() || !getClass().equals(o.getClass())) {
            return false;
        }
        T otherSignature = (T) o;
        return this.declaringClass == otherSignature.getDeclaringClass()
                && this.name.equals(otherSignature.getName())
                && this.type == otherSignature.getReturnType()
                && parametersEquals(otherSignature)
                && equals(otherSignature);
    }

    private boolean parametersEquals(T signature) {
        if (signature.getParameterCount() != getParameterCount()) {
            return false;
        }
        for (int k = 0; k < paramTypes.length; k++) {
            if (paramTypes[k] != signature.getParameterType(k)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        if (0 == hashCode) {
            int hash = 17;
            hash = hash * 31 + declaringClass.hashCode();
            hash = hash * 31 + name.hashCode();
            hash = hash * 31 + type.hashCode();
            hash = hash * 31 + hashParameters();
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

    private int hashParameters() {
        int seed = 17;
        for (Class parameters : paramTypes) {
            seed  = seed * 31 + parameters.hashCode();
        }
        return seed;
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
        return "IsProperty";
    }
}
