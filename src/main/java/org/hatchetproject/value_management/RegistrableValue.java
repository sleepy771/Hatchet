package org.hatchetproject.value_management;

import org.hatchetproject.utils.HatchetInspectionUtils;

public class RegistrableValue implements InjectableValue {

    public static class ValueSignature {
        private final Class type;
        private final String name;


        public ValueSignature(String name, Class type) {
            this.name = name;
            this.type = type;
        }

        public Class getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public boolean isAssignable(Object o) {
            return type.isInstance(o);
        }

        @Override
        public int hashCode() {
            return (17 * 31 + name.hashCode()) * 31 + type.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return !(o == null || !(o instanceof ValueSignature))
                    && ((ValueSignature) o).name.equals(name) && ((ValueSignature) o).type == type;
        }
    }

    private final ValueSignature signature;
    private Object value;

    public RegistrableValue(String name, Object value) {
        this(name, value.getClass(), value);
    }

    public RegistrableValue(String name, Class type, Object value) {
        this.signature = new ValueSignature(name, type);
        setValue(value);
    }

    public void setValue(Object value) {
        if (signature.isAssignable(value)) {
            this.value = value;
            return;
        }
        throw new ClassCastException("Can not cast " + value.getClass().getName() + " to " + signature.type.getName());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Class getType() {
        return signature.type;
    }

    @Override
    public String getName() {
        return signature.name;
    }

    @Override
    public ValueSignature getSignature() {
        return this.signature;
    }

    @Override
    public int hashCode() {
        return signature.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof RegistrableValue))
                && ((RegistrableValue) o).signature.equals(signature)
                && HatchetInspectionUtils.safeEquals(((RegistrableValue) o).value, value);
    }
}
