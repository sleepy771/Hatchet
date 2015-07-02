package org.hatchetproject.value_management;

public class ValueCastSignature {

    private final Class fromClass, toClass;

    public ValueCastSignature(Class fromClass, Class toClass) {
        this.fromClass = fromClass;
        this.toClass = toClass;
    }

    public Class getFromClass() {
        return fromClass;
    }

    public Class getToClass() {
        return toClass;
    }

    public String toString() {
        return "Cast from: " + fromClass.getName() + " to: " + toClass.getName();
    }

    public int hashCode() {
        return (17 * 31 + fromClass.hashCode()) * 31 + toClass.hashCode();
    }

    public boolean equals(Object o) {
        return !(o == null || o.hashCode() != hashCode() || !ValueCastSignature.class.isInstance(o))
                && fromClass == ((ValueCastSignature) o).fromClass && toClass == ((ValueCastSignature) o).toClass;
    }
}
