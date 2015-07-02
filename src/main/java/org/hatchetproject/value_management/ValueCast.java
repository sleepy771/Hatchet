package org.hatchetproject.value_management;

import org.hatchetproject.manager.memory.Releasable;

/**
 * Created by filip on 6/27/15.
 */
public interface ValueCast<INPUT, OUTPUT> extends Releasable {
    OUTPUT cast(INPUT input) throws ClassCastException;

    Class<? extends OUTPUT> getOutputType();

    Class<? extends INPUT> getInputType();
}
