package org.hatchetproject.value_management;


public interface ValueCast<INPUT, OUTPUT> {
    OUTPUT cast(INPUT input) throws ClassCastException;

    Class<? extends OUTPUT> getOutputType();

    Class<? extends INPUT> getInputType();

    ValueCastSignature getSignature();

    ValueCast<OUTPUT, INPUT> reverseValueCast();
}
