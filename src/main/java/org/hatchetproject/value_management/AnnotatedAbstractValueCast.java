package org.hatchetproject.value_management;

import org.hatchetproject.annotations.valuecast.FromClass;
import org.hatchetproject.annotations.valuecast.ToClass;

public abstract class AnnotatedAbstractValueCast<INPUT, OUTPUT> implements ValueCast<INPUT, OUTPUT>{

    private ValueCastSignature sgn;

    @Override
    public Class<? extends OUTPUT> getOutputType() {
        FromClass fromClass = this.getClass().getAnnotation(FromClass.class);
        if (fromClass == null) {
            throw new RuntimeException("Invalid definition of ValueCast");
        }
        return fromClass.value();
    }

    @Override
    public Class<? extends INPUT> getInputType() {
        ToClass fromClass = this.getClass().getAnnotation(ToClass.class);
        if (fromClass == null) {
            throw new RuntimeException("Invalid definition of ValueCast");
        }
        return fromClass.value();
    }

    @Override
    public ValueCastSignature getSignature() {
        if (sgn == null) {
            sgn = new ValueCastSignature(getInputType(), getOutputType());
        }
        return sgn;
    }
}
