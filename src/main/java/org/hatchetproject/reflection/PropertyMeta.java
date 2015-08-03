package org.hatchetproject.reflection;

public class PropertyMeta implements Signature {

    private final String name;
    private final Class type;
    private final Class declaringClass;
    private volatile int hashCode;

    PropertyMeta(Class declaringClass, String name, Class type) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class getType() {
        return type;
    }

    @Override
    public Class getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        PropertyMeta that = (PropertyMeta) o;

        return !(null != name ? !name.equals(that.name) : null != that.name) && !(null != type ? !type.equals(that.type) : null != that.type) && !(null != declaringClass ? !declaringClass.equals(that.declaringClass) : null != that.declaringClass);
    }

    @Override
    public int hashCode() {
        if (0 == hashCode) {
            int result = 17 ;
            result = 31 * result + (null != name ? name.hashCode() : 0);
            result = 31 * result + (null != type ? type.hashCode() : 0);
            result = 31 * result + (null != declaringClass ? declaringClass.hashCode() : 0);
            hashCode = result;
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return "PropertyMeta{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", declaringClass=" + declaringClass +
                '}';
    }
}
