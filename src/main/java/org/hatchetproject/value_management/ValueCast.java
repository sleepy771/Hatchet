package org.hatchetproject.value_management;

/**
 * Created by filip on 6/27/15.
 */
public interface ValueCast<INPUT, OUTPUT> {
    OUTPUT cast(INPUT input) throws ClassCastException;

    Class<? extends OUTPUT> getOutputType();

    Class<? extends INPUT> getInputType();
}
