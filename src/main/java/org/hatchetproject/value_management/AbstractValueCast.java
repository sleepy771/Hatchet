package org.hatchetproject.value_management;

public abstract class AbstractValueCast<INPUT, OUTPUT> implements ValueCast<INPUT, OUTPUT> {

    private final ValueCastSignature signature;

    protected AbstractValueCast(Class<INPUT> input, Class<OUTPUT> output) {
        this.signature = new ValueCastSignature(input, output);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends OUTPUT> getOutputType() {
        return signature.getToClass();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends INPUT> getInputType() {
        return signature.getFromClass();
    }

    @Override
    public ValueCastSignature getSignature() {
        return this.signature;
    }

    @Override
    public int hashCode() {
        return this.signature.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || o.hashCode() != hashCode() || AbstractValueCast.class.isInstance(o))
                && getSignature().equals(((ValueCast) o).getSignature());
    }
}
